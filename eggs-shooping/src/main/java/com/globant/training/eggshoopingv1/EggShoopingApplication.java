package com.globant.training.eggshoopingv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = {"com.globant.training.eggshoopingv1"})
public class EggShoopingApplication {
    public static void main(String... args) {
        SpringApplication.run(EggShoopingApplication.class, args);
    }
}

/*
    faltan hacer algunos tests de los servicios, los de controladores ya estan y de repositorios no hace falta
 */
