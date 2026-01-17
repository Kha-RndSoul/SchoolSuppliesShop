USE school_supplies_db;



-- chen dữ liệu mẫu vào bảng coupons
INSERT INTO coupons (
    id,
    coupon_code,
    image_url,
    discount_type,
    discount_value,
    min_order_amount,
    max_uses,
    used_count,
    start_date,
    end_date,
    is_active,
    created_at
) VALUES
-- 1. Giảm 10% cho đơn hàng đầu tiên
(1, 'HELLOSALE', 'src/main/webapp/assets/images/coupon/shocksale.jpg', 'PERCENTAGE', 10.00, 0.00, 100, 5, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 1, NOW()),

-- 2. Giảm 20k cho đơn từ 200k
(2, 'GIAM20K', 'src/main/webapp/assets/images/coupon/voucher.jpg', 'FIXED_AMOUNT', 20000.00, 200000.00, 50, 12, '2026-01-15 00:00:00', '2026-02-15 23:59:59', 1, NOW()),

-- 3. Mã tri ân khách hàng cũ (15%)
(3, 'THANKS15', 'src/main/webapp/assets/images/coupon/funThousand.jpg', 'PERCENTAGE', 15.00, 500000.00, 200, 45, '2026-01-01 00:00:00', '2026-06-30 23:59:59', 1, NOW()),

-- 4. Mã Flash Sale
(4, 'FLASH99', 'src/main/webapp/assets/images/coupon/flashsale.jpg', 'FIXED_AMOUNT', 99000.00, 100000.00, 10, 10, '2025-12-31 20:00:00', '2026-12-31 23:59:59', 1, '2025-12-31 10:00:00'),

-- 5. Mã giảm giá Valentine sắp tới
(5, 'LOVE2026', 'src/main/webapp/assets/images/coupon/chirismat.jpg', 'PERCENTAGE', 14.00, 140000.00, 500, 0, '2026-02-10 00:00:00', '2026-02-15 23:59:59', 1, NOW()),

-- 6. Mã giảm giá tiểu vương quốc Raumania
(6, 'RAUMA36', 'src/main/webapp/assets/images/coupon/gachasale.jpg', 'FIXED_AMOUNT', 36000.00, 0.00, 3600, 36, '2026-01-17 00:00:00', '2026-12-31 23:59:59', 1, NOW());
-- Chèn dữ liệu mẫu vào bảng admins
INSERT INTO admins (
    id,
    username,
    email,
    password_hash,
    full_name,
    role,
    is_active,
    created_at
) VALUES (
             1,
             'admin',
             'admin@shop.com',
             '$2a$12$sQMl7PEeVK6QNG2x2ICd1.baC0eBgreX9KCA87QyX7mM5JZNWI9su',
             'Nguyễn Tuấn Kha',
             'SUPER_ADMIN',
             1,
             '2026-01-17 17:46:03'
         );

-- Chèn dữ liệu mẫu vào bảng banners
INSERT INTO banners (id, title, image_url, status)
VALUES
    (1, 'Cùng sáng tạo với WONDERLINE', 'src/main/webapp/assets/images/banners/banner1.png', TRUE),
    (2, 'Tự tin học tốt cùng SCHOOLLINE', 'src/main/webapp/assets/images/banners/banner2.png', TRUE),
    (3, 'Giấy in cao cấp, giá hời quá xịn', 'src/main/webapp/assets/images/banners/banner3.png', TRUE),
    (4, 'Học cụ xinh-Rinh Deal xịn', 'src/main/webapp/assets/images/banners/banner4.png', TRUE),
    (5, 'VIP AVAKIDS', 'src/main/webapp/assets/images/banners/banner5.png', TRUE),
    (6, 'Trao quà tặng-Gửi yêu thương', 'src/main/webapp/assets/images/banners/banner6.png', TRUE),
    (7, 'Tết Việt', 'src/main/webapp/assets/images/banners/banner7.png', TRUE)
;


-- Chèn dữ liệu mẫu vào bảng contact_messages
INSERT INTO contact_messages (id, customer_id, full_name, email, phone, subject, message, status, admin_reply, ip_address, created_at, replied_at)
VALUES
    (1, 1, 'Nguyễn Văn A', 'customer1@email.com', '0901234567', 'Hỏi về sản phẩm', 'Sản phẩm balo có màu xanh không?', 'NEW', NULL, '192.168.1.1', NOW(), NULL),
    (2, 2, 'Trần Thị B', 'customer2@email.com', '0912345678', 'Vấn đề giao hàng', 'Đơn hàng của tôi chưa nhận được', 'PROCESSING', NULL, '192.168.1.2', NOW(), NULL),
    (3, 3, 'Lê Văn C', 'guest@email.com', '0923456789', 'Yêu cầu hợp tác', 'Tôi muốn trở thành đối tác', 'NEW', NULL, '192.168.1.3', NOW(), NULL)
;
--Chèn dữ liệu vào bảng categories
INSERT INTO categories (id, category_name, image_url, created_at) VALUES
    (1, 'Bút viết', 'src/main/webapp/assets/images/categories/Bút viết.png', NOW()),
    (2, 'Sổ vở', 'src/main/webapp/assets/images/categories/Sổ vở.png', NOW()),
    (3, 'Đồ dùng vẽ', 'src/main/webapp/assets/images/categories/Đồ dùng vẽ.png', NOW()),
    (4, 'Balo & cặp', 'src/main/webapp/assets/images/categories/Balo & cặp.png', NOW()),
    (5, 'Máy tính ', 'src/main/webapp/assets/images/categories/Máy tính.png', NOW()),
    (6, 'Đèn học', 'src/main/webapp/assets/images/categories/Đèn học.png', NOW()),
    (7, 'Giấy', 'src/main/webapp/assets/images/categories/Giấy in.png', NOW()),
    (8, 'Thước,compa & tẩy', 'src/main/webapp/assets/images/categories/Giấy in.png', NOW())
;
-- Chèn dữ liệu vào bảng brands
INSERT INTO brands (id, brand_name, created_at) VALUES

    (1, 'Thiên Long', NOW()),
    (2, 'Flexoffice', NOW()),
    (3, 'Điểm 10', NOW()),
    (4, 'Campus', NOW()),
    (5, 'Hồng Hà', NOW()),
    (6, 'Hải Tiến', NOW()),
    (7, 'Colokit', NOW()),
    (8, 'Deli', NOW()),
    (9, 'Miti', NOW()),
    (10, 'Mr.Vui', NOW()),
    (11, 'Flexio', NOW()),
    (12, 'Casio', NOW()),
    (13, 'Rạng Đông', NOW()),
    (14, 'Panasonic', NOW()),
    (15, 'Double A', NOW()),
    (16, 'IK Plus', NOW()),
    (17, 'Jamlos', NOW()),
    (18, 'King Jim', NOW())
;

