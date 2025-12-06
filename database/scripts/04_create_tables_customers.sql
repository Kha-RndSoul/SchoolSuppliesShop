-- ============================================
-- Script: Create Customer & Admin Tables
-- Description: Tables for customers and admins
-- Author: Person 3 (Support)
-- Dependencies: 01_create_database.sql
-- ============================================

USE school_supplies_db;

-- ============================================
-- Table: customers
-- Description: Store customer information
-- ============================================
CREATE TABLE customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           password_hash VARCHAR(255) NOT NULL,
                           full_name VARCHAR(100) NOT NULL,
                           phone VARCHAR(20),
                           address TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Indexes for performance
                           INDEX idx_email (email),
                           INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Customer accounts';

-- ============================================
-- Table: admins
-- Description: Store admin user information
-- ============================================
CREATE TABLE admins (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password_hash VARCHAR(255) NOT NULL,
                        full_name VARCHAR(100) NOT NULL,
                        role ENUM('SUPER_ADMIN', 'ADMIN', 'STAFF') DEFAULT 'STAFF',
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Indexes for performance
                        INDEX idx_username (username),
                        INDEX idx_email (email),
                        INDEX idx_role (role),
                        INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Admin users';

-- Confirmation message
SELECT 'Tables customers and admins created successfully' AS message;