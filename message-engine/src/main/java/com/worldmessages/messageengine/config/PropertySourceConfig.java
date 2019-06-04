package com.worldmessages.messageengine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/message-engine.properties")
public class PropertySourceConfig {
}
