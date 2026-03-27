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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product, Integer quantity) {
        var cartItem = getItem(product.getId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            items.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void removeAll() {
        items.clear();
    }
}