-- Chèn dữ liệu  vào bảng products
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
    is_active
) VALUES
      --Insert sản phẩm của Dũng
    (1, 'Bút gel Doraemon TL', 'Bút có thiết kế đơn giản nhưng khoa học, thân tròn, nhỏ rất phù hợp với tay cầm của học sinh tiểu học.Thân bút bằng nhựa trắng đục, in transfer fllm hình nhân vật Doraemon rất thu hút. Mực màu đậm và tươi sáng, viết êm trơn, ra đều và liên tục', 1, 1, 10000.00, 8000.00, 50, 100, TRUE),
    (2, 'Bút gel B TL', 'Kiểu dáng hiện đại, dắt bút bằng kim loại sáng bóng sang trọng rất phù hợp với khách hàng là nhân viên văn phòng. ', 1, 1, 13000.00, 11000.00, 40, 50, TRUE),
    (3, 'Bút gel Fasgel TL', 'Nét viết êm tru,mực ra đều,liên tục. Ngòi bút cao cấp,sang trọng. Thiết Kế tinh vi,nghệ thuật', 1, 1, 7000.00, 6000.00, 2, 20, TRUE),
    (4, 'Bút gel Yoyee TL', 'Bút viết mượt, nét đều, mực khô nhanh — lý tưởng cho học sinh, sinh viên và nhân viên văn phòng muốn chữ rõ ràng, không lem.', 1, 1, 6000.00, 5000.00, 9, 10, TRUE),
    (5, 'Bút gel Demon Slayer TL', 'Đầu bút bền, viết êm, phù hợp học sinh đam mê truyện tranh demon slayer', 1, 1, 12000.00, 10000.00, 15, 15, TRUE),
    (6, 'Bút gel g1 TL', 'Bút có thiết kế đơn giản nhưng khoa học, thân tròn, nhỏ rất phù hợp với tay cầm của học sinh tiểu học.  Thân và nắp bút bằng nhựa trắng đục, in nhũ kim loại.  Mực màu đậm và tươi sáng, viết êm trơn, ra đều và liên tục.', 1, 1, 11000.00, 10000.00, 50, 70, TRUE),
    (7, 'Bút gel Akola TL', 'Thiết kế gọn nhẹ, cầm êm với grip chống trượt;đầu bi bền, thay ruột dễ dàng giúp sử dụng lâu dài và tiết kiệm.', 1, 1, 8000.00, 7000.00, 12, 5, TRUE),
    (8, 'Bút chì gỗ điểm 10', 'Khi sử dụng, ngòi không bị gãy vụn, ít hao, dễ xóa sạch bằng gôm, đặc biệt hạn chế làm bẩn tay và quần áo.được thiết kế nhỏ gọn thân thẳng tròn giúp bạn dễ dàng cầm nắm và điều chỉnh nét vẽ, đồng thời, bút còn dễ cất giữ trong bóp,giỏ xách khi đi học,tiện dùng khi cần.', 1, 3, 4000.00, 3000.00, 300, 400, TRUE),
    (9, 'Bút chì gỗ Neon CLK', 'Nét đậm, để lại nhiều than chì trên giấy,lướt rất nhẹ nhàng trên bề mặt viết', 1, 7, 2500.00, 2000.00, 100, 50, TRUE),
    (10, 'Bút chì gỗ HB Flexoffice', 'Nét đậm, lướt rất nhẹ nhàng trên bề mặt viết,dùng để đánh bóng các bức vẽ, đạt đến nhiều mức độ sáng tối khác nhau.', 1, 1, 5000.00, 4000.00, 48, 100, TRUE),
    (11, 'Bút chì mỹ thuật TL', 'Bút chì mỹ thuật Thiên Long thích hợp cho các hoạt động như ghi chép, vẽ nháp, học tập.', 1, 3, 5000.00, 4000.00, 100, 50, TRUE),
    (12, 'Bút chì bấm điểm 10', 'Cơ chế bấm rất nhạy, rất nhẹ tay,sử dụng min chì thông dụng trên thị trường, thay ruột chì dễ dàng', 1, 3, 8000.00, 7000.00, 100, 88, TRUE),
    (15, 'Bút chì nhựa TL', 'Được sử dụng phổ biến tại các văn phòng, công sở và hữu ích cho học sinh, sinh viên.  Bút chì nhỏ gọn, có tính ứng dụng cao và màu viết đẹp nên được tin dùng trong thời gian vừa qua.  Ruột bút HB với ưu điểm cho nét đậm, ngòi mềm, là loại ruột chì khá phổ biến.', 1, 1, 2000.00, 1500.00, 100, 200, TRUE),
    (16, 'Ống mực điểm 10', 'Được làm từ nhựa trong, mềm, dễ sử dụng, phù hợp cho các loại bút máy chuyên sử dụng ống mực và một số loại bút chuyên sử dụng ống mực khác có trên thị trường.', 1, 3, 5000.00, 4000.00, 6, 2, TRUE),
    (17, 'Mực bút máy điểm 10', 'Mực không lem trên giấy,thích hợp cho các loại bút máy', 1, 3, 6000.00, 5000.00, 20, 5, TRUE),
    (18, 'Bút máy luyện chữ điểm 10', 'Hộp bút được thiết kế sang trọng và tinh tế,đầu bút nghệ thuật cho nét viết siêu đẹp', 1, 3, 30000.00, 24000.00, 50, 100, TRUE),
    (19, 'Bộ ngòi bút máy Iridium điểm 10', 'Ngòi viết được cấu tạo bằng vật liệu thép cao cấp, không gỉ và xi mạ màu vàng sang trọng. ', 1, 3, 14000.00, 12000.00, 6, 5, TRUE),
    (20, 'Bút xóa 12ml TL', 'Kiểu dáng thân dẹp, vừa tầm tay, thuận tiện khi sử dụng.  Cán bằng nhựa màu xanh lá thể hiện sự trẻ trung, năng động.  Đầu bút bằng kim loại có lò xo đàn hồi tốt. ', 1, 1, 26000.00, 24000.00, 70, 100, TRUE),
    (21, 'Bút xóa 7ml TL', 'Có kiểu dáng thân trụ tròn, vừa tầm tay, thuận tiện khi sử dụng.  Cán bằng nhựa màu xanh lá thể hiện sự trẻ trung, năng động. Đầu bút bằng kim loại có lò xo đàn hồi tốt.', 1, 1, 19000.00, 17000.00, 50, 70, TRUE),
    (22, 'Bút xóa FO FL', 'Xóa nhanh khô,bút ít bị tắc mực, độ che phủ bề mặt tốt hơn và mau khô, giúp cho chữ viết rõ ràng, không bị lem, nhòe. ', 1, 1, 24000.00, 22000.00, 50, 100, TRUE),
    (23, 'Bút xóa kéo FO FL', 'Là kết hợp của sự tiện lợi, nhanh chóng, vô cùng an toàn và thân thiện với môi trường. Sản phẩm được thiết kế trẻ trung, năng động, màu sắc tươi sáng, đây là sản phẩm rất phù hợp cho giới văn phòng hiện đại.', 1, 1, 17000.00, 15000.00, 40, 50, TRUE),
    (24, 'Bút xóa Plus FL', 'Có kiểu dáng thân dẹp, vừa tầm tay, thuận tiện khi sử dụng. Cán bằng nhựa màu xanh dương thể hiện sự trẻ trung, năng động. Đầu bút bằng kim loại có lò xo đàn hồi tốt.', 1, 1, 20000.00, 18000.00, 30, 20, TRUE),
    (25, 'Bút xóa điểm 10', 'Có kiểu dáng thân tròn, vừa tầm tay, thuận tiện khi sử dụng.  Cán bằng nhựa đủ màu thể hiện sự trẻ trung, năng động.  Đầu bút bằng kim loại có lò xo đàn hồi tốt.', 1, 3, 15000.00, 14000.00, 50, 50, TRUE),
    (26, 'Bút bi Buddies TL','Bút viết mượt, nét đều, mực khô nhanh,hình ãnh chú chó đáng yêu đồng hành cùng các học sinh', 1, 1, 5000.00, 4500.00, 50, 100, TRUE),
    (27, 'Bút bi b2 TL','Bút viết mượt, nét đều, mực khô nhanh — lý tưởng cho học sinh, sinh viên và nhân viên văn phòng muốn chữ rõ ràng, không lem. ', 1, 1, 6000.00, 5000.00, 900, 1500, TRUE),
    (28, 'Bút bi 1. 0mm TL','Bút viết mượt, nét đều, mực khô nhanh — lý tưởng cho học sinh, sinh viên và nhân viên văn phòng muốn chữ rõ ràng, không lem. ', 1, 1, 6000.00, 5000.00, 400, 500, TRUE),
    (29, 'Bút bi điểm 10','Bút có thiết kế tối giản, nhưng tinh tế và ấn tượng. Toàn bộ thân bút làm từ nhựa màu trong, phối hợp hoàn hảo với màu ruột bút bên trong.', 1, 3, 6000.00, 5000.00, 500, 1000, TRUE),
    (30, 'Bút bi eco g1 TL','Thành phần từ vỏ cám trấu, sử dụng bút ECO Style là góp phần đồng hành cùng Thiên Long bảo vệ môi trường xanh. ', 1, 1, 13000.00, 11000.00, 100, 200, TRUE),
    (31, 'Bút bi eco g2 TL','Bản nâng cấp từ g1, thành phần từ vỏ cám trấu, sử dụng bút ECO Style là góp phần đồng hành cùng Thiên Long bảo vệ môi trường xanh.', 1, 1, 14000.00, 12000.00, 70, 100, TRUE),
    (32, 'Bút bi đế cắm eco TL','Thành phần từ vỏ cám trấu, sử dụng bút ECO Style là góp phần đồng hành cùng Thiên Long bảo vệ môi trường xanh. ', 1, 1, 22000.00, 20000.00, 50, 50, TRUE),
    (33, 'Bút bi đế cắm vp TL','Bút chuyên để trên bàn làm việc nơi đông người như bàn tiếp tân, bưu điện, ngân hàng, siêu thị…,có băng keo 2 mặt phía dưới đế cắm, giúp giữ sản phẩm không xê dịch khi viết.', 1, 1, 25000.00, 23000.00, 50, 100, TRUE),
    (34, 'Hộp 7 bút lông bảng ak TL','Thiết kế thông minh, dễ dàng cố định trên mặt phẳng.  Mực ra đều, màu sắc tươi sáng, nhanh khô và dễ dàng xóa sạch. Có thể sử dụng trên bảng trắng, thuỷ tinh và những bề mặt nhẵn bóng. ', 1, 1, 40000.00, 36000.00, 40, 30, TRUE),
    (35, 'Hộp 2 bút lông vườn xanh TL','là dòng bút đánh dấu cây trồng chuyên dụng dành cho nông nghiệp, mang đến giải pháp tối ưu cho người làm vườn, nông dân và những người yêu cây cảnh.  Với sứ mệnh hỗ trợ nhà vườn quản lý và phân loại cây trồng hiệu quả, Bút Vườn Xanh giải quyết hoàn toàn các vấn đề thường gặp như mực dễ phai, dễ lem, khó bám dính trên một số bề mặt. ', 1, 1, 30000.00, 26000.00, 20, 20, TRUE),
    (36, 'Bút lông dầu Flexoffice','Màu mực đậm tươi, mực ra đều và liên tục,độ bám dính của mực tốt trên các vật liệu:  Giấy, gỗ, da, nhựa, thủy tinh, kim loại, gốm,..', 1, 2, 13000.00, 9000.00, 9, 30, TRUE),
    (37, 'Combo 3 màu bút lông dầu Flexoffice','Màu mực đậm tươi, mực ra đều và liên tục,độ bám dính của mực tốt trên các vật liệu: Giấy, gỗ, da, nhựa, thủy tinh, kim loại, gốm,..', 1, 2, 30000.00, 27000.00, 50, 70, TRUE),
    (38, 'Combo 5 bút dạ quang G1 TL','Kiểu dáng thon gọn, trẻ trung Màu dạ quang mạnh, không làm lem nét chữ của mực khi viết chồng lên và không để lại vết khi qua photocopy đây là đặt điểm vượt trội của bút dạ quang. ', 1, 1, 45000.00, 40000.00, 45, 44, TRUE),
    (39, 'Combo 5 bút dạ quang G2 TL','Sản phẩm được sản xuất theo công nghệ hiện đại, đạt tiêu chuẩn chất lượng quốc tế.Lượng mực nhiều, tăng thời gian sử dụng.', 1, 1, 55000.00, 50000.00, 75, 50, TRUE),
    (40, 'Vở 4 ô ly 80 trang Funny HH','Thiết kế vui nhộn, màu sắc tươi sáng và hình nhân vật ngộ nghĩnh, đáng yêu, phù hợp với học sinh tiểu học. Giấy viết không cợn, không nhòe, không thấm mực sang trang sau, tốt cho người sử dụng đặc biệt là trẻ nhỏ.', 2, 5, 15000.00, 12000.00, 200, 123, TRUE),
    (41, 'Vở 4 ô ly 80 trang Trạng Nguyên HH', 'Thiết kế vui nhộn, màu sắc tươi sáng và hình nhân vật ngộ nghĩnh, đáng yêu, phù hợp với học sinh tiểu học. Giấy viết không cợn, không nhòe, không thấm mực sang trang sau, tốt cho người sử dụng đặc biệt là trẻ nhỏ.', 2, 5, 15000.00, 12000.00, 122, 88, TRUE),
    (42, 'Vở 4 ô ly 80 trang Nhân Tài Đất Việt HH','Thiết kế vui nhộn, màu sắc tươi sáng và hình nhân vật ngộ nghĩnh, đáng yêu, phù hợp với học sinh tiểu học. Giấy viết không cợn, không nhòe, không thấm mực sang trang sau, tốt cho người sử dụng đặc biệt là trẻ nhỏ.', 2, 5, 15000.00, 12000.00, 100, 25, TRUE),
    (43, 'Vở 4 ô ly 48 trang Tuổi Teen HH', 'Là sản phẩm mới đầy sáng tạo, mang đến câu chuyện cuộc sống đầy màu sắc, một hành trình với những khoảng thời gian tươi đẹp. Sản phẩm được sản xuất trên dây truyền hiện đại, chất lượng tốt. ', 2, 5, 13000.00, 11000.00, 78, 44, TRUE),
    (44, 'Vở 4 ô ly 48 trang Funny HH', 'Là sản phẩm mới đầy sáng tạo, mang đến câu chuyện cuộc sống đầy màu sắc, một hành trình với những khoảng thời gian tươi đẹp.  Sản phẩm được sản xuất trên dây truyền hiện đại, chất lượng tốt.', 2, 5, 13000.00, 11000.00, 55, 46, TRUE),
    (45, 'Vở 4 ô ly 48 trang Ếch HH', 'Là sản phẩm mới đầy sáng tạo, mang đến câu chuyện cuộc sống đầy màu sắc, một hành trình với những khoảng thời gian tươi đẹp. Sản phẩm được sản xuất trên dây truyền hiện đại, chất lượng tốt.', 2, 5, 13000.00, 11000.00, 42, 35, TRUE),
    (46, 'Vở 4 ô ly 120 trang Nuna HH', 'Được lấy cảm hứng từ hành trình khám phá vẻ đẹp của thế giới xung quanh. Thiết kế tạo cảm giác hứng thú để các bạn học sinh có những sáng tạo độc đáo.Sản phẩm được sản xuất trên dây chuyền công nghệ hiện đại, dòng kẻ in ấn sắc nét.', 2, 5, 25000.00, 23000.00, 46, 19, TRUE),
    (47, 'Vở 4 ô ly 120 trang Điểm A HH', 'Được lấy cảm hứng từ hành trình khám phá vẻ đẹp của thế giới xung quanh. Thiết kế tạo cảm giác hứng thú để các bạn học sinh có những sáng tạo độc đáo.Sản phẩm được sản xuất trên dây chuyền công nghệ hiện đại, dòng kẻ in ấn sắc nét.', 2, 5, 25000.00, 23000.00, 20, 55, TRUE),
    (48, 'Vở 4 ô ly 200 trang Gấu HH', 'Phù hợp cho học sinh tiểu học, tập viết chữ. Giấy trắng tự nhiên, bề mặt giấy láng mịn, viết êm tay, tạo nét chữ đẹp. Giấy viết không nhòe, không thấm mực sang trang sau.Tốt cho người sử dụng đặc biệt là trẻ nhỏ.', 2, 5, 28000.00, 26000.00, 23, 76, TRUE),
    (49, 'Vở 4 ô ly 200 trang Cáo HH', 'Phù hợp cho học sinh tiểu học, tập viết chữ. Giấy trắng tự nhiên, bề mặt giấy láng mịn, viết êm tay, tạo nét chữ đẹp. Giấy viết không nhòe, không thấm mực sang trang sau.Tốt cho người sử dụng đặc biệt là trẻ nhỏ.', 2, 5, 28000.00, 26000.00, 45, 56, TRUE),
    (50, 'Vở kẻ ngang 48 trang School CP', 'Với thiết kế trang bìa là Hình ảnh school – Thiết kế bìa mô phỏng khung cảnh trường học Nhật Bản quen thuộc như lớp học, sân bóng, cổng trường.. ., được thể hiện bằng nét vẽ manga đậm chất thanh xuân vườn trường – mỗi quyển vở như một khung truyện tranh, mang đến cảm giác trong trẻo, dịu dàng và đầy cảm xúc.', 2, 4, 14000.00, 12000.00, 98, 127, TRUE),
    (51, 'Vở kẻ ngang 80 trang Cake CP', 'Được làm từ chất liệu giấy ngoại nhập chất lượng cao, bề mặt giấy trơn láng, viết đẹp, mượt mà. Gáy vở được đóng theo công nghệ ép keo đa lớp của Nhật Bản, giúp vở luôn mở phẳng đẹp trên bàn học, dễ dàng lật và viết từ trang đầu tiên đến trang cuối cùng.', 2, 4, 15000.00, 12000.00, 54, 121, TRUE),
    (52, 'Vở kẻ ngang 120 trang Food CP', 'Được làm từ chất liệu giấy ngoại nhập chất lượng cao, bề mặt giấy trơn láng, viết đẹp, mượt mà.Gáy vở được đóng theo công nghệ ép keo đa lớp của Nhật Bản, giúp vở luôn mở phẳng đẹp trên bàn học, dễ dàng lật và viết từ trang đầu tiên đến trang cuối cùng. ', 2, 4, 13000.00, 11000.00, 56, 44, TRUE),
    (53, 'Vở kẻ ngang 200 trang Food CP', 'Được làm từ chất liệu giấy ngoại nhập chất lượng cao, bề mặt giấy trơn láng, viết đẹp, mượt mà.Gáy vở được đóng theo công nghệ ép keo đa lớp của Nhật Bản, giúp vở luôn mở phẳng đẹp trên bàn học, dễ dàng lật và viết từ trang đầu tiên đến trang cuối cùng. ', 2, 4, 28000.00, 26000.00, 56, 43, TRUE),
    (54, 'Nhãn vở cp1 CP', 'Được sử dụng loại giấy in chuyên dụng, có độ bán giấy tốt, bám mực tốt, có thể viết tất cả các loại mực mà không bị nhòe.', 2, 4, 5000.00, 4000.00, 78, 100, TRUE),
    (55, 'Vở ô ly 48 trang Hải Ly HT', 'ở ô ly Tiny là người bạn đồng hành lý tưởng cho các bé trong hành trình học tập mỗi ngày. Với chất lượng giấy dày mịn, dòng kẻ rõ nét và thiết kế bìa siêu dễ thương, Tiny giúp việc học của bé trở nên thật vui vẻ và đầy cảm hứng. ', 2, 6, 14000.00, 12000.00, 120, 78, TRUE),
    (56, 'Vở ô ly 80 trang Mặt Trời HT', 'Vở ô ly Mặt Trời Nhỏ mang đến cho bé cuốn vở rực rỡ sắc màu, gợi nên cảm hứng học tập tươi sáng như ánh nắng đầu ngày. Với giấy trắng tự nhiên, dòng kẻ sắc nét, bìa vở sinh động – Mặt Trời Nhỏ không chỉ là người bạn đồng hành trên bàn học mà còn là nguồn động lực để bé thêm yêu việc viết và luyện chữ mỗi ngày.', 2, 6, 15000.00, 13000.00, 78, 120, TRUE),
    (57, 'Vở ô ly 48 Ban Mai HT', 'Mùa tựu trường luôn mang đến những cảm xúc hân hoan: tiếng cười của bạn bè, những bài học mới mẻ và những người bạn đồng hành thân thiết. Trong hành trình ấy, Vở ô ly cao cấp Ban Mai của Giấy Hải Tiến chính là món quà nhỏ nhưng đầy ý nghĩa – giúp các em lưu giữ từng kiến thức và kỷ niệm học trò một cách trọn vẹn.', 2, 6, 14000.00, 12000.00, 98, 77, TRUE),
    (58, 'Vở kẻ ngang 80 trang Grow HT', 'Trong hành trình học tập và trưởng thành, một cuốn vở không chỉ là nơi ghi chép kiến thức mà còn là người bạn đồng hành, truyền cảm hứng để bạn theo đuổi mục tiêu. ', 2, 6, 15000.00, 13000.00, 98, 111, TRUE),
    (59, 'Vở kẻ ngang 200 trang Cornell HT', 'Thiết kế thanh lịch, màu sắc pastel nhẹ nhàng cùng chất giấy cao cấp của Hải Tiến tạo nên một cuốn vở vừa hiện đại vừa tinh tế — người bạn đồng hành lý tưởng trên hành trình học tập và làm việc.', 2, 6, 28000.00, 26000.00, 82, 56, TRUE),
    (60, 'Sổ lò xo kẻ ngang 7mm B5 TL', 'Bìa cứng phủ màng mờ sang trọng, cùng ruột giấy vàng kem tự nhiên giúp bạn ghi chép thoải mái, sketch note dễ dàng mà không lo lóa mắt hay lem mực. ', 2, 1, 50000.00, 25000.00, 100, 45, TRUE),
    (61, 'Sổ lò xo kẻ ngang Easy B5 HT', 'Mang tinh thần tích cực và hiện đại, dòng sổ lò xo E.A.S.Y được thiết kế dành cho học sinh, sinh viên, người đi làm và những ai yêu thích phong cách tối giản nhưng đầy tinh tế.', 2, 6, 40000.00, 27000.00, 121, 71, TRUE),
    (62, 'Sổ lò xo kẻ ngang B5 DemonSlayer TL', 'Bìa cứng phủ màng mờ sang trọng, cùng ruột giấy vàng kem tự nhiên giúp bạn ghi chép thoải mái,phù hợp cho những ai thích thể loại anime DemonSlayer. ', 2, 1, 47000.00, 38000.00, 100, 153, TRUE),
    (63, 'Sổ lò xo kẻ ngang kẻ caro B5 TL', 'Một cuốn sổ không chỉ để ghi chép – nó còn mang theo cảm xúc tích cực và nguồn năng lượng sáng tạo. Với thiết kế gọn đẹp, hình ảnh vui tươi và chất liệu giấy cao cấp, Smile giúp bạn thoải mái viết mỗi ngày mà vẫn giữ được nét cá tính riêng', 2, 1, 44000.00, 27000.00, 120, 78, TRUE),
    (64, 'Sổ bìa cứng Tree A5 TL', 'Sổ dán gáy vuông,khổ A5,in nhiều màu, in theo file thiết kế, cán màn PVC mờ chống thấm, bồi carton cứng, gấp mí góc tròn. ', 2, 1, 50000.00, 42000.00, 99, 100, TRUE),
    (65, 'Màu nước 8 màu A CLK', 'Gồm có các màu thông dụng, mang đến trải nghiệm vẽ màu nước tốt nhất và tươi sáng nhất. Bộ có 08 màu sắc tươi sáng, cường độ màu đậm, độ phủ màu tốt, dễ dàng pha trộn màu.rên khay màu có 01 muỗng để lấy màu và 01 cọ vẽ, thuận tiện khi sử dụng.', 3, 7, 45000.00, 32000.00, 120, 100, TRUE),
    (66, 'Bộ 12 màu nước Doreamon CLK', 'gồm có các màu thông dụng, mang đến trải nghiệm vẽ màu nước tốt nhất và tươi sáng nhất. Bộ có 12 màu sắc tươi sáng, cường độ màu đậm, độ phủ màu tốt, dễ dàng pha trộn màu. Nhãn khay được thiết kế với hình ảnh nhân vật Doreamon trong phim Nobita Và Câu Chuyện Thế Giới Trong Tranh', 3, 7, 48000.00, 37000.00, 35, 97, TRUE),
    (67, 'Màu nước nén 24 màu A CLK', 'Màu sắc tươi sáng,thuộc loại nén thuận tiện cho việc bảo quản. Nhãn khay được thiết kế với hình ảnh nhân vật Doreamon trong phim Nobita Và Câu Chuyện Thế Giới Trong Tranh', 3, 7, 80000.00, 67000.00, 68, 86, TRUE),
    (68, 'Màu nước 14 màu Waco CLK', 'dạng màu nước được đựng trong mỗi lọ nhựa dung tích 15ml trong suốt nhận diện được màu bên trong. Màu sắc tươi sáng, đúng chuẩn màu mỹ thuật. Màu mềm mịn và đều.  Độ hòa tan khi phối màu cao.', 3, 7, 67000.00, 59000.00, 76, 78, TRUE),
    (69, 'Sáp nhựa 12 màu Futy CLK', 'Màu tươi sáng,tô mịn êm. Dễ dàng xóa được. Thách thức trí tưởng tượng và tay nghề mỹ thuật của bé', 3, 7, 52000.00, 41000.00, 78, 89, TRUE),
    (70, 'Sáp 24 màu Doraemon CLK', 'Bộ sáp có 24 cây, 24 màu (khác nhau) thông dụng trên thị trường, được cố định trong khay định hình, tránh trường hợp bị lẫn màu khi tô.phiên bản Nobitas Little Star Wars 2022 được Thiên Long độc quyền theo hình ảnh từ bộ phim cùng tên.', 3, 7, 46000.00, 36000.00, 112, 63, TRUE),
    (71, 'Sáp 18 màu Jumbo CLK', 'Thân sáp hình trụ tròn, nhỏ vừa tay cầm của các bé. Màu sắc tươi sáng đúng chuẩn màu mỹ thuật.  Tô êm, ít bụi. Màu phủ đều và bền màu. Đặc biệt có thêm màu gold và silver hoàn toàn mới. Sáp màu Jumbo có kích thước siêu lớn, giúp dễ cầm hơn ít gãy và tiết kiệm. ', 3, 7, 68000.00, 60000.00, 123, 78, TRUE),
    (72, 'Túi cọ vẽ thẳng và tròn CLK', 'Dùng để vẽ màu nước, màu Acrylic. Cọ được phủ keo gia cố lông cọ. ', 3, 7, 15000.00, 12000.00, 200, 198, TRUE),
    (73, 'Tranh tập tô màu dạng nén Water CLK', 'Bộ gồm có: 10 tờ tranh tô màu A5, 05 tờ màu nước nén A6 (có 08 màu), 01 cây cọ. Giấy dày dặn với định lượng 300gsm có thể tô màu nước mà không lo lem sang mặt sau, không thấm màu, kèm cọ đầu nhỏ, tờ màu nước nén tách riêng với tranh tô, nên không bị lem khi tô màu.', 3, 7, 25000.00, 20000.00, 78, 23, TRUE),
    (74, 'Tập tô màu Book CLK', 'Tập tô màu 12 trang Coloring Book Thiên Long Colokit dành cho bé 3-6 tuổi, định lượng giấy 100 gsm dày dặn không lem, dùng được với nhiều loại màu khác nhau, có chủ đề đa dạng giúp bé phát triển tư duy. ', 3, 7, 19000.00, 16000.00, 57, 23, TRUE),
    (75, 'Tập tô màu Doraemon CLK', 'Tập tô màu 12 trang Doraemon Colokit dành cho bé 3-6 tuổi, định lượng giấy 100 gsm dày dặn không lem, dùng được với nhiều loại màu khác nhau, có chủ đề đa dạng giúp bé phát triển tư duy.', 3, 7, 18000.00, 14000.00, 78, 35, TRUE),
    (76, 'Bột nặn Claver CLK', 'Mềm hơn, mịn hơn, không dính tay.Dễ tạo hình với khuôn, không dính khuôn.Có thể phối trộn màu với nhau.', 3, 7, 14000.00, 12000.00, 42, 21, TRUE),
    (77, 'Bộ sáp nặn và Khuôn CLK', 'Bộ sản phẩm bao gồm 8 màu sáp nặn, dao cắt sáp, rulo cán nặn và bộ khuôn tạo hình 6 nhân vật trong truyện Doraemon giúp các bé thỏa sức sáng tạo. ', 3, 7, 55000.00, 49000.00, 123, 45, TRUE),
    (78, 'Sáp nặn MC CLK', '8 màu tươi sáng,sáp mịn, mềm, dẻo, không bở, không chai cứng, dễ dàng tạo hình, không dính tay khi nặn sáp.', 3, 7, 20000.00, 15000.00, 134, 98, TRUE),
    (79, 'Sáp nặn Sáng Tạo CLK', 'Màu sắc tươi sáng, theo đúng chuẩn màu mỹ thuật cơ bản.  Đặc biệt sáp nặn Thiên Long MC-021 có thể dễ dàng pha trộn với nhau để tạo ra các màu mới theo mong muốn của bé.', 3, 7, 30000.00, 27000.00, 100, 74, TRUE),
    (80, 'Bộ 16 khuôn nặn sáp CLK', 'Bộ sản phẩm bao gồm:  bộ khuôn tạo hình 6 nhân vật trong truyện Doraemon, 8 khuôn hình trái cây, 1 dao cắt, 1 rulo lăn sáp. ', 3, 7, 25000.00, 21000.00, 120, 69, TRUE),
