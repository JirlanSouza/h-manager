package com.js.hmanager.config.di;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandGatewayConfig {

    @Bean
    public CommandGateway commandGateway(SimpleCommandBus commandBus) {
        CommandGatewayFactory factory = CommandGatewayFactory.builder()
                .commandBus(commandBus)
                .build();

        return factory.createGateway(CommandGateway.class);
    }

    @Bean
    public SimpleCommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }
}
