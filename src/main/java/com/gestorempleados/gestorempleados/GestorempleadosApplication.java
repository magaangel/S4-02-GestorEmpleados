package com.gestorempleados.gestorempleados;

import com.gestorempleados.gestorempleados.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties({Employee.class})//--> habilita @ConfigurationProperty
public class GestorempleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorempleadosApplication.class, args);

	}





}