--Insert thêm sản phẩm của Kha
      (81, 'Balo mẫu giáo khủng long tím Miti', 'Balo mẫu giáo khủng long tím kích thước 24 x 12 x 26 cm, 300gram, vải Polyester', 4, 9, 299000.00, 270000.00, 360, 36,TRUE),
      (82, 'Balo mẫu giáo phi hành gia Miti', 'Balo mẫu giáo phi hành gia kích thước 24 x 12 x 26 cm, 300gram, vải Polyester', 4, 9, 299000.00, 270000.00, 420, 69, TRUE),
      (83, 'Balo học sinh Miti01', 'Balo học sinh Miti cấp 1 xanh xám, kích thước 29 x 15 x 40 cm, 500gram', 4, 9, 350000.00, 300000.00, 458, 354, TRUE),
      (84, 'Balo học sinh Miti02', 'Balo học sinh Miti cấp 1 Hello Kitty đỏ, kích thước 30 x 20 x 40 cm, 650gram', 4, 9, 360000.00, 320000.00, 123, 567, TRUE),
      (85, 'Balo học sinh Miti03', 'Balo Teen, kích thước: 32 x 15 x 44 cm,1000g, vải Oxford cao cấp, chống thấm nhẹ, bền màu và dễ vệ sinh. Màu sắc:Đen phối viền trắng nổi bật, phong cách unisex phù hợp cả nam và nữ.', 4, 9, 450000.00, 400000.00, 342, 868, TRUE),
      (86, 'Balo học sinh Miti04', 'Balo Teen, kích thước: 32 x 15 x 44 cm,900g, vải polyester chống thấm, bền chắc, dễ vệ sinh.Màu sắc chủ đạo: Đen phối trắng viền trắng, họa tiết mặt trước hiện đại.', 4, 9, 425000.00, 375000.00, 134, 689, TRUE),
      (87, 'Balo mẫu giáo Capybara MV', 'Balo mẫu giáo capybara kích thước 27 x 12 x 21 cm,290gram, vải Polyester', 4, 10, 300000.00, 250000.00, 64, 364, TRUE),
      (88, 'Balo mẫu giáo thỏ hồng MV', 'Balo mẫu giáo thỏ hồng kích thước 24 x 13 x 30 cm,450gram, vải Oxford', 4, 10, 250000.00, 225000.00, 544, 234, TRUE),
      (89, 'Balo học sinh MV01', 'Balo Teen, kích thước: 39 x 14 x 29 cm,460g, vải Oxford cao cấp, chống thấm nhẹ, bền màu và dễ vệ sinh. Màu sắc:Xanh đen', 4, 10, 300000.00, 275.000, 23, 544, TRUE),
      (90, 'Balo học sinh MV02', 'Balo Teen, kích thước: 43 x 18 x 30 cm,770g, vải dù lạnh, Oxford lót trong. Màu sắc:Đỏ', 4, 10, 400000.00, 370000.00, 567, 975, TRUE),
      (91, 'Balo Laptop 15.6 inch MV01', 'Balo đựng laptop Mr.Vui, kích thước: 44 x 30 x 15 cm, 1050gram, ngăn laptop: 15.6 inch. Màu sắc: xanh đen', 4, 10, 350000.00, 300000.00, 32, 575, TRUE),
      (92, 'Balo Laptop 15.6 inch MV02', 'Balo đựng laptop Mr.Vui, kích thước: 43 x 29 x 14 cm, 940gram, ngăn laptop: 15.6 inch. Màu sắc: Xám chì', 4, 10, 450000.00, 415000.00, 23, 674, TRUE),
      (93, 'Balo Laptop 13 inch MV01', 'Balo đựng laptop Mr.Vui, kích thước: 31 x 42 x 16 cm, 700gram, ngăn laptop: 13 inch. Màu sắc: Đỏ', 4, 10, 400000.00, 375000.00, 344, 425, TRUE),
      (94, 'Balo Laptop 13 inch MV02', 'Balo đựng laptop Mr.Vui, kích thước: 30 x 44 x 13 cm, 830gram, ngăn laptop: 13 inch. Màu sắc: Xám', 4, 10, 560000.00, 500000.00, 23, 423, TRUE),
      (95, 'Balo Laptop 17 inch MV01', 'Balo đựng laptop Mr.Vui, kích thước: 32 x 46 x 16 cm, 1190gram, ngăn laptop: 17 inch. Màu sắc: Xám chì', 4, 10, 600000.00, 550000.00, 234, 86, TRUE),
      (96, 'Máy tính Casio fx-580VN X', 'Máy tính Casio fx-580 VN X là một sản phẩm nổi bật với 521 chức năng đa dạng, bao gồm tính toán cơ bản, phương trình, ma trận, đạo hàm, tích phân,…', 5, 13, 600000.00, 550000.00, 234, 753, TRUE),
      (97, 'Máy tính Casio fx-570VN PLUS-2', 'Máy tính Casio fx-570VN PLUS-2 là một sản phẩm nổi bật với 453 chức năng đa dạng, bao gồm tính toán cơ bản, phương trình, ma trận, đạo hàm, tích phân,…', 5, 13, 500000.00, 450000.00, 123, 553, TRUE),
      (98, 'Máy tính khoa học Casio fx-500MS-2', 'Máy tính Casio fx-500MS-2 là một thiết bị tính toán đa chức năng, có 244 chức năng tính toán, bao gồm giải phương trình, tính toán lượng giác, thống kê và logarit.', 5, 13, 350000.00, 300000.00, 257, 199, TRUE),
      (99, 'Máy tính khoa học Casio fx-880BTG', 'Máy tính Casio fx-880BTG là một sản phẩm máy tính khoa học hiện đại, thuộc dòng ClassWiz của Casio. Sản phẩm này được thiết kế với nhiều cải tiến về thiết kế, giao diện và tính năng, giúp đáp ứng nhu cầu học tập và thi cử của học sinh, sinh viên.', 5, 13, 700000.00, 670000.00, 23, 336, TRUE),
      (100, 'Máy tính văn phòng Flexio CAL-011', 'Máy tính văn phòng CAL-011 đa năng này phù hợp sử dụng tại nhà, trường học, văn phòng hoặc cửa hàng. Sự kết hợp chip xử lý và mạch điều khiển công nghệ hiện đại đưa ra những kết quả phép tính đáng tin cậy, nhanh chóng đáp ứng tốt cho mục đích cá nhân hoặc chuyên nghiệp.', 5, 12, 200000.00, 150000.00, 545, 342, TRUE),
      (101, 'Máy tính văn phòng Flexio CAL-010', 'Máy tính văn phòng CAL-010 đa năng này phù hợp sử dụng tại nhà, trường học, văn phòng hoặc cửa hàng. Sự kết hợp chip xử lý và mạch điều khiển công nghệ hiện đại đưa ra những kết quả phép tính đáng tin cậy, nhanh chóng đáp ứng tốt cho mục đích cá nhân hoặc chuyên nghiệp.', 5, 12, 225000.00, 175000.00, 54, 667, TRUE),
      (102, 'Máy tính văn phòng Flexio CAL-009', 'Máy tính văn phòng CAL-009 đa năng này phù hợp sử dụng tại nhà, trường học, văn phòng hoặc cửa hàng. Sự kết hợp chip xử lý và mạch điều khiển công nghệ hiện đại đưa ra những kết quả phép tính đáng tin cậy, nhanh chóng đáp ứng tốt cho mục đích cá nhân hoặc chuyên nghiệp.', 5, 12, 100000.00, 75000.00, 53, 360, TRUE),
      (103, 'Máy tính văn phòng Flexio CAL-008', 'Máy tính văn phòng CAL-008 đa năng này phù hợp sử dụng tại nhà, trường học, văn phòng hoặc cửa hàng. Sự kết hợp chip xử lý và mạch điều khiển công nghệ hiện đại đưa ra những kết quả phép tính đáng tin cậy, nhanh chóng đáp ứng tốt cho mục đích cá nhân hoặc chuyên nghiệp.', 5, 12, 150000.00, 100000.00, 66, 454, TRUE),
      (104, 'Máy tính văn phòng Flexio CAL-007', 'Máy tính văn phòng CAL-007 đa năng này phù hợp sử dụng tại nhà, trường học, văn phòng hoặc cửa hàng. Sự kết hợp chip xử lý và mạch điều khiển công nghệ hiện đại đưa ra những kết quả phép tính đáng tin cậy, nhanh chóng đáp ứng tốt cho mục đích cá nhân hoặc chuyên nghiệp.', 5, 12, 125000.00, 100000.00, 89, 123, TRUE),
      (105, 'Máy tính khoa học Flexio Fx509VN', 'Máy tính khoa học Flexio Fx509VN là một sản phẩm nổi bật với 244 chức năng đa dạng, bao gồm tính toán cơ bản, phương trình, ma trận, đạo hàm, tích phân,…', 5, 12, 150000.00, 125000.00, 353, 756, TRUE),
      (106, 'Máy tính khoa học Flexio Fx799VN', 'Máy tính khoa học Flexio Fx509VN là một sản phẩm nổi bật với 546 chức năng đa dạng, bao gồm tính toán cơ bản, phương trình, ma trận, đạo hàm, tích phân,…', 5, 12, 650000.00, 550000.00, 243, 675, TRUE),
      (107, 'Máy tính khoa học Flexio Fx680VN Plus', 'Máy tính khoa học Flexio Fx509VN là một sản phẩm nổi bật với 529 chức năng đa dạng, bao gồm tính toán cơ bản, phương trình, ma trận, đạo hàm, tích phân,…', 5, 12, 700000.00, 450000.00, 234, 756, TRUE),
      (108, 'Balo Jamlos Goodie', 'Balo canvas 16inch nhiều ngăn đi học đi làm êm vai và siêu nhẹ', 4, 11, 800000.00, 715000.00, 3452, 233, TRUE),
      (109, 'Balo Jamlos City', 'Balo canvas quai xách thanh lịch hiện đại vừa laptop 13inch, A4 đi học đi làm', 4, 11, 700000.00, 605000.00, 344, 321, TRUE),
      (110, 'Balo Jamlos Triple', 'Balo thời trang vải canvas nhiều ngăn đi học và làm vừa laptop 15inch', 4, 11, 800000.00, 735000.00, 2131, 798, TRUE),
      (111, 'Balo Jamlos Mini Pocket', 'Balo thời trang vải canvas nhiều ngăn nhỏ gọn đi học đi làm vừa laptop 13inch A4', 4, 11, 700000.00, 660000.00, 4652, 134, TRUE),
      (112, 'Balo trẻ em Jamlos Gelato', 'Balo vải canvas nắp hít có hình kem ốc quế vừa A4 đi học, đi chơi', 4, 11, 500000.00, 440000.00, 1234, 557, TRUE),
      (113, 'Thước thẳng 15cm', 'Thước thẳng Thiên Long 15cm phiên bản Demon Slayer', 8, 1, 9000.00, 8100.00, 34, 127, TRUE),
      (114, 'Thước đo độ 180º', 'Thước đo độ SR-035 được sản xuất tại Thiên Long, là loại thước đo độ 180º, thước làm bằng nhựa PS trong. Thích hợp cho học sinh, sinh viên,...', 8, 1, 5000.00, 4800.00, 425, 948, TRUE),
      (115, 'Compa Thiên Long C-021', 'Compa Thiên Long Ngon, Bổ, Rẻ', 8, 1, 17300.00, 15570.00, 474, 138, TRUE),
      (116, 'Thước thẳng 15cm Ezpik PeTiTe', 'Thước SR-037 được sản xuất tại Thiên Long, là loại thước thẳng, màu trong, có chiều dài vạch chia 15 cm. Thích hợp cho học sinh, sinh viên,...', 8, 1, 6400.00, 5760.00, 41, 949, TRUE),
      (117, 'Thước thẳng 20cm Thiên Long', 'Thước thẳng được chế tạo từ nhựa PS có độ bền cao. Được sử dụng nhiều trong học tập, cơ khí, vẽ các bảng kĩ thuật, xây dựng, vẽ tranh...', 8, 1, 5800.00, 4640.00, 58, 234, TRUE),
      (118, 'Thước nhựa thẳng màu Pastel 20 cm Thiên Long Pazto', 'Thước thẳng Thiên Long 20cm', 8, 1, 6300.00, 5670.00, 20, 65, TRUE),
      (119, 'Thước thẳng Thiên Long Điểm 10 15cm', 'Thước Thiên Long 15cm ngon, bổ, rẻ', 8, 1, 6000.00, 5400.00, 58, 986, TRUE),
      (120, 'Compa Thiên Long C-015', 'Compa được làm bằng nhựa ABS, vít kim loại và mũi nhọn làm bằng thép.', 8, 1, 17300.00, 15570.00, 95, 234, TRUE),
      (121, 'Compa Thiên Long C-017', 'Compa được làm bằng nhựa ABS, vít kim loại và mũi nhọn làm bằng thép.', 8, 1, 31400.00, 28260.00, 47, 798, TRUE),
      (122, 'Compa Thiên Long C-016', 'Compa được làm bằng nhựa ABS, vít kim loại và mũi nhọn làm bằng thép.', 8, 1, 26400.00, 13200.00, 821, 97, TRUE),
      (123, 'Compa Thiên Long C-018', 'Compa được làm bằng nhựa ABS, vít kim loại và mũi nhọn làm bằng thép.', 8, 1, 26400.00, 23760.00, 611, 46, TRUE),
      (124, 'Compa Điểm 10 MTEN TP-C019', 'Compa Điểm 10 MTEN TP-C019 là dụng cụ học tập quan trọng, không thể thiếu của học sinh, sinh viên, là trợ thủ đắc lực của dân văn phòng, kiến trúc sư,... Giúp người dùng dễ dàng vẽ các đường cong hình học, hình tròn, hình oval, xoắn ốc, hình bán nguyệt,...', 8, 3, 31400.00, 28260.00, 312, 42, TRUE),
      (125, 'Compa Thiên Long Y C-020', 'Compa Thiên Long Y C-020 là dụng cụ học tập quan trọng, không thể thiếu của học sinh, sinh viên, là trợ thủ đắc lực của dân văn phòng, kiến trúc sư,... Giúp người dùng dễ dàng vẽ các đường cong hình học, hình tròn, hình oval, xoắn ốc, hình bán nguyệt,...', 8, 1, 31400.00, 28260.00, 634, 87, TRUE),
      (126, 'Thước thẳng Thiên Long 30cm SR-031', 'Là loại thước thẳng phù hợp cho mọi đối tượng, dài 30cm.', 8, 1, 11200.00, 10080.00, 68, 98, TRUE),
      (127, 'Compa MTEN Điểm 10 TP-C012 Elsa', 'Compa MTEN Điểm 10 TP-C012 là dụng cụ học tập quan trọng, không thể thiếu của học sinh, sinh viên. và là trợ thủ đắc lực của dân văn phòng, kiến trúc sư,... Giúp người dùng dễ dàng vẽ các đường cong hình học, hình tròn, hình oval, xoắn ốc, hình bán nguyệt,...', 8, 3, 26400.00, 23760.00, 697, 4, TRUE),
      (128, 'Compa Y Thiên Long TL-C01', 'Compa Y Thiên Long TL-C01 (Compass Y C-014) là dụng cụ học tập quan trọng, không thể thiếu của học sinh, sinh viên. và là trợ thủ đắc lực của dân văn phòng, kiến trúc sư,... Giúp người dùng dễ dàng vẽ các đường cong hình học, hình tròn, hình oval, xoắn ốc, hình bán nguyệt,...', 8, 1, 26400.00, 23760.00, 56, 6, TRUE),
      (129, 'Thước thẳng Flexoffice FO-SR02', 'Là loại thước thẳng chuyên dùng trong văn phòng, dài 20cm.', 8, 2, 5800.00, 5220.00, 32, 31, TRUE),
      (130, 'Thước thẳng Flexoffice FO-SR01', 'Là loại thước thẳng chuyên dùng trong văn phòng, dài 30cm.', 8, 2, 6900.00, 6210.00, 978, 34, TRUE),
      (131, 'Thước thẳng Điểm 10 Doraemon', 'Thước thẳng Doraemon ngon,bổ,rẻ', 8, 3, 5800.00, 5220.00, 623, 56, TRUE),
      (132, 'Gôm tẩy nhân vật Akooland Thiên Long TP-E017/AK', 'Gôm tẩy sạch vết chì trên giấy, không bụi, tẩy êm, nhẹ tay, không bị rách giấy và không bị gãy gôm khi tẩy.', 8, 1, 4800.00, 5000.00, 34, 64, TRUE),
      (133, 'Gôm tẩy nhân vật Demon Slayer Thiên Long E-036/DS', 'Gôm Thiên Long tạo hình nhân vật trong phim Demon Slayer.Hàng chất lượng cao, an toàn.', 8, 1, 5000.00, 6000.00, 435, 32, TRUE),
      (134, 'Gôm tẩy không bụi mịn - Strive Dust Free Thiên Long E-035', 'Gôm tẩy sạch vết chì trên giấy, gôm sẽ tạo thành sợi - không bụi mịn, tẩy êm - nhẹ tay, không bị rách giấy và không bị gãy gôm khi tẩy.', 8, 1, 6000.00, 8000.00, 456, 323, TRUE),
      (135, 'Gôm điện tự động Thiên Long Flexio EE-001', 'Gôm điện tự động Thiên Long siêu hiện đại, không hại điện.', 8, 2, 51000.00, 75000.00, 3245, 32, TRUE),
      (136, 'Gôm tẩy Creativ Thiên Long E-031', 'Gôm Thiên Long chất lượng, an toàn cho người sử dụng', 8, 1, 6000.00, 8000.00, 355, 32, TRUE),
      (137, 'Gôm tẩy không mùi Pastel Pazto Thiên Long E-010', 'Chất liệu nhựa PVC không mùi an toàn với người sử dụng', 8, 1, 5000.00, 7500.00, 324, 65, TRUE),
      (138, 'Gôm tẩy chì Black & Pink Trendy Thiên Long E-011', 'Gôm Thiên Long cho fan BlackPink', 8, 1, 6000.00, 8000.00, 43, 345, TRUE),
      (139, 'Gôm tẩy xóa chì Thiên Long Hi Polymer E-030', 'Gôm Thiên Long Hi Polymer E-030 được làm từ chất liệu cao cấp, đáp ứng các chỉ tiêu an toàn cho phép, không có mùi khó chịu mang đến sự an tâm cho người sử dụng. Gôm siêu mềm và siêu dẻo, không bị cứng khi sử dụng trong thời gian dài.', 8, 1, 6000.00, 10000.00, 378, 366, TRUE),
      (140, 'Gôm tẩy xóa chì kháng khuẩn Điểm 10 TP-E029', 'Gôm Điểm 10 kháng khuẩn, siêu an toàn.', 8, 3, 7500.00, 10000.00, 35, 323, TRUE),
      (141, 'Gôm tẩy xóa chì Flexoffice FO-E04', 'Gôm Flexoffice chất lượng cao, an toàn.', 8, 2, 3600.00, 5000.00, 536, 23, TRUE),
      (142, 'Gôm tẩy xóa chì Flexoffice FO-E02', 'Gôm Thiên Long - Flexoffice FO-E02 được làm từ chất liệu cao cấp, đáp ứng các chỉ tiêu an toàn cho phép, không có mùi khó chịu mang đến sự an tâm cho người sử dụng. Gôm siêu mềm và siêu dẻo, không bị cứng khi sử dụng trong thời gian dài.', 8, 2, 2500.00, 5000.00, 354, 32, TRUE),
      (143, 'Gôm tẩy xóa chì Điểm 10 E-015', 'Gôm Thiên Long - Điểm 10 E-15 được làm từ chất liệu cao cấp, đáp ứng các chỉ tiêu an toàn cho phép, không có mùi khó chịu mang đến sự an tâm cho người sử dụng. Gôm siêu mềm và siêu dẻo, không bị cứng khi sử dụng trong thời gian dài.', 8, 3, 6000.00, 8000.00, 36, 63, TRUE),
      (144, 'Gôm tẩy xóa chì Thiên Long E-06', 'Gôm Thiên Long E-06 được làm từ chất liệu cao cấp, đáp ứng các chỉ tiêu an toàn cho phép, không có mùi khó chịu mang đến sự an tâm cho người sử dụng. Gôm siêu mềm và siêu dẻo, không bị cứng khi sử dụng trong thời gian dài.', 8, 1, 5000.00, 6000.00, 65, 98, TRUE),
