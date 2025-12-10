
USE school_supplies_db;
-- Tạo bảng tin nhắn liên hệ
CREATE TABLE contact_messages (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  customer_id INT NULL,
                                  full_name VARCHAR(100) NOT NULL,
                                  email VARCHAR(100) NOT NULL,
                                  phone VARCHAR(20),
                                  subject VARCHAR(200) NOT NULL,
                                  message TEXT NOT NULL,
                                  status ENUM('NEW', 'READ', 'REPLIED') DEFAULT 'NEW',
                                  admin_reply TEXT,
                                  ip_address VARCHAR(45),
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  replied_at TIMESTAMP NULL,

                                  FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,

                                  INDEX idx_customer (customer_id),
                                  INDEX idx_status (status),
                                  INDEX idx_created_at (created_at),
                                  INDEX idx_email (email)

) ;

--Tạo bảng banners
CREATE TABLE banners (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(200) NOT NULL,
                         image_url VARCHAR(500) NOT NULL,
                         status BIT DEFAULT 1,

                         INDEX idx_status (status)
) ;

-- Tin nhắn xác nhận
SELECT 'Tạo bảng thành công' AS message;