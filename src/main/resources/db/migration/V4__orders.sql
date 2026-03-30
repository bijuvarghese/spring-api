create table orders
(
    customer_id BIGINT                         not null,
    status      VARCHAR(20)                  not null,
    created_at  DATETIME default current_timestamp not null,
    total_price DECIMAL(10, 2)               not null,
    id          BIGINT auto_increment
        primary key,
    constraint orders_customer_id_fk
        foreign key (customer_id) references users(id)
);

create table order_items
(
    order_id    BIGINT           not null,
    product_id  BIGINT           not null,
    unit_price  DECIMAL(10, 2) not null,
    quantity    int default 1  not null,
    total_price DECIMAL(10, 2) not null,
    id          BIGINT auto_increment
        primary key,
    constraint order_items_orders_id_fk
        foreign key (order_id) references orders (id),
    constraint order_items_product_id_fk
            foreign key (product_id) references products (id)
);


