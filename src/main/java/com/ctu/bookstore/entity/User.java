package com.ctu.bookstore.entity;

import com.ctu.bookstore.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String avatar;
    String email;
    String adress;


}
