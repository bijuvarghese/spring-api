create table carts
(
    id           BINARY(16) default (UUID_TO_BIN(UUID())) not null
        primary key,
    created_date date       default (curdate())           not null
);
create table cart_items
(
    cart_id    BINARY(16)    not null,
    product_id BIGINT        not null,
    quantity   int default 1 not null,
    id         bigint auto_increment
        primary key,
    constraint cart_items_product_unique_key
        unique (cart_id, product_id),
    constraint cart_items_carts_id_fk
        foreign key (cart_id) references carts (id)
            on delete cascade,
    constraint cart_items_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade
);


