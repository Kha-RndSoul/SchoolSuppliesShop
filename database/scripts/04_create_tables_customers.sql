USE school_supplies_db;

--Tạo bảng customers
CREATE TABLE customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           password VARCHAR(100) NOT NULL,
                           full_name VARCHAR(100) NOT NULL,
                           phone VARCHAR(20),
                           address TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                           INDEX idx_email (email),
                           INDEX idx_created_at (created_at)
) ;

-- Tạo bảng admins
CREATE TABLE admins (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        full_name VARCHAR(100) NOT NULL,
                        role ENUM('SUPER_ADMIN', 'ADMIN', 'STAFF') DEFAULT 'STAFF',
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        INDEX idx_username (username),
                        INDEX idx_email (email),
                        INDEX idx_role (role),
                        INDEX idx_active (is_active)
) ;

-- Tin nhắn xác nhận
SELECT 'Tạo bảng customers và admin thành công' AS message;