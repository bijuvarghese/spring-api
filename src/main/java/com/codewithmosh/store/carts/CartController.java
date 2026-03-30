package com.codewithmosh.store.carts;

import com.codewithmosh.store.common.ErrorDto;
import com.codewithmosh.store.products.ProductNotFoundException;
import jakarta.validation.Valid;
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

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest  request
            ) {
        var cartItemDto = cartService.addItemToCart(
                cartId,
                request.getProductId(),
                request.getQuantity()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        var cartItemDto = cartService.updateCartItem(
                cartId,
                productId,
                request.getQuantity()
        );
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(cartItemDto);

    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        cartService.deleteCartItem(
                cartId,
                productId
        );
        return  ResponseEntity.noContent()
                .build();

    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteCart(
            @PathVariable UUID cartId
    ) {
        cartService.deleteCart(cartId);
        return  ResponseEntity
                .noContent()
                .build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorDto("Cart not found")
                );
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorDto("Cart is Empty")
                );
    }
}
