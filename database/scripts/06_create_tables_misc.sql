-- ============================================
-- Script: Create Miscellaneous Tables
-- Description: Tables for contact messages and banners
-- Author: Person 3 (Support)
-- Dependencies: 01_create_database. sql, 04_create_tables_customers.sql
-- ============================================

USE school_supplies_db;

-- ============================================
-- Table: contact_messages
-- Description: Store contact form submissions
-- ============================================
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

    -- Foreign key (nullable - guests can send messages)
                                  FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,

    -- Indexes for performance
                                  INDEX idx_customer (customer_id),
                                  INDEX idx_status (status),
                                  INDEX idx_created_at (created_at),
                                  INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Contact form messages from customers or guests';

-- ============================================
-- Table: banners
-- Description: Store homepage banner/slider images
-- ============================================
CREATE TABLE banners (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(200) NOT NULL,
                         image_url VARCHAR(500) NOT NULL,
                         status BIT DEFAULT 1,

    -- Index for performance
                         INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Homepage banners and sliders';

-- Confirmation message
SELECT 'Tables contact_messages and banners created successfully' AS message;