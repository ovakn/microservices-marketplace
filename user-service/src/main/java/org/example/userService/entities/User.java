package org.example.userService.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.userService.entities.enums.UserRole;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @SequenceGenerator(
            name = "seq_generator",
            sequenceName = "seq_generator",
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_generator"
    )
    @Column(name = "_id")
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false, unique = true)
    String phoneNumber;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String hashPassword;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserRole userRole;
    @Embedded
    Address address;
}