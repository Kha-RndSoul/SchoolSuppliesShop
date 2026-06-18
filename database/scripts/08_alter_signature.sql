USE school_supplies_db;

-- Tạo bảng user_keys
CREATE TABLE user_keys (
                           id               INT PRIMARY KEY AUTO_INCREMENT,
                           file_name        VARCHAR(255) NULL,
                           customer_id      INT          NOT NULL,
                           public_key       TEXT         NOT NULL,
                           is_active        BOOLEAN      DEFAULT TRUE,
                           source           ENUM('GENERATED', 'UPLOADED') DEFAULT 'GENERATED',
                           created_at       DATETIME     DEFAULT NOW(),
                           reported_lost_at DATETIME     NULL,
                           FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);
--  thêm cột vào orders
ALTER TABLE orders
    ADD COLUMN order_hash  VARCHAR(512) NULL,
    ADD COLUMN signature   TEXT         NULL,
    ADD COLUMN key_id      INT          NULL,
    ADD COLUMN is_verified TINYINT      DEFAULT 0,
    ADD CONSTRAINT fk_orders_key
        FOREIGN KEY (key_id) REFERENCES user_keys(id);
