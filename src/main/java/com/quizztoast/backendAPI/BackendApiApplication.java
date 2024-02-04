package com.quizztoast.backendAPI;


import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth.auth_service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BackendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }
//        @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService authenticationService
//    ){
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstName("Admin")
//                    .lastName("Demo")
//                    .email("admin2@gmail.com")
//                    .password("password")
//
//                    .build();
//            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());
//        };
//    }
      //test quiz
//            var quiz = Quiz.builder()
//                    .quiz_id(1)
//                    .quiz_name("test Quiz")
//                    .rate(5)
//                    .class_id(12)
//                    .created_at(LocalDateTime.parse("2024-01-22 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//                    .build();
//            quizrepo.save(quiz);
//
//        };

}