--Inser sản phẩm của Phước
      (145, 'Đèn học Rạng Đông chống cận 6w 8w, cảm ứng 4 màu ánh sáng bảo vệ thị lực cho bé MODEL RD-RL-45', '-Sản phẩm: ĐÈN BÀN\n-Chất liệu: Hợp kim nhôm\n-Màu sắc:Màu đen\n-Công suất :12W\n-Tuổi thọ đèn:38000\n-Ánh sáng: Có thể điều chỉnh với 3 chế độ sáng và 10 mức độ khác nhau', 6, 13, 255999.00, 145919.43, 494, 194, TRUE),
      (146, 'Đèn học Rạng Đông LED chống cận, bảo vệ mắt, thị lực,3 mức sáng, MODEL RD-RL-36', 'THÔNG TIN SẢN PHẨM ĐÈN HỌC ĐỂ BÀN, ĐÈN BÀN , LÀM VIỆC\n+ Điện áp đầu vào : 5V An toàn tuyệt đối không giật như điện 220V.\n+ Công suất: 12w\n+ Led: 3 màu ánh sáng/ Có thể tăng giảm độ sáng', 6, 13, 254790.00, 216571.50, 430, 279, TRUE),
      (147, 'Đèn Học Chống Cận Cảm Ứng Rạng Đông RD-RL-21 8w– Bảo Vệ Đôi Mắt Bé Yêu', 'Công suất\t8W\nĐiện áp danh định\t220V / 50Hz\nNhiệt độ màu\t3000K – 6500K\nĐộ rọi trung bình\t1200 lux\nHệ số hoàn màu (CRI)\t95\nTuổi thọ\t25.000 giờ\nKích thước (RxC)\t162 x 386 mm', 6, 13, 399999.00, 319999.20, 330, 93, TRUE),
      (148, 'Đèn học Rạng Đông chống cận để bàn bảo vệ thị lực cho bé RD-RL-24 5W BH 2 Năm-Himect', 'Tiết kiệm điện năng và thân thiện với môi trường\n- Công suất chỉ 5W nhưng vẫn mang lại ánh sáng trung thực, tự nhiên\n- Tiết kiệm điện năng, giảm hóa đơn tiền điện\n- Không chứa thủy ngân và hóa chất độc hại, an toàn cho sức khỏe', 6, 13, 149000.00, 101320.00, 318, 192, TRUE),
      (149, 'Đèn học Rạng Đông chống cận 5w, đèn bàn học cho bé, học sinh, đọc sách, làm việc MODEL RD-RL-27.V2', 'Đèn học Rạng Đông chống cận 5w, đèn học để bàn cho bé, học sinh, đọc sách, làm việc RD-RL-27.V2 5W\n👉Thông số kỹ thuật:\nCông suất: 5W\nĐiện áp:\t220V/50Hz\nMàu ánh sáng: Trắng/Vàng\nĐộ rọi:700 lux', 6, 13, 175000.00, 101500.00, 312, 208, TRUE),
      (150, 'Đèn bàn, đèn học LED, đèn chống cận Rạng Đông RD-RL24.V2 - 5W, Ánh sáng VÀNG Chống cận thị cho bé', 'THÔNG TIN SẢN PHẨM ĐÈN HỌC ĐỂ BÀN, ĐÈN BÀN , LÀM VIỆC\n+ Điện áp đầu vào : 5V An toàn tuyệt đối không giật như điện 220V.\n+ Công suất: 12w\n+ Led: 3 màu ánh sáng/ Có thể tăng giảm độ sáng', 6, 13, 152900.00, 85624.00, 332, 210, TRUE),
      (151, 'Đèn học Rạng Đông LED chống cận để bàn bảo vệ mắt cảm ứng 3 mức ánh Sáng, 4 màu ánh sáng RD-RL-36', 'Công suất:8W\nĐiện áp danh định:220V/50Hz\nDòng điện đầu vào (Max):0.11A\nĐộ rọi trung bình:1200 lux\nNhiệt độ màu:6500/3000K\nChỉ số thể hiện màu CRI:95\nTuổi thọ:25.000 giờ\nBảo hành:2 năm', 6, 13, 249000.00, 176790.00, 312, 49, TRUE),
      (153, 'Đèn học chống cận LED Rạng Đông, chip LED SAMSUNG (RL-19)', 'Điện áp: 170V- 250V/50\n_ Công suất: 5W\n_ Độ rọi: 600 lux\n_ Tuổi thọ của đèn có thể lên đến 15000h\n_ Phần trên thân đèn được thiết kế kiểu lò xo nên xoay được đa chiều theo nhu cầu sử dụng .\n_ Lưu ý: không tự ý tháo rời các bộ phận của đèn, không để nước rơi vào đèn hoặc để nơi có độ ẩm cao.\n_ GIAO MÀU NGẪU NHIÊN THEO ĐỢT HÀNG.', 6, 13, 145000.00, 145000.00, 461, 71, TRUE),
      (154, 'Đèn học rạng đông 5w đèn bàn chống cận cho bé, học sinh MODEL RD-RL-24.V2', 'Thông tin chi tiết\n- Đui đèn E27 dễ dàng thay thế bóng đèn\n- Tuổi thọ lên đến 15,000 giờ\n- Bảo hành 24 tháng, an tâm sử dụng', 6, 13, 176000.00, 123200.00, 406, 138, TRUE),
      (155, 'Đèn học Rạng Đông chống cận 5W RD-RL-01.V2, Đèn bàn học sinh Rạng Đông cho bé trai, bé gái', 'Model: RD-RL-60 8W\nCông suất:8W\nĐiện áp danh định:220V/50Hz\nNhiệt độ màu:3000K - 6500K\nĐộ rọi trung bình:700 lux\nTuổi thọ:25.000 giờ\nKích thước(RxC):(240x566)mm', 6, 13, 159345.00, 111541.50, 388, 49, TRUE),
      (156, 'Đèn học Rạng Đông chống cận 6W, để bàn cho bé học sinh, cute, làm việc MODEL RD-RL-38.LED', 'Công suất: 6W\nĐiện áp danh định:\t(150-250)V/50Hz\nChỉ số hoàn màu:\t97\nĐộ rọi trung bình:\t700 lux\nTuổi thọ:\t25.000 giờ', 6, 13, 212000.00, 173840.00, 448, 120, TRUE),
      (157, 'Đèn học để bàn chống cận 5W LED Rạng Đông, bảo vệ mắt, thị lực MODEL RD-RL-26.LED', 'Công suất:5W\nĐiện áp: 220V/50Hz\nMàu ánh sáng:Trắng/ Vàng\nĐộ rọi trên mặt bàn:> 700 lux:\nĐèn bàn LED Rạng Đông bảo vệ thị lực\nCần đèn linh hoạt, dễ dàng điều khiển chiều cao, góc chiếu sáng\nTuổi thọ cao 15 000 giờ', 6, 13, 183999.00, 136159.26, 305, 67, TRUE),
      (158, 'Đèn học Rạng Đông chống cận 6W, để bàn cảm ứng 3 chế độ cho bé học sinh đọc sách,MODEL RD-RL-38.PLUS', 'Thông số kỹ thuật:\nCông suất:6W\nĐiện áp danh định:220V/50Hz\nNhiệt độ màu:3000K - 6500K\nĐộ rọi trung bình:700 lux\nHệ số trả màu:80\nTuổi thọ:25.000 giờ\nKích thước (RxC):(162x386)mm', 6, 13, 300000.00, 225000.00, 342, 118, TRUE),
      (159, 'Đèn Học Đế Gỗ Để Bàn Dùng Bóng Đèn LED Bulb Của Rạng Đông Chống Cận Thị', '- Model: db-1006\n- Màu sắc: đen, trắng\n- Ánh sáng: tùy vào bóng đèn sử dụng\n- Nút bấm: nút công tắt on/off\n- Chuôi đèn: E27\n- Chiều dài dây cắm: 1M\n- Công suất: tùy vào bóng đèn sử dụng\n- Kích thước: chiều cao 43cm, chụp đèn đường kính 12cm, chui đèn dài 17cm, đế đèn đường kính 15cm (xem chi tiết trong hình sản phẩm)\n- Chất liệu: gỗ, kim loại, ABS\n- Điện áp đầu vào: 110-240V', 6, 13, 179000.00, 134250.00, 471, 230, TRUE),
      (160, 'Đèn học chống cận Rạng Đông (ánh sáng vàng)', 'Đèn Bàn Bảo Vệ Thị Lực Chống Cận Cho Bé RẠNG ĐÔNG - Có Hộc Đựng Bút, Để Học và Làm Việc', 6, 13, 149000.00, 149000.00, 445, 291, TRUE),
      (161, 'Đèn Học Để Bàn Chống Cận Rạng Đông RL45 Nút Bấm Cảm Ứng Có Ống Đựng Bút Thông Minh BH 2 năm- HIMECT', 'ĐIỂM NỔI BẬT ĐÈN HỌC ĐỂ BÀN\nĐèn sử dụng đồng thời ""4 PHƯƠNG THỨC BẢO VỆ MẮT""\n+ Sử dụng công nghệ 3 Lớp Lọc ánh sáng : lọc ánh sáng xanh + lớp lọc ánh sáng tạp + lớp phân tán đều ánh sáng.\n+ 3 chế độ ánh sáng (Trắng / Vàng / Trung tính) với ánh sáng Trung tính dễ chịu với mắt.\n+ Công nghệ đèn LED cho ánh sáng liên tục, không nhấp nháy như đèn neon.\n+ Lớp lọc tản sáng hạn chế hiện tượng nhiều bóng mờ, giúp mắt người ít phải điều tiết khi dùng.', 6, 13, 249000.00, 159360.00, 310, 138, TRUE),
      (162, 'Bóng đèn học Rạng Đông 11w', 'Bóng đèn ánh sáng trắng và ánh sáng vàng (Bóng đèn Trung Quốc) - Sản phẩm dùng để thay thế cho bóng đèn học Rạng Đông.\nNguồn điện: 220v.\nCông suất: 11 w.\nChiều dài bóng vàng: 22,5cm.\nChiều dài bóng trắng: 23.2cm.\nXuất xứ: Trung Quốc.', 6, 13, 45999.00, 45999.00, 442, 273, TRUE),
      (163, 'Đèn học sinh bảo vệ thị lực Rạng Đông 5w, thiết kế hình con vật nhiều màu sắc, bảo hành 2 năm', 'Bảo vệ thị lực bé yêu mỗi ngày! Đèn học LED Rạng Đông chống cận, thiết kế dành riêng cho trẻ em, giúp bảo vệ mắt hiệu quả trong quá trình học tập và làm việc. Ánh sáng LED không nhấp nháy, dịu nhẹ, giúp mắt bé không bị mỏi và tăng khả năng tập trung.', 6, 13, 156600.00, 140940.00, 446, 178, TRUE),
      (164, 'Bóng đèn học RẠNG ĐÔNG đui xoáy E27 bóng LED chip SAMSUNG chống cận bảo vệ thị lực bảo hành 12 tháng', 'Bóng đèn led lắp đui đèn trang trí ngoài trời, đèn thả trang trí, đèn ngủ, đèn bàn trang điểm...\n+Bóng đèn Led tiết kiệm điện dùng để trang trí và chiếu sáng, được sử dụng phổ biến khắp mọi nơi từ trong nhà đến ngoài trời, sân vườn, hàng quán, đường phố\n+Tiết kiệm điện đến 80% so với bóng đèn thông thường\n+Ánh sáng giống như ánh sáng tự nhiên, nên không hại mắt, có thể dùng để chiếu sáng cho cây xanh tốt', 6, 13, 39499.00, 31204.21, 313, 184, TRUE),
      (165, 'Bóng đèn học 11w ánh sáng trắng, vàng (bóng đèn Trung Quốc) thay thế cho đèn RẠNG ĐÔNG (loại tốt)', '#Tuổi thọ, độ bền:\n+Led có tuổi thọ cao từ 15000-20000 giờ, với điều kiện sử dụng đêm bật ngày tắt thì đến vài năm mới có thể hỏng\n+Chất liệu bóng đèn bằng nhựa kỹ thuật nên không sợ va chạm hay rơi vỡ\n#Hướng dẫn chọn công suất phù hợp:\n+3-5w: Làm đèn trang trí, đèn tường, đèn góc, đèn gương, đèn ở khu vực nhỏ cần sáng ít\n+7-12w: Chiếu sáng và trang trí, đèn cầu thang, đèn học, đèn nhà tắm, vệ sinh, khu vực nhỏ cần sáng vừa\n+15-28w: Chiếu sáng phòng nhỏ, nhà bếp, hành lang, ban công, khu vực nhỏ và vừa cần sáng rõ ràng\n+30-50w: Chiếu sáng phòng lớn, sân vườn, khu vực rộng cần ánh sáng mạnh\n+Ước tính khoảng 20-30w chiếu sáng đủ cho 10m2', 6, 13, 42000.00, 42000.00, 397, 182, TRUE),
      (166, 'Bóng đèn huỳnh quang thay thế bóng đèn học Rạng Đông', 'Bóng đèn ánh sáng trắng và ánh sáng vàng (Bóng đèn Trung Quốc) - Sản phẩm dùng để thay thế cho bóng đèn học Rạng Đông.\nNguồn điện: 220v.\nCông suất: 11 w.\nChiều dài bóng vàng: 22,5cm.\nChiều dài bóng trắng: 23.2cm.\nXuất xứ: Trung Quốc.', 6, 13, 40000.00, 40000.00, 349, 1, TRUE),
      (167, 'Giấy kiểm tra Điểm 10', 'Thiên Long - Miền Trung | Xuất xứ : Việt Nam | Thương hiệu Điểm 10 | Model: TP-GKT02 | Kích thước 170 x 240 mm | Trọng lượng 30 gram | Quy cách 20 tờ đôi và 6 tờ đơn / xấp | Kích thước ô ly 4 ô ly vuông (2,5 x 2,5) mm | Chất liệu Bột giấy | Định lượng 100 gsm', 7, 3, 18128.00, 22500.00, 482, 188, TRUE),
    (168, 'Giấy kiểm tra Điểm 10 TP-GKT02 (4 ô ly ) -20 tờ đôi và 6 tờ đơn', 'Xuất xứ : Việt Nam | Thương hiệu Điểm 10 | Model: TP-GKT02 | Kích thước 170 x 240 mm | Trọng lượng 30 gram | Quy cách 20 tờ đôi và 6 tờ đơn / xấp | Kích thước ô ly 4 ô ly vuông (2,5 x 2,5) mm', 7, 3, 14850.00, 22500.00, 456, 26, TRUE),
    (169, 'Giấy ghi chú Thiên Long', 'Thông số kĩ thuật | Thương hiệu Thiên Long | Định lượng giấy 100 gsm | Tiêu chuẩn TCCS 095:2016/TL-GN | Xuất xứ Việt Nam | Sản xuất Trung Quốc | Khuyến cáo Nhiệt độ 10~55ºC, Độ ẩm 55~95% RH | Tránh xa nguồn nhiệt, dầu mỡ', 7, 1, 9240.00, 14000.00, 456, 13, TRUE),
    (170, 'Set 100 tờ giấy ghi chú Pastel Thiên Long', 'Thông số kĩ thuật | Thương hiệu Thiên Long | Định lượng giấy 100 gsm | Tiêu chuẩn TCCS 095:2016/TL-GN | Xuất xứ Việt Nam | Sản xuất Trung Quốc', 7, 1, 16456.00, 22000.00, 491, 277, TRUE),
    (171, 'Thẻ flashcard trắng CAO CẤP', 'Sản phẩm giấy ghi chú kích thước mini 4x7 cm | Trọng lượng 30 gram | Tiện lợi mang theo | Phù hợp ghi chép nhanh | Để bàn làm việc, túi sách hoặc ngăn kéo xe hơi', 7, 3, 8000.00, 25000.00, 403, 202, TRUE),
    (172, 'Set 100 Giấy Note Ghi Chú, Giấy Nhớ Hình Vuông Dán Vàng', 'Tên sản phẩm: Giấy nhắn Deli | Mã sản phẩm: EA00353/EA00253 | Quy cách: 1 tệp', 7, 3, 7000.00, 15000.00, 424, 47, TRUE),
    (173, 'Giấy Note Elephant', 'Giấy Note Elephant - Giấy ghi chú - Giấy nhớ 2x3 inch | Màu pastel 100 tờ/xấp | Màu neon 80 tờ/xấp | Hỗ trợ quản lý và tổ chức kế hoạch dài hạn', 7, 3, 15000.00, 18000.00, 409, 116, TRUE),
    (174, 'Giấy note set ghi chú 400 tờ', 'Bộ sản phẩm 400 tờ | Phù hợp ghi chép hàng ngày | Đánh dấu nội dung quan trọng | Dùng trong học tập, họp và công việc', 7, 3, 29000.00, 39000.00, 300, 31, TRUE),
    (175, 'Giấy ghi chú trong suốt', 'Chất liệu PET trong suốt | Bề mặt mịn dễ viết | Keo dán chắc dễ bóc không để keo | Không rách giấy | Màu pastel dịu mắt | Ghi chú rõ ràng khi dùng lâu', 7, 3, 27750.00, 34500.00, 458, 94, TRUE),
    (176, 'Giấy nhớ note 4 màu', 'Giấy note 4 màu có dòng kẻ 100 tờ/tập | Model Baoke TZ6001 (76x102 mm) | Thương hiệu Baoke | Xuất xứ Trung Quốc | Nhà cung cấp GuangDong Baoke Stationery Co.,LTD', 7, 3, 27000.00, 26000.00, 325, 269, TRUE),
    (177, 'Giấy A4 Double A 70 gsm', '🌟 Giấy In A4 Double A - Chất Lượng Cao Cấp 🌟\n\n📌 Chất Lượng Vượt Trội\n\n- Giấy A4 Double A là lựa chọn hàng đầu cho in ấn văn phòng với chất lượng cao cấp.\n\n- Định lượng giấy có sẵn: 70gsm và 80gsm, phù hợp cho nhiều mục đích sử dụng từ in hợp đồng, bản ký kết đến phác thảo thiết kế.\n\n- Độ trắng sáng và sắc nét, không gây kẹt giấy nhờ công nghệ cắt hiện đại. ', 7, 15, 106000.00, 72080.00, 167, 13, TRUE),
    (178, 'Giấy in A4 IK Plus 70gsm 500 tờ/ream', '🌟 Đặc điểm nổi bật 🌟\n\n- Độ trắng cao 98%:  Giúp bản in sắc nét và rõ ràng, phù hợp cho mọi thiết bị văn phòng.\n\n- Bề mặt nhẵn mịn:  Tối ưu lượng mực sử dụng, in ấn mượt mà.\n\n- Công nghệ Trutone: Tạo ra hình ảnh in chân thực, sống động cả khi in trắng đen lẫn in màu.', 7, 16, 70900.00, 42540.00, 123, 65, TRUE),
    (179, 'Giấy A4 70 Ik Plus', '- Một trong những đặc điểm vượt bậc của giấy photocopy IK Plus chính là độ dai bền của xớ giấy, giúp giấy không bị cong vênh, không bị kẹt vào máy khi in - photocopy dưới tốc độ nhanh và nhiệt độ cao.\n\n- Phù hợp với hầu hết các loại Máy in phun, Máy in Laser, Máy Fax laser, Máy Photocopy', 7, 16, 65000.00, 63700.00, 106, 34, TRUE),
    (180, 'Giấy in văn phòng IK Plus A4 80gsm', '(1 Ream) Giấy in văn phòng IK Plus A4 80gsm\n\nThông tin chung\n\nLoại sản phẩm\n\nGiấy In Văn Phòng IK Plus A4 80gsm\n\nHãng sản xuất IK Plus\n\nChức năng In văn phản\n\nMàu sắc Trắng\n\nChất liệu Giấy láng, mịn, trắng bóng\n\nCông nghệ in In phun, in laser màu\n\nTÍNH NĂNG NỔI BẬT\n\nĐộ dày giấy 80gsm\n\nKháng nước Tương đối\n\nIn mực chính hãng >= 98% độ lên màu\n\nIn mực Inktec >=95% độ lên màu\n\nIn mực Dye UV +/- 90-95% độ lên màu\n\nIn mực Pigment UV  +/- 85 - 90% độ lên màu\n\nThông số kỹ thuật\n\nKích thước 210x297mm\n\nSố lượng mặt in 2 mặt\n\nQui cách đóng gói 500 tờ/gram\n\nBảo quản Điều kiện bảo quản tốt nhất từ 20 - 25 độ C\n\n Giấy in IK Plus A4 sở hữu độ trắng sáng tiêu chuẩn, mịn và bám màu mực tốt.  Giấy đáp ứng nhu cầu in ấn của hầu hết các loại máy in,máy', 7, 16, 82500.00, 62700.00, 140, 88, TRUE),
    (181, '50/100 tờ giấy đôi kiểm tra 4 ô ly/5 ô ly', 'Mô tả từ nhà sản xuất :\n\n- Kích thước:  155x205mm\n\n- Định lượng 80 gsm.\n\n- Giấy trắng cao cấp.\n\n- Dòng kẻ in - rõ nét', 7, 18, 25999.00, 23399.10, 162, 76, TRUE),
    (182, 'Túi 15 Tờ Giấy Kiểm Tra 4 Ô Ly', 'THÔNG TIN SẢN PHẨM\n\n+ Kích thước: A5, 205mm x 155mm (+/-2) mm\n\n+ Số tờ:  15 tờ đôi\n\n+ Định lượng giấy: 80 gsm\n\n+ Dòng kẻ:  4 ô ly (ô vuông caro 2x2 mm) - KT814/Ô ly ngang - KT811', 7, 18, 11000.00, 8690.00, 175, 68, TRUE),
    (183, 'Giấy Kiểm Tra 156x205mm Dày 80 Gsm', 'Mặt giấy mịn, viết êm tay, không thấm mực\n\nCác thông tin cần thiết được in rõ ràng\n\nDòng kẻ ô ly giúp các em viết chữ ngay ngắn\n\nSử dụng độ trắng không làm hại mắt\n\nĐịnh lượng 80gsm ăn mực hầu hết các loại bút\n\nLốc 200 Tờ Giấy Kiểm Tra Hòa Bình có mặt giấy láng mịn, viết êm tay, tạo nét chữ đẹp.  Với định lượng 80gsm ăn mực hầu hết các loại bút, giấy viết không nhòe, không thấm mực ra trang sau.  Chất liệu giấy không bụi, đảm bảo sức khỏe cho người sử dụng, đặc biệt là trẻ em.\n\nQuyển tập có đường kẻ ô ly rõ ràng, đều đặn giúp các em học sinh viết chữ đẹp hơn, nắn nót hơn. Tập kiểm tra giúp cho các em học sinh sử dụng để làm bài kiểm tra, các thông tin cần thiết được in rõ ràng như: trường, lớp, họ và tên, môn, thời gian... được in rõ ràng, trình bày khoa học.\n\nSản phẩm được đựng cẩn thận trong túi bóng, sẽ là người bạn đồng hành giúp các bạn nhỏ học tốt hơn, góp phần đưa các em vươn tới sức mạnh của tri thức.', 7, 18, 30001.00, 19500.65, 149, 79, TRUE),
    (184, 'Giấy kiểm tra kẻ ngang Campus', '𝐆𝐢𝐚̂́𝐲 𝐤𝐢𝐞̂̉𝐦 𝐭𝐫𝐚 𝐂𝐚𝐦𝐩𝐮𝐬\n\n\t-NBTPBM70 :  Giấy KT cấp II có tờ đơn và đôi BM70 (20 tờ đôi - 10 tờ đơn)\n\n\t-NBTPBR70 : Giấy KT cấp II không chấm (25 tờ đôi)\n\n\t-NBTPBS70 : Giấy KT cấp II có chấm (25 tờ đôi)\n\n        -BRMEO70/25:Giấy KT cấp II  tờ đôi\n\n        -TPNB5L30 -30:Giấy KT cấp II  tờ đôi', 7, 18, 37000.00, 33300.00, 126, 11, TRUE),
    (185, 'Lốc 200 đôi 10x20 Giấy kiểm tra học sinh - Mẫu 80gsm', '', 7, 18, 89460.00, 78724.80, 193, 7, TRUE),
    (186, 'Combo 2 tập giấy kiểm tra cấp II, câp III, set 20 tờ đôi giấy chống lóa', 'Giấy kiểm tra cấp 2, 3 set 20 tờ đôi B5 Klong định lượng 100/76 chống lóa MS 279,280 giấy làm bài Klong\n\nĐịnh lượng\t100 g/m2\n\nĐộ trắng\t76 %ISO\n\nSố trang\t20 tờ kép/tập\n\nKích thước\t(175 x 250) mm\n\n- Giấy trắng kem tự nhiên (76% ISO) không lóa mỏi mắt khi đọc và viết;\n\n- Giấy có định lượng cao 100 g/m2 không lem nhòe với các loại mực, màu gốc nước; \n\n- Được in offset dòng kẻ ngang nét mảnh và các chấm so le sắc nét, rõ ràng thuận tiện khi làm bài kiểm tra, dựng hình kẻ bảng, vẽ đồ thị;\n\n- Giấy được gấp tự động từng tờ và cắt góc tránh quăn mép khi sử dụng;', 7, 18, 32800.00, 23288.00, 151, 10, TRUE),
    (187, '(50 tờ) Giấy thi A3 Bộ, Sở giấy thi văn thpt giấy thi cấp 3 ĐL 70gms viết không nhòe', '𝐓𝐇𝐎̂𝐍𝐆 𝐓𝐈𝐍 𝐒𝐀̉𝐍 𝐏𝐇𝐀̂̉𝐌 𝐆𝐈𝐀̂́𝐘 𝐓𝐇𝐈\n\n\n\n-Sản phẩm có 2 mẫu :  Giấy thi Bộ Giáo Dục Đào Tạo - Giấy thi Sở Kế hoạch Hà Nội\n\n-Khổ Giấy A3 :  \n\n   +Giấy thi Bộ Giáo Dục Đào tạo :  Ngang 42.5 - Cao 30cm\n\n   +Giấy thi Sở Giáo Dục Hà Nội  : Ngang 44.8 - Cao 30cm\n\n-Định lượng giấy 70mgs \n\n-Độ trắng :  90 - Giấy mịn đẹp theo tiêu chuẩn Bãi Bằng\n\n-Mẫu tiêu chuẩn Bộ giáo dục - Sở quy định\n\n\n\n-Sản phẩm được đặt in tại xưởng\n\n-Hình ảnh shop tự chụp, mẫu giấy tối hơn so với thực tế', 7, 18, 20902.00, 15049.44, 188, 19, TRUE),
    (188, 'Set 50 tờ giấy thi khổ A3 rọc phách- giấy viết văn giá rẻ', 'Set 50 tờ giấy thi rọc phách khổ A3 giá rẻ\n\n- Giấy đẹp , trắng sáng,\n\n- Giấy Khổ A3 chuẩn mẫu, giá rẻ\n\n- Định lượng giấy 60gsm\n\n- Giấy trắng sáng , mịn, đường kẻ, mực rõ ràng \n\n- Sản phẩm chất lượng, đóng gói cẩn thận, \n\n Quy cách đóng gói:  20 tờ/ set', 7, 18, 16727.00, 11708.90, 187, 28, TRUE),
    (189, 'Giấy thi A3 chuẩn mẫu Bộ Giáo dục và đào tạo, giấy kỳ thi tự luận học sinh', 'Mô Tả Sản Phẩm\n\nMẫu giấy thi tự luận là mẫu giấy dùng để thi tự luận cho các thí sinh.  Mẫu được dùng nhiều trong các kỳ thi tốt nghiệp trung học phổ thông.  Mẫu có đầy đủ nội dung thông tin của thí sinh, hội đồng coi thi, chấm thi và số phá\n\nĐịnh lượng giấy:  65gms\n\nShop cố gắng giao đúng đủ hàng, nếu trong khi đếm có tàu hoả nhập ma thiếu 1-2 tờ thì các bạn thông cảm nhé.  Mà thường thì không bị thiếu đâu\n\nCAM KẾT :  GIẤY DÀY, TRẮNG, IN NÉT\n\nShop chuyên cung cấp số lượng lớn :\n\n- Hồ sơ học sinh sinh viên, hồ sơ công chức,hồ sơ đảng viên, hồ sơ xin việc\n\n- Sổ đoàn, chi đoàn ,huy hiệu, thẻ đoàn\n\n- Lý lịch đảng viên, lý lịch của người xin vào đảng\n\n- Các biểu mẫu thu, chi, xuất, nhập, hoá đơn bán lẻ\n\n- Phong bì trắng, phong bì nâu các cỡ', 7, 18, 13000.00, 12350.00, 141, 56, TRUE),
