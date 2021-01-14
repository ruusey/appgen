package com.appgen.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppgenMain implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(AppgenMain.class);


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing Transaction Service...");
   
    }

}
