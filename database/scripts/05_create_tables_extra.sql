USE school_supplies_db;

-- Bảng product_reviews
CREATE TABLE product_reviews (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 product_id INT NOT NULL,
                                 customer_id INT NOT NULL,
                                 rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
                                 comment TEXT,
                                 status BIT DEFAULT 1,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                 FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
                                 INDEX idx_product (product_id),
                                 INDEX idx_customer (customer_id)
);

-- Bảng coupons
CREATE TABLE coupons (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         coupon_code VARCHAR(50) NOT NULL UNIQUE,
                         discount_type ENUM('PERCENTAGE', 'FIXED_AMOUNT') NOT NULL,
                         discount_value DECIMAL(10, 2) NOT NULL,
                         min_order_amount DECIMAL(10, 2) DEFAULT 0,
                         max_uses INT DEFAULT 0,
                         used_count INT DEFAULT 0,
                         start_date TIMESTAMP NOT NULL,
                         end_date TIMESTAMP NOT NULL,
                         is_active BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         INDEX idx_coupon_code (coupon_code)
);

-- Bảng payment_transactions
CREATE TABLE payment_transactions (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      order_id INT NOT NULL,
                                      payment_method ENUM('COD', 'BANK_TRANSFER', 'MOMO', 'VNPAY', 'ZALOPAY') NOT NULL,
                                      payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
                                      amount DECIMAL(10, 2) NOT NULL,
                                      transaction_note TEXT,
                                      paid_at TIMESTAMP NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                                      INDEX idx_order (order_id)
);

SELECT 'Tạo 3 bảng extra thành công!' AS message;