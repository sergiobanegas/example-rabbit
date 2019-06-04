build-apps:
	@ echo 'Generating all applications JARs...'
	@ echo '##########################################################################'	
	@ echo 'Building Message Engine...'
	@ cd message-engine && ./gradlew clean build
	@ echo '##########################################################################'	
	@ echo 'Building Message Manager...'
	@ cd message-manager && ./gradlew clean build
	@ echo '##########################################################################'	
	@ echo 'Building EU Service...'
	@ cd eu-service && ./gradlew clean build
	@ echo '##########################################################################'	
	@ echo 'Building Spain Service...'
	@ cd spain-service && ./gradlew clean build
	@ echo '##########################################################################'	
	@ echo 'Building Italy Service...'
	@ cd italy-service && ./gradlew clean build
	@ echo '##########################################################################'	
	@ echo 'Building Japan Service...'
	@ cd japan-service && ./gradlew clean build

build-images:
	@ echo 'Building all applications images...'
	@ echo '##########################################################################'	
	@ echo 'Building Message Engine image...'
	@ docker build -t message-engine -f message-engine/Dockerfile .
	@ echo '##########################################################################'	
	@ echo 'Building Message Manager image...'
	@ docker build -t message-manager -f message-manager/Dockerfile .
	@ echo '##########################################################################'	
	@ echo 'Building EU Service image...'
	@ docker build -t eu-service -f eu-service/Dockerfile .
	@ echo '##########################################################################'	
	@ echo 'Building Spain Service image...'
	@ docker build -t spain-service -f spain-service/Dockerfile .
	@ echo '##########################################################################'	
	@ echo 'Building Italy Service image...'
	@ docker build -t italy-service -f italy-service/Dockerfile .
	@ echo '##########################################################################'	
	@ echo 'Building Japan Service image...'
	@ docker build -t japan-service -f japan-service/Dockerfile .

compose:
	@ echo 'Deploying application containers...'
	@ cd docker && docker-compose up -d

compose-stop:
	@ echo 'Stopping containers...'
	@ cd docker && docker-compose down

k8s-infrastructure:
	@ echo '##########################################################################'	
	@ echo 'Creating namespaces...'
	@ kubectl apply -f kubernetes/namespace
	@ echo '##########################################################################'	
	@ echo 'Deploying infrastructure...'
	@ kubectl apply -f kubernetes/infrastructure

k8s-services:
	@ echo 'Deploying services...'
	@ kubectl apply -f kubernetes/service

k8s-service:
	@ echo 'Deploying service $(svc)...'
	@ kubectl apply -f kubernetes/service/$(svc).yaml

k8s-delete:
	@ echo 'Deleting Kubernetes environment...'
	@ echo '##########################################################################'
	@ echo 'Deleting services...'
	@ kubectl delete -f kubernetes/service
	@ echo '##########################################################################'
	@ echo 'Deleting infrastructure...'
	@ kubectl delete -f kubernetes/infrastructure	
	@ echo '##########################################################################'
	@ echo 'Deleting namespace...'
	@ kubectl delete -f kubernetes/namespace
