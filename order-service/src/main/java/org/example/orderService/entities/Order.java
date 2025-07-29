package org.example.orderService.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderService.entities.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
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
    Long userId;
    @Column(nullable = false)
    LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;
    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<OrderItem> items;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}