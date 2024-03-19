package com.epf.rentmanager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan({"com.epf.rentmanager.service", "com.epf.rentmanager.dao","com.epf.rentmanager.persistence","com.epf.rentmanager.servlet"}) // packages dans lesquels chercher les beans
public class AppConfiguration {
}
