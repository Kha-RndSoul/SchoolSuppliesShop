-- ============================================
-- Script: Create Database
-- Description: Drop and create school_supplies_db database
-- Author: Person 3 (Support)
-- ============================================

-- Drop database if exists (for clean setup)
DROP DATABASE IF EXISTS school_supplies_db;

-- Create database with UTF-8 support
CREATE DATABASE school_supplies_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE school_supplies_db;

-- Confirmation message
SELECT 'Database school_supplies_db created successfully' AS message;