--Insert sản phẩm của Dũng
      (191, 'Túi tote học sinh Jamlos', 'Túi tote vải canvas phong cách, đựng vừa sách vở A4, thích hợp đi học thêm hoặc dạo phố.', 4, 17, 150000.00, 130000.00, 50, 60, TRUE),
      (192, 'Túi đựng bút Jamlos', 'Túi vải canvas nhỏ gọn, thiết kế tối giản, bền đẹp.', 4, 17, 50000.00, 45000.00, 80, 40, TRUE),
      (193, 'Balo laptop Jamlos', 'Thiết kế hiện đại, ngăn chống sốc cho laptop, chất liệu trượt nước.', 4, 17, 400000.00, 380000.00, 30, 20, TRUE),
      (194, 'Keo dán khô Điểm 10', 'Màu keo trắng, độ kết dính cao, keo bền, mau khô, không độc hại cho người sử dụng, đóng nắp kỹ sau khi sử dụng để tránh keo bị khô.', 3, 3, 15000.00, 12000.00, 150, 55, TRUE),
      (195, 'Màu nước Colokit', 'Màu nước được đựng kín trong lọ 6ml bằng nhựa trắng trong suốt và có 8 màu sắc khác nhau.', 3, 7, 25000.00, 18000.00, 78, 24, TRUE),
      (196, 'Kéo học sinh DL', 'Lưỡi kéo được thiết kế bằng thép không rỉ.Cảm giác cắt nhẹ nhàng, lưỡi kéo lâu cùn.Dễ dàng sử dụng để cắt giấy và cắt thủ công', 3, 8, 15000.00, 13000.00, 120, 41, TRUE),
      (197, 'Kéo học sinh TL', 'Lưỡi kéo được thiết kế bằng thép không rỉ.Cảm giác cắt nhẹ nhàng, lưỡi kéo lâu cùn.Dễ dàng sử dụng để cắt giấy và cắt thủ công', 3, 1, 16000.00, 14000.00, 78, 58, TRUE),
      (198, 'Kéo học sinh Panda Điểm 10', 'Kéo học sinh Loại kéo nhỏ dành cho học sinh làm thủ công, được làm bằng kim loại không gỉ, mũi kéo tròn để bảo vệ an toàn cho trẻ khi sử dụng. Tay cầm bằng nhựa giúp cắt nhẹ nhàng, không đau tay. hai lưỡi đồng được giữ áp sát vào nhau một cách linh hoạt nhằm để giữ hai lưỡi kéo tại đúng vị trí, cho phép chúng được ép sát lại với nhau.', 3, 3, 20000.00, 18000.00, 20, 120, TRUE),
      (199, 'Kéo học sinh Kẹo CLK', 'Loại kéo nhỏ dành cho học sinh làm thủ công, được làm bằng kim loại không gỉ, mũi kéo tròn để bảo vệ an toàn cho trẻ khi sử dụng. Tay cầm bằng nhựa giúp cắt nhẹ nhàng', 3, 7, 20000.00, 18000.00, 47, 52, TRUE),
      (200, 'Bao gồm: 6 mẫu lưỡi cắt và 1 cán kéo.Có cán làm từ nhựa ABS, thân làm bằng thép không gỉ, dễ dàng tháo lắp. Bộ kéo thủ công Colokit SC-C05 phù hơp cho học sinh tiểu học. Kiểu dáng trang nhã, màu sắc phong phú.', 3, 7, 80000.00, 75000.00, 34, 23, TRUE)
      ;
