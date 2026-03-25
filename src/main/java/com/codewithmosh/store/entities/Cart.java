package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ColumnDefault("(curdate())")
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }
}
