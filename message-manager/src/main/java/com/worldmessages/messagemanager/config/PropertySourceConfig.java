package com.worldmessages.messagemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/message-manager.properties")
public class PropertySourceConfig {
}
