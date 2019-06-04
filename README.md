# World Messages
Application using **Spring**, **RabbitMQ** and **MySQL** for development and **Docker** and **Kubernetes** for deployment.

----
## Installation

1- Install **JDK8**

2- Install **Docker Desktop**

3- Install **Docker Compose** (it's included in Docker Desktop)

4- Enable **Kubernetes** on Docker Desktop or install it separately

5- Install **Make** (optional)
```sh
sudo apt-get install build-essential
```
> **Note:** If you don't want to install Make, take a look at the commands used in each step inside the file Makefile.

6- Modify the file **kubernetes/infrastructure/config-volume.yaml** replacing ```PROJECT_PATH``` with the absolute path of the project in your machine (only required for Kubernetes)
```yaml
  hostPath:
    path: "PROJECT_PATH/kubernetes/config"
```
> **Note:** in a real environment, this hostPath should be replaced by using some tool to achieve templating engine, like Helm.

7- Set up **dnsmasq** (only required for running a service manually)

  You must add the "**.wmdev**" domain and the **wmdev** resolver, enabling all requests to a name that finishes in ".wmdev" to be redirected to localhost. Example: a request to http://message-engine.wmdev:8081/messages will go to http://localhost:8081/messages.
  Steps for macOS:
  - Install dnsmasq:
    ```sh
    brew install dnsmasq
    ```
  - Start dnsmasq:
    ```sh
    sudo brew services start dnsmasq
    ```
  - Edit /usr/local/etc/dnsmasq.conf and add the following line:
    ```
    address=/wmdev/127.0.0.1
    ```
  - Restart dnsmasq:
    ```sh
    sudo brew services restart dnsmasq
    ```
  - Run the following command:
    ```sh
    sudo bash -c 'echo "nameserver 127.0.0.1" > /etc/resolver/wmdev'
    ```
----
## Usage
### Build
To avoid building each application separately, you can use the following Make command:
```sh
make build-apps
```
Then, build the images using the this Make command:

```sh
make build-images
```
### Deploy
This application can be deployed using Docker Compose, Kubernetes or doing it manually.

#### 1- Using Docker Compose

Execute the following command to deploy the application:
```sh
make compose
```

If you want to stop all containers, you can use the following Make command:
```sh
make compose-stop
```

> **Note:** it's possible that RabbitMQ or MySQL containers are deployed after the others, that will cause some connection exceptions in the services logs.

#### 2- Using Kubernetes

Execute this command to deploy the infrastructure:
```sh
make k8s-infrastructure
```

Wait for RabbitMQ and MySQL to be deployed, an then execute this command to deploy the services:
```sh
make k8s-services
```
> **Note:** if the readiness of some services is not reached, then you must execute each service one by one. You can use the following Make command:
> ```sh
> make k8s-service svc=SERVICE_NAME
>``` 
> List of services names: 
> - message-manager
> - message-engine
> - eu-service
> - spain-service
> - italy-service
> - japan-service
>
> Example:
>  ```sh
> make k8s-service svc=message-manager
>  ```
> This way each service will deploy faster.

If you want to delete all deployments, you can use the following Make command:
```sh
make k8s-delete
```
To see the services, deployments, pods, etc. you must first switch to namespace **dev** executing this kubectl command:
```sh
kubectl config set-context --current --namespace=dev
```

Then, you can use the different kubectl commands, for example:
```sh
kubectl get pods
kubectl get services
kubectl get deployments
kubectl logs POD_NAME
```  
#### 3- Deploy each application separately

Although it is not the purpose of this project, you can execute all services via console or IDE. You would need:
 - MySQL server running on the default port (**3306**) with the following users:
    - Root user. Username: **root**, password: **root**
    - Normal user. Username: **user**, password: **password**
 - RabbitMQ server running on the default port (**5672**) with the user **user** and password **pass**
 - Build and deploy each Java service (they're Spring Boot Applications) passing the environment variable ```CONFIG_FOLDER=PROJECT_PATH/docker/config```
  
    Example:
    ```sh
    cd message-engine
    ./gradlew clean build
    java -jar build/libs/message-engine-0.0.1-SNAPSHOT.jar --CONFIG_FOLDER=PROJECT_PATH/docker/config
    ```

----
## Architecture
The application contains the following services:
- **Message Manager**: this component handles the creation of messages. It creates all the RabbitMQ exchanges and binds the different queues to them. When a message creation request is received, it calls Message Engine to store the message and then publishes the message to the correspondant queues
- **Message Engine**: interacts with the database (MySQL). It stores and retrieves the different messages requested via REST from Message Manager
- **EU Service**: it reads all the messages with 'EU', all its members ('ES' and 'IT') or 'WORLD' as receiver
- **Spain Service**: it reads all the messages with 'ES' or 'WORLD' as receiver
- **Italy Service**: it reads all the messages with 'IT' or 'WORLD' as receiver
- **Japan Service**: it reads all the messages with 'JP' or 'WORLD' as receiver

<p align="center">
  <img src="https://raw.githubusercontent.com/sergiobanegas/world-messages/master/diagrams/architecture.png">
</p>

----
## Test it
- The RabbitMQ management page is accessible by using the following URL:
http://localhost:15672/#/ (username: **user**, password: **pass**)

- A Swagger page is available on the following link:
http://localhost:8080/swagger-ui.html

- Connect to database:

  - Docker Compose:
    ```sh
    docker exec -it db mysql -uroot -p # Enter password 'root'
    ```
  - Kubernetes:
    ```sh
    kubectl config set-context --current --namespace=dev
    kubectl exec -it db-7844fbbff4-24ntr -- mysql -u root -p # Enter password 'root'
    ```

  Then, you can check the messages table:
  ```sh
  use worldmessages;
  SELECT * FROM message;
  ```

*  Create a message

    Make a POST request to http://localhost:8080/messages with the following body:
    ```json
    {
      "body": "Message you want to send",
      "receiver": "IT"
    }
    ```
    Response:
    ```json
    {
      "message": "The message was sent",
      "status": 200
    }
    ```
    To test it fully, when you create a message, check out the recipients logs and you will see that they logged this line:
    
     ```Received message: 'MESSAGE_BODY'```
 - Get all messages

    Make a GET request to http://localhost:8080/messages

    Response:
    ```json
    {
      "content": [
        {
          "id": 1,
          "body": "Message 1",
          "receiver": "IT",
          "creationDate": "2019-04-29T20:39:58.000+0000"
        },
        {
          "id": 2,
          "body": "Message 2",
          "receiver": "JP",
          "creationDate": "2019-04-30T13:37:43.000+0000"
        },
        {
          "id": 3,
          "body": "Message 3",
          "receiver": "EU",
          "creationDate": "2019-05-29T19:21:19.000+0000"
        }
      ]
    }
    ```
----
**Application made by Sergio Banegas Cortijo**