--Insert hình ảnh sản phẩm
INSERT INTO product_images (id, product_id, image_url, is_primary,create_at) VALUES
    -- Bút viết
    (1,1, 'src/main/webapp/assets/images/products/ButViet/1-butgel-1.jpg', FALSE, NOW()),
    (2,1, 'src/main/webapp/assets/images/products/ButViet/1-butgel-2.jpg', FALSE, NOW()),
    (3,1, 'src/main/webapp/assets/images/products/ButViet/1-butgel-pri.jpg', TRUE,NOW()),

    (4,2, 'src/main/webapp/assets/images/products/ButViet/2-butgel-1.jpg', FALSE, NOW()),
    (5,2, 'src/main/webapp/assets/images/products/ButViet/2-butgel-2.jpg', FALSE, NOW()),
    (6,2, 'src/main/webapp/assets/images/products/ButViet/2-butgel-pri.jpg', TRUE,NOW()),

    (7,3, 'src/main/webapp/assets/images/products/ButViet/3-butgel-1.jpg', FALSE, NOW()),
    (8,3, 'src/main/webapp/assets/images/products/ButViet/3-butgel-pri.jpg', TRUE,NOW()),

    (9,4, 'src/main/webapp/assets/images/products/ButViet/4-butgel-1.jpg', FALSE, NOW()),
    (10,4, 'src/main/webapp/assets/images/products/ButViet/4-butgel-2.jpg', FALSE, NOW()),
    (11,4, 'src/main/webapp/assets/images/products/ButViet/4-butgel-pri.jpg', TRUE,NOW()),

    (13,5, 'src/main/webapp/assets/images/products/ButViet/5-butgel-1.jpg', FALSE, NOW()),
    (14,5, 'src/main/webapp/assets/images/products/ButViet/5-butgel-2.jpg', FALSE, NOW()),
    (15,5, 'src/main/webapp/assets/images/products/ButViet/5-butgel-pri.jpg', TRUE,NOW()),

    (16,6, 'src/main/webapp/assets/images/products/ButViet/6-butgel-1.jpg', FALSE, NOW()),
    (17,6, 'src/main/webapp/assets/images/products/ButViet/6-butgel-2.jpg', FALSE, NOW()),
    (18,6, 'src/main/webapp/assets/images/products/ButViet/6-butgel-pri.jpg', TRUE,NOW()),

    (19,7, 'src/main/webapp/assets/images/products/ButViet/7-butgel-1.jpg', FALSE, NOW()),
    (20,7, 'src/main/webapp/assets/images/products/ButViet/7-butgel-2.jpg', FALSE, NOW()),
    (21,7, 'src/main/webapp/assets/images/products/ButViet/7-butgel-pri.jpg', TRUE,NOW()),

    (22,8, 'src/main/webapp/assets/images/products/ButViet/8-butchi-1.jpg', FALSE, NOW()),
    (23,8, 'src/main/webapp/assets/images/products/ButViet/8-butchi-2.jpg', FALSE, NOW()),
    (24,8, 'src/main/webapp/assets/images/products/ButViet/8-butchi-pri.jpg', TRUE,NOW()),

    (25,9, 'src/main/webapp/assets/images/products/ButViet/9-butchi-1.jpg', FALSE, NOW()),
    (26,9, 'src/main/webapp/assets/images/products/ButViet/9-butchi-2.jpg', FALSE, NOW()),
    (27,9, 'src/main/webapp/assets/images/products/ButViet/9-butchi-pri.jpg', TRUE,NOW()),

    (28,10, 'src/main/webapp/assets/images/products/ButViet/10-butchi-1.jpg', FALSE, NOW()),
    (29,10, 'src/main/webapp/assets/images/products/ButViet/10-butchi-2.jpg', FALSE, NOW()),
    (30,10, 'src/main/webapp/assets/images/products/ButViet/10-butchi-pri.jpg', TRUE,NOW()),

    (31,11, 'src/main/webapp/assets/images/products/ButViet/11-butchi-1.jpg', FALSE, NOW()),
    (32,11, 'src/main/webapp/assets/images/products/ButViet/11-butchi-2.jpg', FALSE, NOW()),
    (33,11, 'src/main/webapp/assets/images/products/ButViet/11-butchi-pri.jpg', TRUE,NOW()),

    (34,12, 'src/main/webapp/assets/images/products/ButViet/12-butchi-1.jpg', FALSE, NOW()),
    (35,12, 'src/main/webapp/assets/images/products/ButViet/12-butchi-2.jpg', FALSE, NOW()),
    (36,12, 'src/main/webapp/assets/images/products/ButViet/12-butchi-pri.jpg', TRUE,NOW()),

    (37,13, 'src/main/webapp/assets/images/products/ButViet/13-butchi-1.jpg', FALSE, NOW()),
    (38,13, 'src/main/webapp/assets/images/products/ButViet/13-butchi-2.jpg', FALSE, NOW()),
    (39,13, 'src/main/webapp/assets/images/products/ButViet/13-butchi-pri.jpg', TRUE,NOW()),

    (40,14, 'src/main/webapp/assets/images/products/ButViet/14-butchi-1.jpg', FALSE, NOW()),
    (41,14, 'src/main/webapp/assets/images/products/ButViet/14-butchi-2.jpg', FALSE, NOW()),
    (42,14, 'src/main/webapp/assets/images/products/ButViet/14-butchi-pri.jpg', TRUE,NOW()),

    (43,15, 'src/main/webapp/assets/images/products/ButViet/15-butchi-1.jpg', FALSE, NOW()),
    (44,15, 'src/main/webapp/assets/images/products/ButViet/15-butchi-pri.jpg', TRUE,NOW()),

    (45,16, 'src/main/webapp/assets/images/products/ButViet/16-ongmuc-1.jpg', FALSE, NOW()),
    (46,16, 'src/main/webapp/assets/images/products/ButViet/16-ongmuc-pri.jpg', TRUE, NOW()),

    (47,17, 'src/main/webapp/assets/images/products/ButViet/17-mucbutmay-1.jpg', FALSE, NOW()),
    (48,17, 'src/main/webapp/assets/images/products/ButViet/17-mucbutmay-pri.jpg', TRUE,NOW()),

    (49,18, 'src/main/webapp/assets/images/products/ButViet/18-butmay-1.jpg', FALSE, NOW()),
    (50,18, 'src/main/webapp/assets/images/products/ButViet/18-butmay-pri.jpg', TRUE,NOW()),

    (51,19, 'src/main/webapp/assets/images/products/ButViet/19-bongoi-1.jpg', FALSE, NOW()),
    (52,19, 'src/main/webapp/assets/images/products/ButViet/19-bongoi-pri.jpg', TRUE,NOW()),

    (53,20, 'src/main/webapp/assets/images/products/ButViet/20-butxoa-1.jpg', FALSE, NOW()),
    (54,20, 'src/main/webapp/assets/images/products/ButViet/20-butxoa-2.jpg', FALSE, NOW()),
    (55,20, 'src/main/webapp/assets/images/products/ButViet/20-butxoa-pri.jpg', TRUE,NOW()),

    (56,21, 'src/main/webapp/assets/images/products/ButViet/21-butxoa-1.jpg', FALSE, NOW()),
    (57,21, 'src/main/webapp/assets/images/products/ButViet/21-butxoa-2.jpg', FALSE, NOW()),
    (58,21, 'src/main/webapp/assets/images/products/ButViet/21-butxoa-pri.jpg', TRUE,NOW()),

    (59,22, 'src/main/webapp/assets/images/products/ButViet/22-butxoa-1.jpg', FALSE, NOW()),
    (60,22, 'src/main/webapp/assets/images/products/ButViet/22-butxoa-2.jpg', FALSE, NOW()),
    (61,22, 'src/main/webapp/assets/images/products/ButViet/22-butxoa-pri.jpg', TRUE,NOW()),

    (62,23, 'src/main/webapp/assets/images/products/ButViet/23-butxoa-1.jpg', FALSE, NOW()),
    (63,23, 'src/main/webapp/assets/images/products/ButViet/23-butxoa-2.jpg', FALSE, NOW()),
    (64,23, 'src/main/webapp/assets/images/products/ButViet/23-butxoa-pri.jpg', TRUE,NOW()),

    (65,24, 'src/main/webapp/assets/images/products/ButViet/24-butxoa-1.jpg', FALSE, NOW()),
    (66,24, 'src/main/webapp/assets/images/products/ButViet/24-butxoa-2.jpg', FALSE, NOW()),
    (67,24, 'src/main/webapp/assets/images/products/ButViet/24-butxoa-pri.jpg', TRUE,NOW()),

    (68,25, 'src/main/webapp/assets/images/products/ButViet/25-butxoa-1.jpg', FALSE, NOW()),
    (69,25, 'src/main/webapp/assets/images/products/ButViet/25-butxoa-2.jpg', FALSE, NOW()),
    (70,25, 'src/main/webapp/assets/images/products/ButViet/25-butxoa-pri.jpg', TRUE,NOW()),

    (71,26, 'src/main/webapp/assets/images/products/ButViet/26-butbi-1.jpg', FALSE, NOW()),
    (72,26, 'src/main/webapp/assets/images/products/ButViet/26-butbi-pri.jpg', TRUE,NOW()),

    (73,27, 'src/main/webapp/assets/images/products/ButViet/27-butbi-1.jpg', FALSE, NOW()),
    (74,27, 'src/main/webapp/assets/images/products/ButViet/27-butbi-2.jpg', FALSE, NOW()),
    (75,27, 'src/main/webapp/assets/images/products/ButViet/27-butbi-pri.jpg', TRUE,NOW()),

    (76,28, 'src/main/webapp/assets/images/products/ButViet/28-butbi-1.jpg', FALSE, NOW()),
    (77,28, 'src/main/webapp/assets/images/products/ButViet/28-butbi-pri.jpg', TRUE,NOW()),

    (78,29, 'src/main/webapp/assets/images/products/ButViet/29-butbi-1.jpg', FALSE, NOW()),
    (79,29, 'src/main/webapp/assets/images/products/ButViet/29-butbi-2.jpg', FALSE, NOW()),
    (80,29, 'src/main/webapp/assets/images/products/ButViet/29-butbi-pri.jpg', TRUE,NOW()),

    (81,30, 'src/main/webapp/assets/images/products/ButViet/30-butbi-1.jpg', FALSE, NOW()),
    (82,30, 'src/main/webapp/assets/images/products/ButViet/30-butbi-2.jpg', FALSE, NOW()),
    (83,30, 'src/main/webapp/assets/images/products/ButViet/30-butbi-pri.jpg', TRUE,NOW()),

    (84,31, 'src/main/webapp/assets/images/products/ButViet/31-butbi-1.jpg', FALSE, NOW()),
    (85,31, 'src/main/webapp/assets/images/products/ButViet/31-butbi-pri.jpg', TRUE,NOW()),

    (86,32, 'src/main/webapp/assets/images/products/ButViet/32-butbi-1.jpg', FALSE, NOW()),
    (87,32, 'src/main/webapp/assets/images/products/ButViet/32-butbi-2.jpg', FALSE, NOW()),
    (88,32, 'src/main/webapp/assets/images/products/ButViet/32-butbi-pri.jpg', TRUE,NOW()),

    (89,33, 'src/main/webapp/assets/images/products/ButViet/33-butbi-1.jpg', FALSE, NOW()),
    (90,33, 'src/main/webapp/assets/images/products/ButViet/33-butbi-pri.jpg', TRUE,NOW()),

    (91,34, 'src/main/webapp/assets/images/products/ButViet/34-butlong-1.jpg', FALSE, NOW()),
    (92,34, 'src/main/webapp/assets/images/products/ButViet/34-butlong-2.jpg', FALSE, NOW()),
    (93,34, 'src/main/webapp/assets/images/products/ButViet/34-butlong-pri.jpg', TRUE,NOW()),

    (94,35, 'src/main/webapp/assets/images/products/ButViet/35-butbi-1.jpg', FALSE, NOW()),
    (95,35, 'src/main/webapp/assets/images/products/ButViet/35-butbi-2.jpg', FALSE, NOW()),
    (96,35, 'src/main/webapp/assets/images/products/ButViet/35-butbi-pri.jpg', TRUE,NOW()),

    (97,36, 'src/main/webapp/assets/images/products/ButViet/36-butlong-1.jpg', FALSE, NOW()),
    (98,36, 'src/main/webapp/assets/images/products/ButViet/36-butlong-pri.jpg', TRUE,NOW()),

    (99,37, 'src/main/webapp/assets/images/products/ButViet/37-butlong-1.jpg', FALSE, NOW()),
    (100,37, 'src/main/webapp/assets/images/products/ButViet/37-butlong-pri.jpg', TRUE,NOW()),

    (101,38, 'src/main/webapp/assets/images/products/ButViet/38-butdaquang-1.jpg', FALSE, NOW()),
    (102,38, 'src/main/webapp/assets/images/products/ButViet/38-butdaquang-pri.jpg', TRUE,NOW()),

    (103,39, 'src/main/webapp/assets/images/products/ButViet/39-butdaquang-1.jpg', FALSE, NOW()),
    (104,39, 'src/main/webapp/assets/images/products/ButViet/39-butdaquang-pri.jpg', TRUE,NOW()),
    --Sổ vở
    (105,40, 'src/main/webapp/assets/images/products/SoVo/40-vo-1.jpg', FALSE, NOW()),
    (106,40, 'src/main/webapp/assets/images/products/SoVo/40-vo-2.jpg', FALSE, NOW()),
    (107,40, 'src/main/webapp/assets/images/products/SoVo/40-vo-pri.jpg', TRUE,NOW()),

    (108,41, 'src/main/webapp/assets/images/products/SoVo/41-vo-1.jpg', FALSE, NOW()),
    (109,41, 'src/main/webapp/assets/images/products/SoVo/41-vo-2.jpg', FALSE, NOW()),
    (110,41, 'src/main/webapp/assets/images/products/SoVo/41-vo-pri.jpg', TRUE,NOW()),

    (111,42, 'src/main/webapp/assets/images/products/SoVo/42-vo-1.jpg', FALSE, NOW()),
    (112,42, 'src/main/webapp/assets/images/products/SoVo/42-vo-2.jpg', FALSE, NOW()),
    (113,42, 'src/main/webapp/assets/images/products/SoVo/42-vo-pri.jpg', TRUE,NOW()),

    (114,43, 'src/main/webapp/assets/images/products/SoVo/43-vo-1.jpg', FALSE, NOW()),
    (115,43, 'src/main/webapp/assets/images/products/SoVo/43-vo-2.jpg', FALSE, NOW()),
    (116,43, 'src/main/webapp/assets/images/products/SoVo/43-vo-pri.jpg', TRUE,NOW()),

    (117,44, 'src/main/webapp/assets/images/products/SoVo/44-vo-1.jpg', FALSE, NOW()),
    (118,44, 'src/main/webapp/assets/images/products/SoVo/44-vo-2.jpg', FALSE, NOW()),
    (119,44, 'src/main/webapp/assets/images/products/SoVo/44-vo-pri.jpg', TRUE,NOW()),

    (120,45, 'src/main/webapp/assets/images/products/SoVo/45-vo-1.jpg', FALSE, NOW()),
    (121,45, 'src/main/webapp/assets/images/products/SoVo/45-vo-2.jpg', FALSE, NOW()),
    (122,45, 'src/main/webapp/assets/images/products/SoVo/45-vo-pri.jpg', TRUE,NOW()),

    (123,46, 'src/main/webapp/assets/images/products/SoVo/46-vo-1.jpg', FALSE, NOW()),
    (124,46, 'src/main/webapp/assets/images/products/SoVo/46-vo-2.jpg', FALSE, NOW()),
    (125,46, 'src/main/webapp/assets/images/products/SoVo/46-vo-pri.jpg', TRUE,NOW()),

    (126,47, 'src/main/webapp/assets/images/products/SoVo/47-vo-1.jpg', FALSE, NOW()),
    (127,47, 'src/main/webapp/assets/images/products/SoVo/47-vo-pri.jpg', TRUE,NOW()),

    (128,48, 'src/main/webapp/assets/images/products/SoVo/48-vo-1.jpg', FALSE, NOW()),
    (129,48, 'src/main/webapp/assets/images/products/SoVo/48-vo-2.jpg', FALSE, NOW()),
    (130,48, 'src/main/webapp/assets/images/products/SoVo/48-vo-pri.jpg', TRUE,NOW()),

    (131,49, 'src/main/webapp/assets/images/products/SoVo/49-vo-1.jpg', FALSE, NOW()),
    (132,49, 'src/main/webapp/assets/images/products/SoVo/49-vo-pri.jpg', TRUE,NOW()),

    (133,50, 'src/main/webapp/assets/images/products/SoVo/50-vo-1.jpg', FALSE, NOW()),
    (134,50, 'src/main/webapp/assets/images/products/SoVo/50-vo-pri.jpg', TRUE,NOW()),

    (135,51, 'src/main/webapp/assets/images/products/SoVo/51-vo-1.jpg', FALSE, NOW()),
    (136,51, 'src/main/webapp/assets/images/products/SoVo/51-vo-pri.jpg', TRUE,NOW()),

    (137,52, 'src/main/webapp/assets/images/products/SoVo/52-vo-1.jpg', FALSE, NOW()),
    (138,52, 'src/main/webapp/assets/images/products/SoVo/52-vo-pri.jpg', TRUE,NOW()),

    (139,53, 'src/main/webapp/assets/images/products/SoVo/53-vo-1.jpg', FALSE, NOW()),
    (140,53, 'src/main/webapp/assets/images/products/SoVo/53-vo-pri.jpg', TRUE,NOW()),

    (141,54, 'src/main/webapp/assets/images/products/SoVo/54-vo-1.jpg', FALSE, NOW()),
    (142,54, 'src/main/webapp/assets/images/products/SoVo/54-vo-pri.jpg', TRUE,NOW()),

    (143,55, 'src/main/webapp/assets/images/products/SoVo/55-vo-1.jpg', FALSE, NOW()),
    (144,55, 'src/main/webapp/assets/images/products/SoVo/55-vo-2.jpg', FALSE, NOW()),
    (145,55, 'src/main/webapp/assets/images/products/SoVo/55-vo-pri.jpg', TRUE,NOW()),

    (147,56, 'src/main/webapp/assets/images/products/SoVo/56-vo-1.jpg', FALSE, NOW()),
    (148,56, 'src/main/webapp/assets/images/products/SoVo/56-vo-pri.jpg', TRUE,NOW()),

    (149,57, 'src/main/webapp/assets/images/products/SoVo/57-vo-1.jpg', FALSE, NOW()),
    (150,57, 'src/main/webapp/assets/images/products/SoVo/57-vo-2.jpg', FALSE, NOW()),
    (151,57, 'src/main/webapp/assets/images/products/SoVo/57-vo-pri.jpg', TRUE,NOW()),

    (152,58, 'src/main/webapp/assets/images/products/SoVo/58-vo-1.jpg', FALSE, NOW()),
    (153,58, 'src/main/webapp/assets/images/products/SoVo/58-vo-pri.jpg', TRUE,NOW()),

    (154,59, 'src/main/webapp/assets/images/products/SoVo/59-vo-1.jpg', FALSE, NOW()),
    (155,59, 'src/main/webapp/assets/images/products/SoVo/59-vo-pri.jpg', TRUE,NOW()),

    (156,60, 'src/main/webapp/assets/images/products/SoVo/60-so-1.jpg', FALSE, NOW()),
    (157,60, 'src/main/webapp/assets/images/products/SoVo/60-so-2.jpg', FALSE,NOW()),
    (158,60, 'src/main/webapp/assets/images/products/SoVo/60-so-pri.jpg', TRUE, NOW()),

    (159,61, 'src/main/webapp/assets/images/products/SoVo/61-so-1.jpg', FALSE, NOW()),
    (160,61, 'src/main/webapp/assets/images/products/SoVo/61-so-pri.jpg', TRUE,NOW()),

    (161,62, 'src/main/webapp/assets/images/products/SoVo/62-so-1.jpg', FALSE, NOW()),
    (162,62, 'src/main/webapp/assets/images/products/SoVo/62-so-2.jpg', FALSE,NOW()),
    (163,62, 'src/main/webapp/assets/images/products/SoVo/62-so-3.jpg', FALSE,NOW()),
    (163,62, 'src/main/webapp/assets/images/products/SoVo/62-so-pri.jpg', TRUE,NOW()),

    (164,63, 'src/main/webapp/assets/images/products/SoVo/63-so-1.jpg', FALSE, NOW()),
    (165,63, 'src/main/webapp/assets/images/products/SoVo/63-so-2.jpg', FALSE, NOW()),
    (166,63, 'src/main/webapp/assets/images/products/SoVo/63-so-pri.jpg', TRUE,NOW()),

    (167,64, 'src/main/webapp/assets/images/products/SoVo/64-so-1.jpg', FALSE, NOW()),
    (168,64, 'src/main/webapp/assets/images/products/SoVo/64-so-pri.jpg', TRUE,NOW()),
    --Dụng cụ vẽ
    (169,65, 'src/main/webapp/assets/images/products/DungCuVe/65-dcv-1.jpg', FALSE, NOW()),
    (170,65, 'src/main/webapp/assets/images/products/DungCuVe/65-dcv-pri.jpg', TRUE,NOW()),

    (171,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-1.jpg', FALSE, NOW()),
    (172,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-2.jpg', FALSE,NOW()),
    (173,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-3.jpg', FALSE, NOW()),
    (174,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-4.jpg', FALSE, NOW()),
    (175,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-5.jpg', FALSE, NOW()),
    (176,66, 'src/main/webapp/assets/images/products/DungCuVe/66-dcv-pri.jpg', TRUE,NOW()),

    (177,67, 'src/main/webapp/assets/images/products/DungCuVe/67-dcv-1.jpg', FALSE, NOW()),
    (178,67, 'src/main/webapp/assets/images/products/DungCuVe/67-dcv-pri.jpg', TRUE,NOW()),
    (179,67, 'src/main/webapp/assets/images/products/DungCuVe/67-dcv-2.jpg', FALSE, NOW()),

    (180,68, 'src/main/webapp/assets/images/products/DungCuVe/68-dcv-1.jpg', FALSE, NOW()),
    (181,68, 'src/main/webapp/assets/images/products/DungCuVe/68-dcv-2.jpg', FALSE, NOW()),
    (182,68, 'src/main/webapp/assets/images/products/DungCuVe/68-dcv-pri.jpg', TRUE,NOW()),

    (183,69, 'src/main/webapp/assets/images/products/DungCuVe/69-dcv-1.jpg', FALSE, NOW()),
    (184,69, 'src/main/webapp/assets/images/products/DungCuVe/69-dcv-2.jpg', FALSE, NOW()),
    (185,69, 'src/main/webapp/assets/images/products/DungCuVe/69-dcv-pri.jpg', TRUE,NOW()),

    (186,70, 'src/main/webapp/assets/images/products/DungCuVe/70-dcv-1.jpg', FALSE, NOW()),
    (187,70, 'src/main/webapp/assets/images/products/DungCuVe/70-dcv-2.jpg', FALSE, NOW()),
    (188,70, 'src/main/webapp/assets/images/products/DungCuVe/70-dcv-pri.jpg', TRUE,NOW()),

    (189,71, 'src/main/webapp/assets/images/products/DungCuVe/71-dcv-1.jpg', FALSE, NOW()),
    (190,71, 'src/main/webapp/assets/images/products/DungCuVe/71-dcv-2.jpg', FALSE, NOW()),
    (191,71, 'src/main/webapp/assets/images/products/DungCuVe/71-dcv-3.jpg', FALSE, NOW()),
    (192,71, 'src/main/webapp/assets/images/products/DungCuVe/71-dcv-pri.jpg', TRUE,NOW()),

    (193,72, 'src/main/webapp/assets/images/products/DungCuVe/72-dcv-1.jpg', FALSE, NOW()),
    (194,72, 'src/main/webapp/assets/images/products/DungCuVe/72-dcv-2.jpg', FALSE, NOW()),
    (195,72, 'src/main/webapp/assets/images/products/DungCuVe/72-dcv-pri.jpg', TRUE,NOW()),

    (196,73, 'src/main/webapp/assets/images/products/DungCuVe/73-dcv-1.jpg', FALSE, NOW()),
    (197,73, 'src/main/webapp/assets/images/products/DungCuVe/73-dcv-pri.jpg', TRUE,NOW()),

    (198,74, 'src/main/webapp/assets/images/products/DungCuVe/74-dcv-1.jpg', FALSE, NOW()),
    (199,74, 'src/main/webapp/assets/images/products/DungCuVe/74-dcv-2.jpg', FALSE, NOW()),
    (200,74, 'src/main/webapp/assets/images/products/DungCuVe/74-dcv-pri.jpg', TRUE,NOW()),

    (201,75, 'src/main/webapp/assets/images/products/DungCuVe/75-dcv-1.jpg', FALSE, NOW()),
    (202,75, 'src/main/webapp/assets/images/products/DungCuVe/75-dcv-pri.jpg', TRUE,NOW()),

    (203,76, 'src/main/webapp/assets/images/products/DungCuVe/76-dcv-1.jpg', FALSE, NOW()),
    (204,76, 'src/main/webapp/assets/images/products/DungCuVe/76-dcv-2.jpg', FALSE, NOW()),
    (205,76, 'src/main/webapp/assets/images/products/DungCuVe/76-dcv-pri.jpg', TRUE,NOW()),

    (206,77, 'src/main/webapp/assets/images/products/DungCuVe/77-dcv-1.jpg', FALSE, NOW()),
    (207,77, 'src/main/webapp/assets/images/products/DungCuVe/77-dcv-2.jpg', FALSE, NOW()),
    (208,77, 'src/main/webapp/assets/images/products/DungCuVe/77-dcv-pri.jpg', TRUE,NOW()),

    (209,78, 'src/main/webapp/assets/images/products/DungCuVe/78-dcv-1.jpg', FALSE, NOW()),
    (210,78, 'src/main/webapp/assets/images/products/DungCuVe/78-dcv-2.jpg', FALSE, NOW()),
    (211,78, 'src/main/webapp/assets/images/products/DungCuVe/78-dcv-pri.jpg', TRUE,NOW()),

    (212,79, 'src/main/webapp/assets/images/products/DungCuVe/79-dcv-1.jpg', FALSE, NOW()),
    (213,79, 'src/main/webapp/assets/images/products/DungCuVe/79-dcv-2.jpg', FALSE, NOW()),
    (214,79, 'src/main/webapp/assets/images/products/DungCuVe/79-dcv-pri.jpg', TRUE,NOW()),

    (215,80, 'src/main/webapp/assets/images/products/DungCuVe/80-dcv-1.jpg', FALSE, NOW()),
    (216,80, 'src/main/webapp/assets/images/products/DungCuVe/80-dcv-2.jpg', FALSE, NOW()),
    (217,80, 'src/main/webapp/assets/images/products/DungCuVe/80-dcv-pri.jpg', TRUE,NOW()),

    (218,190, 'src/main/webapp/assets/images/products/DungCuVe/190-dcv-1.jpg', FALSE, NOW()),
    (219,190, 'src/main/webapp/assets/images/products/DungCuVe/190-dcv-2.jpg', FALSE, NOW()),
    (220,190, 'src/main/webapp/assets/images/products/DungCuVe/190-dcv-pri.jpg', TRUE,NOW()),

    (221,191, 'src/main/webapp/assets/images/products/DungCuVe/191-dcv-1.jpg', FALSE, NOW()),
    (222,191, 'src/main/webapp/assets/images/products/DungCuVe/191-dcv-2.jpg', FALSE, NOW()),
    (223,191, 'src/main/webapp/assets/images/products/DungCuVe/191-dcv-pri.jpg', TRUE,NOW()),

    (224,192, 'src/main/webapp/assets/images/products/DungCuVe/192-dcv-1.jpg', FALSE, NOW()),
    (225,192, 'src/main/webapp/assets/images/products/DungCuVe/192-dcv-2.jpg', FALSE, NOW()),
    (226,192, 'src/main/webapp/assets/images/products/DungCuVe/192-dcv-pri.jpg', TRUE,NOW()),

    (227,193, 'src/main/webapp/assets/images/products/DungCuVe/193-dcv-1.jpg', FALSE, NOW()),
    (228,193, 'src/main/webapp/assets/images/products/DungCuVe/193-dcv-2.jpg', FALSE, NOW()),
    (229,193, 'src/main/webapp/assets/images/products/DungCuVe/193-dcv-pri.jpg', TRUE,NOW()),

    (230,194, 'src/main/webapp/assets/images/products/DungCuVe/194-dcv-1.jpg', FALSE, NOW()),
    (231,194, 'src/main/webapp/assets/images/products/DungCuVe/194-dcv-2.jpg', FALSE, NOW()),
    (232,194, 'src/main/webapp/assets/images/products/DungCuVe/194-dcv-pri.jpg', TRUE,NOW()),

    (233,195, 'src/main/webapp/assets/images/products/DungCuVe/195-dcv-1.jpg', FALSE, NOW()),
    (234,195, 'src/main/webapp/assets/images/products/DungCuVe/195-dcv-2.jpg', FALSE, NOW()),
    (235,195, 'src/main/webapp/assets/images/products/DungCuVe/195-dcv-pri.jpg', TRUE,NOW()),

    (236,196, 'src/main/webapp/assets/images/products/DungCuVe/196-dcv-1.jpg', FALSE, NOW()),
    (237,196, 'src/main/webapp/assets/images/products/DungCuVe/196-dcv-pri.jpg', TRUE,NOW()),

    (238,197, 'src/main/webapp/assets/images/products/DungCuVe/197-dcv-1.jpg', FALSE, NOW()),
    (239,197, 'src/main/webapp/assets/images/products/DungCuVe/197-dcv-pri.jpg', TRUE,NOW()),

    (240,198, 'src/main/webapp/assets/images/products/DungCuVe/198-dcv-1.jpg', FALSE, NOW()),
    (241,198, 'src/main/webapp/assets/images/products/DungCuVe/198-dcv-1.jpg', FALSE, NOW()),
    (242,198, 'src/main/webapp/assets/images/products/DungCuVe/198-dcv-pri.jpg', TRUE,NOW()),

    (243,199, 'src/main/webapp/assets/images/products/DungCuVe/199-dcv-1.jpg', FALSE, NOW()),
    (244,199, 'src/main/webapp/assets/images/products/DungCuVe/199-dcv-1.jpg', FALSE, NOW()),
    (245,199, 'src/main/webapp/assets/images/products/DungCuVe/199-dcv-pri.jpg', TRUE,NOW()),

    (246,200, 'src/main/webapp/assets/images/products/DungCuVe/200-dcv-1.jpg', FALSE, NOW()),
    (247,200, 'src/main/webapp/assets/images/products/DungCuVe/200-dcv-1.jpg', FALSE, NOW()),
    (248,200, 'src/main/webapp/assets/images/products/DungCuVe/200-dcv-pri.jpg', TRUE,NOW()),

    (366,145, 'src/main/webapp/assets/images/products/DenHoc/145-Denhoc-1.jpg', FALSE, NOW()),
    (367,145, 'src/main/webapp/assets/images/products/DenHoc/145-Denhoc-pri.jpg', TRUE,NOW()),

    (366,145,'src/main/webapp/assets/images/products/DenHoc/145-Denhoc-1.jpg',FALSE,NOW()),
    (367,145,'src/main/webapp/assets/images/products/DenHoc/145-Denhoc-pri.jpg',TRUE,NOW()),

    (368,146,'src/main/webapp/assets/images/products/DenHoc/146-Denhoc-1.jpg',FALSE,NOW()),
    (369,146,'src/main/webapp/assets/images/products/DenHoc/146-Denhoc-pri.jpg',TRUE,NOW()),

    (370,147,'src/main/webapp/assets/images/products/DenHoc/147-Denhoc-1.jpg',FALSE,NOW()),
    (371,147,'src/main/webapp/assets/images/products/DenHoc/147-Denhoc-pri.jpg',TRUE,NOW()),

    (372,148,'src/main/webapp/assets/images/products/DenHoc/148-Denhoc-1.jpg',FALSE,NOW()),
    (373,148,'src/main/webapp/assets/images/products/DenHoc/148-Denhoc-pri.jpg',TRUE,NOW()),

    (374,149,'src/main/webapp/assets/images/products/DenHoc/149-Denhoc-1.jpg',FALSE,NOW()),
    (375,149,'src/main/webapp/assets/images/products/DenHoc/149-Denhoc-pri.jpg',TRUE,NOW()),

    (376,150,'src/main/webapp/assets/images/products/DenHoc/150-Denhoc-1.jpg',FALSE,NOW()),
    (377,150,'src/main/webapp/assets/images/products/DenHoc/150-Denhoc-pri.jpg',TRUE,NOW()),

    (378,151,'src/main/webapp/assets/images/products/DenHoc/151-Denhoc-1.jpg',FALSE,NOW()),
    (379,151,'src/main/webapp/assets/images/products/DenHoc/151-Denhoc-pri.jpg',TRUE,NOW()),

    (380,152,'src/main/webapp/assets/images/products/DenHoc/152-Denhoc-1.jpg',FALSE,NOW()),
    (381,152,'src/main/webapp/assets/images/products/DenHoc/152-Denhoc-pri.jpg',TRUE,NOW()),

    (382,153,'src/main/webapp/assets/images/products/DenHoc/153-Denhoc-1.jpg',FALSE,NOW()),
    (383,153,'src/main/webapp/assets/images/products/DenHoc/153-Denhoc-pri.jpg',TRUE,NOW()),

    (384,154,'src/main/webapp/assets/images/products/DenHoc/154-Denhoc-1.jpg',FALSE,NOW()),
    (385,154,'src/main/webapp/assets/images/products/DenHoc/154-Denhoc-pri.jpg',TRUE,NOW()),

    (386,155,'src/main/webapp/assets/images/products/DenHoc/155-Denhoc-1.jpg',FALSE,NOW()),
    (387,155,'src/main/webapp/assets/images/products/DenHoc/155-Denhoc-pri.jpg',TRUE,NOW()),

    (388,156,'src/main/webapp/assets/images/products/DenHoc/156-Denhoc-1.jpg',FALSE,NOW()),
    (389,156,'src/main/webapp/assets/images/products/DenHoc/156-Denhoc-pri.jpg',TRUE,NOW()),

    (390,157,'src/main/webapp/assets/images/products/DenHoc/157-Denhoc-1.jpg',FALSE,NOW()),
    (391,157,'src/main/webapp/assets/images/products/DenHoc/157-Denhoc-pri.jpg',TRUE,NOW()),

    (392,158,'src/main/webapp/assets/images/products/DenHoc/158-Denhoc-1.jpg',FALSE,NOW()),
    (393,158,'src/main/webapp/assets/images/products/DenHoc/158-Denhoc-pri.jpg',TRUE,NOW()),

    (394,159,'src/main/webapp/assets/images/products/DenHoc/159-Denhoc-1.jpg',FALSE,NOW()),
    (395,159,'src/main/webapp/assets/images/products/DenHoc/159-Denhoc-pri.jpg',TRUE,NOW()),

    (396,160,'src/main/webapp/assets/images/products/DenHoc/160-Denhoc-1.jpg',FALSE,NOW()),
    (397,160,'src/main/webapp/assets/images/products/DenHoc/160-Denhoc-pri.jpg',TRUE,NOW()),

    (398,161,'src/main/webapp/assets/images/products/DenHoc/161-Denhoc-1.jpg',FALSE,NOW()),
    (399,161,'src/main/webapp/assets/images/products/DenHoc/161-Denhoc-pri.jpg',TRUE,NOW()),

    (400,162,'src/main/webapp/assets/images/products/DenHoc/162-Denhoc-1.jpg',FALSE,NOW()),
    (401,162,'src/main/webapp/assets/images/products/DenHoc/162-Denhoc-pri.jpg',TRUE,NOW()),

    (402,163,'src/main/webapp/assets/images/products/DenHoc/163-Denhoc-1.jpg',FALSE,NOW()),
    (403,163,'src/main/webapp/assets/images/products/DenHoc/163-Denhoc-pri.jpg',TRUE,NOW()),

    (404,164,'src/main/webapp/assets/images/products/DenHoc/164-Denhoc-1.jpg',FALSE,NOW()),
    (405,164,'src/main/webapp/assets/images/products/DenHoc/164-Denhoc-pri.jpg',TRUE,NOW()),

    (406,165,'src/main/webapp/assets/images/products/DenHoc/165-Denhoc-1.jpg',FALSE,NOW()),
    (407,165,'src/main/webapp/assets/images/products/DenHoc/165-Denhoc-pri.jpg',TRUE,NOW()),

    (408,166,'src/main/webapp/assets/images/products/DenHoc/166-Denhoc-1.jpg',FALSE,NOW()),
    (409,166,'src/main/webapp/assets/images/products/DenHoc/166-Denhoc-pri.jpg',TRUE,NOW()),

    (410,167,'src/main/webapp/assets/images/products/Giay/167-Giay-1.jpg',FALSE,NOW()),
    (411,167,'src/main/webapp/assets/images/products/Giay/167-Giay-pri.jpg',TRUE,NOW()),

    (412,167,'src/main/webapp/assets/images/products/Giay/168-Giay-1.jpg',FALSE,NOW()),
    (413,167,'src/main/webapp/assets/images/products/Giay/168-Giay-pri.jpg',TRUE,NOW()),

    (414,168,'src/main/webapp/assets/images/products/Giay/169-Giay-1.jpg',FALSE,NOW()),
    (415,168,'src/main/webapp/assets/images/products/Giay/169-Giay-pri.jpg',TRUE,NOW()),

    (416,169,'src/main/webapp/assets/images/products/Giay/170-Giay-1.jpg',FALSE,NOW()),
    (417,169,'src/main/webapp/assets/images/products/Giay/170-Giay-pri.jpg',TRUE,NOW()),

    (418,170,'src/main/webapp/assets/images/products/Giay/171-Giay-1.jpg',FALSE,NOW()),
    (419,170,'src/main/webapp/assets/images/products/Giay/171-Giay-pri.jpg',TRUE,NOW()),

    (420,171,'src/main/webapp/assets/images/products/Giay/172-Giay-1.jpg',FALSE,NOW()),
    (421,171,'src/main/webapp/assets/images/products/Giay/172-Giay-pri.jpg',TRUE,NOW()),

    (422,172,'src/main/webapp/assets/images/products/Giay/173-Giay-1.jpg',FALSE,NOW()),
    (423,172,'src/main/webapp/assets/images/products/Giay/173-Giay-pri.jpg',TRUE,NOW()),

    (424,173,'src/main/webapp/assets/images/products/Giay/174-Giay-1.jpg',FALSE,NOW()),
    (425,173,'src/main/webapp/assets/images/products/Giay/174-Giay-pri.jpg',TRUE,NOW()),

    (426,174,'src/main/webapp/assets/images/products/Giay/175-Giay-1.jpg',FALSE,NOW()),
    (427,174,'src/main/webapp/assets/images/products/Giay/175-Giay-pri.jpg',TRUE,NOW()),

    (428,175,'src/main/webapp/assets/images/products/Giay/176-Giay-1.jpg',FALSE,NOW()),
    (429,175,'src/main/webapp/assets/images/products/Giay/176-Giay-pri.jpg',TRUE,NOW()),

    (430,176,'src/main/webapp/assets/images/products/Giay/177-Giay-1.jpg',FALSE,NOW()),
    (431,176,'src/main/webapp/assets/images/products/Giay/177-Giay-pri.jpg',TRUE,NOW()),

    (432,177,'src/main/webapp/assets/images/products/Giay/178-Giay-1.jpg',FALSE,NOW()),
    (433,177,'src/main/webapp/assets/images/products/Giay/178-Giay-pri.jpg',TRUE,NOW()),

    (434,178,'src/main/webapp/assets/images/products/Giay/179-Giay-1.jpg',FALSE,NOW()),
    (435,178,'src/main/webapp/assets/images/products/Giay/179-Giay-pri.jpg',TRUE,NOW()),

    (436,179,'src/main/webapp/assets/images/products/Giay/180-Giay-1.jpg',FALSE,NOW()),
    (437,179,'src/main/webapp/assets/images/products/Giay/180-Giay-pri.jpg',TRUE,NOW()),

    (438,180,'src/main/webapp/assets/images/products/Giay/181-Giay-1.jpg',FALSE,NOW()),
    (439,180,'src/main/webapp/assets/images/products/Giay/181-Giay-pri.jpg',TRUE,NOW()),

    (440,181,'src/main/webapp/assets/images/products/Giay/182-Giay-1.jpg',FALSE,NOW()),
    (441,181,'src/main/webapp/assets/images/products/Giay/182-Giay-pri.jpg',TRUE,NOW()),

    (442,182,'src/main/webapp/assets/images/products/Giay/183-Giay-1.jpg',FALSE,NOW()),
    (443,182,'src/main/webapp/assets/images/products/Giay/183-Giay-pri.jpg',TRUE,NOW()),

    (444,183,'src/main/webapp/assets/images/products/Giay/184-Giay-1.jpg',FALSE,NOW()),
    (445,183,'src/main/webapp/assets/images/products/Giay/184-Giay-pri.jpg',TRUE,NOW()),

    (446,184,'src/main/webapp/assets/images/products/Giay/185-Giay-1.jpg',FALSE,NOW()),
    (447,184,'src/main/webapp/assets/images/products/Giay/185-Giay-pri.jpg',TRUE,NOW()),

    (448,185,'src/main/webapp/assets/images/products/Giay/186-Giay-1.jpg',FALSE,NOW()),
    (449,185,'src/main/webapp/assets/images/products/Giay/186-Giay-pri.jpg',TRUE,NOW()),

    (450,186,'src/main/webapp/assets/images/products/Giay/187-Giay-1.jpg',FALSE,NOW()),
    (451,186,'src/main/webapp/assets/images/products/Giay/187-Giay-pri.jpg',TRUE,NOW()),

    (452,187,'src/main/webapp/assets/images/products/Giay/188-Giay-1.jpg',FALSE,NOW()),
    (453,187,'src/main/webapp/assets/images/products/Giay/188-Giay-pri.jpg',TRUE,NOW()),

    (454,188,'src/main/webapp/assets/images/products/Giay/189-Giay-1.jpg',FALSE,NOW()),
    (455,188,'src/main/webapp/assets/images/products/Giay/189-Giay-pri.jpg',TRUE,NOW()),

    (456,189,'src/main/webapp/assets/images/products/Giay/190-Giay-1.jpg',FALSE,NOW()),
    (457,189,'src/main/webapp/assets/images/products/Giay/190-Giay-pri.jpg',TRUE,NOW())
;






-- Tin nhắn xác nhận
SELECT 'Dữ liệu sản phẩm đã tải thành công' AS message;