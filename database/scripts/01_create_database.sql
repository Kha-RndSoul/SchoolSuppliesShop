

-- Drop DB nếu nó đã tồn tại
DROP DATABASE IF EXISTS school_supplies_db;

-- Tạo DB
CREATE DATABASE school_supplies_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_520_ci;

-- Use DB
USE school_supplies_db;

-- Tin nhắn xác nhận
SELECT 'Tạo DB thành công' AS message;