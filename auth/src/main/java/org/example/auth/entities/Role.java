package org.example.auth.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

@Data
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements GrantedAuthority {
    @Id
    @SequenceGenerator(name = "seq_generator",
            sequenceName = "seq_generator",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seq_generator")
    @Column(name = "_id")
    Long id;
    @Column(nullable = false,
            unique = true,
            length = 32)
    String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    Set<User> users;


    @Override
    public String getAuthority() {
        return null;
    }
}