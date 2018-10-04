package com.silvio.algamoneyapi;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.silvio.algamoneyapi.config.property.AlgaMoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(AlgaMoneyApiProperty.class)
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamoneyApiApplication.class, args);

		System.out.println(Locale.getDefault());
	}
}
