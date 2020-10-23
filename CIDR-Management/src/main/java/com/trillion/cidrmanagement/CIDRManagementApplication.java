package com.trillion.cidrmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.Configuration;


@SpringBootApplication
public class CIDRManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CIDRManagementApplication.class, args);
	}
	
	


}
