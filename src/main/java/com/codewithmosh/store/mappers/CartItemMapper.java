package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "productId", target = "product.id")
    CartItem toEntity(AddItemToCartRequest addItemToCartRequest);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "cart.id", target = "cartId")
    CartItemDto toDto(CartItem cartItem);
}
