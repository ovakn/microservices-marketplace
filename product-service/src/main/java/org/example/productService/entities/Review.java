package org.example.productService.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review implements Serializable {
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
    @ManyToOne
    Product product;
    @Column(nullable = false)
    Long userId;
    @Column(nullable = false)
    Integer rating;
    @Column
    String comment;
    @Column(nullable = false)
    LocalDateTime createdAt;
}