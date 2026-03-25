package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final ProductRepository productRepository;

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
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }
        cartRepository.save(cart);
        CartItemDto cartItemDto = cartMapper.toDto(cartItem);
        cartItemDto.setCartId(cart.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

}
