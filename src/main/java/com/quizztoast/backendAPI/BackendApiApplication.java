package com.quizztoast.backendAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService authenticationService
//    ){
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Demo")
//                    .email("admin2@gmail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());
//        };
//    }
}
