USE school_supplies_db;
-- Chèn dữ liệu mẫu vào bảng products
INSERT INTO products (
    id,
    product_name,
    description,
    category_id,
    brand_id,
    price,
    sale_price,
    stock_quantity,
    sold_count,
    image_url,
    is_active
) VALUES
      (
          1,
          'Combo Bút chì gỗ Flexoffice',
          'Nét đậm, lướt rất nhẹ nhàng trên bề mặt viết, dùng để đánh bóng các bức vẽ, đạt đến nhiều mức độ sáng tối khác nhau.',
          1,
          1,
          60000.00,
          48000.00,
          12,
          50,
          'https://cdn.hstatic.net/products/1000230347/fo-gp009_xk_47ef83928c914bbaad101ed556a2340e.jpg',
          TRUE
      ),
      (
          2,
          'Bút lông dầu Flexoffice',
          'Màu mực đậm tươi, mực ra đều và liên tục, Độ bám dính của mực tốt trên các vật liệu:  Giấy, gỗ, da, nhựa, thủy tinh, kim loại, gốm.',
          1,
          1,
          13000.00,
          9000.00,
          10,
          30,
          'https://cdn.hstatic.net/products/1000230347/fo-pm06_xk_88ba3eb9333848b4a320c854d2ed683a.jpg',
          TRUE
      ),
      (
          3,
          'Bút gel Fasgel Thiên Long',
          'Nét viết êm trơn, mực ra đều, liên tục. Ngòi bút cao cấp, sang trọng. Thiết kế tinh vi, nghệ thuật.',
          1,
          2,
          7000.00,
          6000.00,
          20,
          20,
          'https://cdn.hstatic.net/products/1000230347/artboard_1_copy_dc035789d53b4ef48cc60a0f39eca851.jpg',
          TRUE
      ),
      (
          4,
          'Combo Bút lông dầu Flexoffice',
          'Màu mực đậm tươi, mực ra đều và liên tục, Độ bám dính của mực tốt trên các vật liệu:  Giấy, gỗ, da, nhựa, thủy tinh, kim loại, gốm.',
          1,
          1,
          30000.00,
          27000.00,
          3,
          10,
          'https://cdn.hstatic.net/products/1000230347/fo-pm06_xk_e33fa07b6bd545fab5e2f01dfc1880db.jpg',
          TRUE
      ),
      (
          5,
          'Combo Bút gel Fasgel Thiên Long',
          'Bộ combo bút gel Fasgel với nhiều màu sắc, viết êm trơn, mực ra đều.',
          1,
          2,
          21000.00,
          18000.00,
          3,
          15,
          'https://cdn.hstatic.net/products/1000230347/artboard_1_copy_dc035789d53b4ef48cc60a0f39eca851.jpg',
          TRUE
      ),
      (
          6,
          'Bút chì gỗ Flexoffice',
          'Nét đậm, lướt rất nhẹ nhàng trên bề mặt viết, dùng để đánh bóng các bức vẽ, đạt đến nhiều mức độ sáng tối khác nhau.',
          1,
          1,
          5000.00,
          4000.00,
          50,
          100,
          'https://cdn.hstatic.net/products/1000230347/fo-gp009_xk_47ef83928c914bbaad101ed556a2340e.jpg',
          TRUE
      );
-- Tin nhắn xác nhận
SELECT 'Dữ liệu sản phẩm đã tải thành công' AS message;