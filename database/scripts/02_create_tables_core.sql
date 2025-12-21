USE school_supplies_db;

-- Bảng categories
CREATE TABLE categories (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            category_name VARCHAR(100) NOT NULL,
                            image_url VARCHAR(500),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            INDEX idx_category_name (category_name)
);

-- Bảng brands
CREATE TABLE brands (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        brand_name VARCHAR(100) NOT NULL,
                        logo_url VARCHAR(500),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        INDEX idx_brand_name (brand_name)
);

-- Bảng products
CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          product_name VARCHAR(200) NOT NULL,
                          description TEXT,
                          category_id INT NOT NULL,
                          brand_id INT NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          sale_price DECIMAL(10, 2),
                          stock_quantity INT DEFAULT 0,
                          sold_count INT DEFAULT 0,
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_id) REFERENCES categories(id),
                          FOREIGN KEY (brand_id) REFERENCES brands(id),
                          INDEX idx_category (category_id),
                          INDEX idx_brand (brand_id),
                          INDEX idx_price (price)
);

-- Bảng product_images
CREATE TABLE product_images (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                product_id INT NOT NULL,
                                image_url VARCHAR(500) NOT NULL,
                                is_primary BOOLEAN DEFAULT FALSE,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                INDEX idx_product (product_id)
);

SELECT 'Tạo 4 bảng core thành công!' AS message;