package com.ctu.bookstore.entity;

import com.ctu.bookstore.entity.display.Cart;
import com.ctu.bookstore.entity.payment.InforCheckout;
import com.ctu.bookstore.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

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
    String firstname;
    String lastname;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate dob;
    String avatar;
    String email;
    String phoneNumber;
    String adress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "checkout_id") // tạo cột checkout_id trong bảng user
    InforCheckout inforCheckout;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    Cart cart;
    @ManyToMany
    Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", adress='" + adress + '\'' +
                ", roles=" + roles +
                '}';
    }
}
