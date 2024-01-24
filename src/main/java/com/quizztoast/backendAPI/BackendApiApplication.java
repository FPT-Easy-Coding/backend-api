package com.quizztoast.backendAPI;

import com.quizztoast.backendAPI.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.QuizRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth.auth_service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.quizztoast.backendAPI.model.entity.user.Role.ADMIN;

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
//                    .firstname("Admin")
//                    .lastname("Demo")
//                    .email("admin2@gmail.com")
//                    .password("password")
//                    .role(ADMIN)
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

        };


