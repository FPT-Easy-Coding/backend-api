package com.quizztoast.backendAPI.model.inClass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Class {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int class_id;

  @Column(name= "user_id" ,nullable = false)
  private Long user_id;
  @Column(name= "class_name" ,nullable = false,length = 255)
  private String class_name;
  @Column(name= "slug_code" ,nullable = false,unique = true,length = 255)
  private String slug_code;
  @Column(name= "created_at" ,nullable = false)
  private LocalDateTime created_at;


}
