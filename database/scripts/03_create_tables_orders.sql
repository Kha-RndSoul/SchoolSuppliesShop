-- 1. Bảng orders
create table orders (
                        id int auto_increment primary key,
                        customer_id int not null,
                        order_code varchar(50) not null unique,
                        order_status varchar(20) default 'pending',
                        total_amount decimal(15, 2) not null default 0,
                        shipping_address varchar(500),
                        shipping_phone varchar(20),
                        note text,
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp on update current_timestamp,
                        foreign key (customer_id) references customers(id)
);

-- 2. Bảng order_details
create table order_details (
                               id int auto_increment primary key,
                               order_id int not null,
                               product_id int not null,
                               quantity int not null default 1,
                               unit_price decimal(15, 2) not null,
                               subtotal decimal(15, 2) not null,
                               created_at timestamp default current_timestamp,
                               foreign key (order_id) references orders(id) on delete cascade,
                               foreign key (product_id) references products(id)
);

-
-- 3. Bảng cart_items

create table cart_items (
                            id int auto_increment primary key,
                            customer_id int not null,
                            product_id int not null,
                            quantity int not null default 1,
                            created_at timestamp default current_timestamp,
                            updated_at timestamp default current_timestamp on update current_timestamp,
                            foreign key (customer_id) references customers(id) on delete cascade,
                            foreign key (product_id) references products(id) on delete cascade,
                            unique key unique_cart_item (customer_id, product_id)
);


-- 4. Bảng order_coupons

create table order_coupons (
                               id int auto_increment primary key,
                               order_id int not null,
                               coupon_id int not null,
                               coupon_code varchar(50) not null,
                               discount_amount decimal(15, 2) not null,
                               applied_at timestamp default current_timestamp,
                               foreign key (order_id) references orders(id) on delete cascade,
                               foreign key (coupon_id) references coupons(id)
);