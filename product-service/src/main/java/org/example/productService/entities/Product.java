package org.example.productService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product implements Serializable {
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
    @Min(
            value = 0,
            message = "Product price must be positive"
    )
    @Column(nullable = false)
    Double price;
    @Column(nullable = false)
    Integer stock;
    @Transient
    Double averageRating;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    List<Review> reviews;
}