package com.appgen.application;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appgen.config.MessagingConfiguration;
import com.appgen.messaging.StoreTransactionReceiver;

@Component
public class AppgenMain implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(AppgenMain.class);


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing Appgen Service...");
        
      


    }

}
