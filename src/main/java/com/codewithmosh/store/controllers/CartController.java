package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartItemMapper;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.mappers.ProductMapperImpl;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    private final ProductMapperImpl productMapperImpl;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;

    @PostMapping
    public ResponseEntity<?> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cart = new Cart();
        cartRepository.save(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartMapper.toDto(cart));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest  request,
            UriComponentsBuilder uriBuilder
            ) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        cart.getCartItems().forEach(cartItem -> {
            System.out.println("Product ID CART P ID:" + cartItem.getProduct().getId());
            System.out.println("Product Name R:" + cartItem.getProduct().getName());
            System.out.println("Product ID R:" + request.getProductId());
        });


        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);
        System.out.println(cartItem);
        if (cartItem == null) {
            cartItem = cartItemMapper.toEntity(request);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }
        cartRepository.save(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartId).toUri();
        return ResponseEntity.created(uri).body(cartItemMapper.toDto(cartItem));
    }

}
