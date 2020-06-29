package com.my.poc.springwebfluxreactiveprog;

import com.my.poc.position.PositionStore;
import com.my.poc.positionstoreimpl.PositionStoreImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringWebfluxReactiveProgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxReactiveProgApplication.class, args);
	}

}
