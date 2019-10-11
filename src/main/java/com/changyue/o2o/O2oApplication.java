package com.changyue.o2o;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication()
public class O2oApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2oApplication.class, args);
    }

}
