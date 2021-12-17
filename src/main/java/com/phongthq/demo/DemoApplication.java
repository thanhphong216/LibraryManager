package com.phongthq.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        String hibernateFilename = System.getProperty("user.dir") + "/hibernate.cfg.xml";
        try {
            File file = new File(hibernateFilename);
            System.out.println("buildSessionFactory - path = " + hibernateFilename);
            Configuration configuration = new Configuration().configure(file);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.out.println("Initial SessionFactory creation failed. "+ ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
}
