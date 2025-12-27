USE school_supplies_db;

CREATE TABLE customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           password_hash VARCHAR(100) NOT NULL,
                           full_name VARCHAR(100) NOT NULL,
                           phone VARCHAR(20),
                           address TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE admins (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password_hash VARCHAR(100) NOT NULL,
                        full_name VARCHAR(100) NOT NULL,
                        role ENUM('SUPER_ADMIN', 'ADMIN', 'STAFF') DEFAULT 'STAFF',
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT 'Tạo bảng customers và admins thành công' AS message;