USE school_supplies_db;

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
-- Chèn dữ liệu vào bảng categories
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
    (12, 'Panasonic', NOW()),
    (13, 'Double A', NOW()),
    (14, 'IK Plus', NOW()),
    (15, 'Jamlos', NOW()),
    (16, 'King Jim', NOW())
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
      -- Insert sản phẩm của Dũng
INSERT INTO products (id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, is_active) VALUES
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
-- Insert thêm sản phẩm của Kha
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
-- Insert sản phẩm của Phước

-- Insert sản phẩm của Dũng
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
-- Insert hình ảnh sản phẩm
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
-- Balo
    (249,81, 'src/main/webapp/assets/images/products/Balo/81-baloMT-1.jpg', FALSE, NOW()),
    (250,81, 'src/main/webapp/assets/images/products/Balo/81-baloMT-2.jpg', FALSE, NOW()),
    (251,81, 'src/main/webapp/assets/images/products/Balo/81-baloMT-pri.jpg', TRUE,NOW()),

    (252,82, 'src/main/webapp/assets/images/products/Balo/82-baloMT-1.jpg', FALSE, NOW()),
    (253,82, 'src/main/webapp/assets/images/products/Balo/82-baloMT-2.jpg', FALSE, NOW()),
    (254,82, 'src/main/webapp/assets/images/products/Balo/82-baloMT-pri.jpg', TRUE,NOW()),

    (255,83, 'src/main/webapp/assets/images/products/Balo/83-baloMT-1.jpg', FALSE, NOW()),
    (256,83, 'src/main/webapp/assets/images/products/Balo/83-baloMT-2.jpg', FALSE, NOW()),
    (257,83, 'src/main/webapp/assets/images/products/Balo/83-baloMT-pri.jpg', TRUE,NOW()),

    (258,84, 'src/main/webapp/assets/images/products/Balo/84-baloMT-1.jpg', FALSE, NOW()),
    (259,84, 'src/main/webapp/assets/images/products/Balo/84-baloMT-2.jpg', FALSE, NOW()),
    (260,84, 'src/main/webapp/assets/images/products/Balo/84-baloMT-pri.jpg', TRUE,NOW()),

    (261,85, 'src/main/webapp/assets/images/products/Balo/85-baloMT-1.jpg', FALSE, NOW()),
    (262,85, 'src/main/webapp/assets/images/products/Balo/85-baloMT-2.jpg', FALSE, NOW()),
    (263,85, 'src/main/webapp/assets/images/products/Balo/85-baloMT-pri.jpg', TRUE,NOW()),

    (264,86, 'src/main/webapp/assets/images/products/Balo/86-baloMT-1.jpg', FALSE, NOW()),
    (265,86, 'src/main/webapp/assets/images/products/Balo/86-baloMT-2.jpg', FALSE, NOW()),
    (266,86, 'src/main/webapp/assets/images/products/Balo/86-baloMT-pri.jpg', TRUE,NOW()),

    (267,87, 'src/main/webapp/assets/images/products/Balo/87-baloMT-1.jpg', FALSE, NOW()),
    (268,87, 'src/main/webapp/assets/images/products/Balo/87-baloMT-pri.jpg', TRUE,NOW()),

    (269,88, 'src/main/webapp/assets/images/products/Balo/88-baloMT-1.jpg', FALSE, NOW()),
    (270,88, 'src/main/webapp/assets/images/products/Balo/88-baloMT-pri.jpg', TRUE,NOW()),

    (271,89, 'src/main/webapp/assets/images/products/Balo/89-baloMT-1.jpg', FALSE, NOW()),
    (272,89, 'src/main/webapp/assets/images/products/Balo/89-baloMT-pri.jpg', TRUE,NOW()),

    (273,90, 'src/main/webapp/assets/images/products/Balo/90-baloMT-1.jpg', FALSE, NOW()),
    (274,90, 'src/main/webapp/assets/images/products/Balo/90-baloMT-pri.jpg', TRUE,NOW()),

    (275,91, 'src/main/webapp/assets/images/products/Balo/91-baloMT-1.jpg', FALSE, NOW()),
    (276,91, 'src/main/webapp/assets/images/products/Balo/91-baloMT-pri.jpg', TRUE,NOW()),

    (277,92, 'src/main/webapp/assets/images/products/Balo/92-baloMT-1.jpg', FALSE, NOW()),
    (278,92, 'src/main/webapp/assets/images/products/Balo/92-baloMT-pri.jpg', TRUE,NOW()),

    (279,93, 'src/main/webapp/assets/images/products/Balo/93-baloMT-1.jpg', FALSE, NOW()),
    (280,93, 'src/main/webapp/assets/images/products/Balo/93-baloMT-pri.jpg', TRUE,NOW()),

    (281,94, 'src/main/webapp/assets/images/products/Balo/94-baloMT-1.jpg', FALSE, NOW()),
    (282,94, 'src/main/webapp/assets/images/products/Balo/94-baloMT-pri.jpg', TRUE,NOW()),

    (283,95, 'src/main/webapp/assets/images/products/Balo/95-baloMT-1.jpg', FALSE, NOW()),
    (284,95, 'src/main/webapp/assets/images/products/Balo/95-baloMT-pri.jpg', TRUE,NOW()),

    (285,108, 'src/main/webapp/assets/images/products/Balo/108-baloJL-1.jpg', FALSE, NOW()),
    (286,108, 'src/main/webapp/assets/images/products/Balo/108-baloJL-1.jpg', TRUE,NOW()),

    (287,109, 'src/main/webapp/assets/images/products/Balo/109-baloJL-1.jpg', FALSE, NOW()),
    (288,109, 'src/main/webapp/assets/images/products/Balo/109-baloJL-pri.jpg', TRUE,NOW()),

    (289,110, 'src/main/webapp/assets/images/products/Balo/110-baloJL-1.jpg', FALSE, NOW()),
    (290,110, 'src/main/webapp/assets/images/products/Balo/110-baloJL-pri.jpg', TRUE,NOW()),

    (291,111, 'src/main/webapp/assets/images/products/Balo/111-baloJL-1.jpg', FALSE, NOW()),
    (292,111, 'src/main/webapp/assets/images/products/Balo/111-baloJL-pri.jpg', TRUE,NOW()),

    (293,112, 'src/main/webapp/assets/images/products/Balo/112-baloJL-1.jpg', FALSE, NOW()),
    (294,112, 'src/main/webapp/assets/images/products/Balo/112-baloJL-pri.jpg', TRUE,NOW()),

  -- May tinh

    (295,96, 'src/main/webapp/assets/images/products/MayTinh/96-mtCS-1.jpg', FALSE, NOW()),
    (296,96, 'src/main/webapp/assets/images/products/MayTinh/96-mtCS-pri.jpg', TRUE,NOW()),

    (297,97, 'src/main/webapp/assets/images/products/MayTinh/97-mtCS-1.jpg', FALSE, NOW()),
    (298,97, 'src/main/webapp/assets/images/products/MayTinh/97-mtCS-pri.jpg', TRUE,NOW()),

    (299,98, 'src/main/webapp/assets/images/products/MayTinh/98-mtCS-1.jpg', FALSE, NOW()),
    (300,98, 'src/main/webapp/assets/images/products/MayTinh/98-mtCS-pri.jpg', TRUE,NOW()),

    (301,99, 'src/main/webapp/assets/images/products/MayTinh/99-mtCS-1.jpg', FALSE, NOW()),
    (302,99, 'src/main/webapp/assets/images/products/MayTinh/99-mtCS-pri.jpg', TRUE,NOW()),

    (303,100, 'src/main/webapp/assets/images/products/MayTinh/100-mtFL-1.jpg', FALSE, NOW()),
    (304,100, 'src/main/webapp/assets/images/products/MayTinh/100-mtFL-pri.jpg', TRUE,NOW()),

    (305,101, 'src/main/webapp/assets/images/products/MayTinh/101-mtFL-1.jpg', FALSE, NOW()),
    (306,101, 'src/main/webapp/assets/images/products/MayTinh/101-mtFL-pri.jpg', TRUE,NOW()),

    (307,102, 'src/main/webapp/assets/images/products/MayTinh/102-mtFL-1.jpg', FALSE, NOW()),
    (308,102, 'src/main/webapp/assets/images/products/MayTinh/102-mtFL-pri.jpg', TRUE,NOW()),

    (309,103, 'src/main/webapp/assets/images/products/MayTinh/103-mtFL-1.jpg', FALSE, NOW()),
    (310,103, 'src/main/webapp/assets/images/products/MayTinh/103-mtFL-pri.jpg', TRUE,NOW()),

    (311,104, 'src/main/webapp/assets/images/products/MayTinh/104-mtFL-1.jpg', FALSE, NOW()),
    (312,104, 'src/main/webapp/assets/images/products/MayTinh/104-mtFL-pri.jpg', TRUE,NOW()),

    (313,105, 'src/main/webapp/assets/images/products/MayTinh/105-mtFL-1.jpg', FALSE, NOW()),
    (314,105, 'src/main/webapp/assets/images/products/MayTinh/105-mtFL-pri.jpg', TRUE,NOW()),

    (315,106, 'src/main/webapp/assets/images/products/MayTinh/106-mtFL-1.jpg', FALSE, NOW()),
    (316,106, 'src/main/webapp/assets/images/products/MayTinh/106-mtFL-pri.jpg', TRUE,NOW()),

    (317,107, 'src/main/webapp/assets/images/products/MayTinh/107-mtFL-1.jpg', FALSE, NOW()),
    (318,107, 'src/main/webapp/assets/images/products/MayTinh/107-mtFL-pri.jpg', TRUE,NOW()),

-- Thuoc
    (319,113, 'src/main/webapp/assets/images/products/Thuoc/113-thuoc-pri.jpg', TRUE, NOW()),
    (320,114, 'src/main/webapp/assets/images/products/Thuoc/114-thuoc-pri.jpg', TRUE, NOW()),
    (321,115, 'src/main/webapp/assets/images/products/Thuoc/115-thuoc-pri.jpg', TRUE, NOW()),
    (322,116, 'src/main/webapp/assets/images/products/Thuoc/116-thuoc-pri.jpg', TRUE, NOW()),
    (323,117, 'src/main/webapp/assets/images/products/Thuoc/117-thuoc-pri.jpg', TRUE, NOW()),
    (324,118, 'src/main/webapp/assets/images/products/Thuoc/118-thuoc-pri.jpg', TRUE, NOW()),

    (325,119, 'src/main/webapp/assets/images/products/Thuoc/119-thuoc-1.jpg', FALSE, NOW()),
    (326,119, 'src/main/webapp/assets/images/products/Thuoc/119-thuoc-pri.jpg', TRUE, NOW()),

    (327,120, 'src/main/webapp/assets/images/products/Thuoc/120-thuoc-pri.jpg', TRUE, NOW()),

    (328,121, 'src/main/webapp/assets/images/products/Thuoc/121-thuoc-pri.jpg', TRUE, NOW()),

    (329,122, 'src/main/webapp/assets/images/products/Thuoc/122-thuoc-pri.jpg', TRUE, NOW()),

    (330,123, 'src/main/webapp/assets/images/products/Thuoc/123-thuoc-pri.jpg', TRUE, NOW()),

    (331,124, 'src/main/webapp/assets/images/products/Thuoc/124-thuoc-pri.jpg', TRUE, NOW()),

    (332,125, 'src/main/webapp/assets/images/products/Thuoc/125-thuoc-pri.jpg', TRUE, NOW()),

    (333,126, 'src/main/webapp/assets/images/products/Thuoc/126-thuocTL-1.jpg', FALSE, NOW()),
    (334,126, 'src/main/webapp/assets/images/products/Thuoc/126-thuocTL-pri.jpg', TRUE, NOW()),

    (335,127, 'src/main/webapp/assets/images/products/Thuoc/127-thuocTL-1.jpg', FALSE, NOW()),
    (336,127, 'src/main/webapp/assets/images/products/Thuoc/127-thuocTL-pri.jpg', TRUE, NOW()),

    (337,128, 'src/main/webapp/assets/images/products/Thuoc/128-thuocTL-pri.jpg', TRUE, NOW()),

    (338,129, 'src/main/webapp/assets/images/products/Thuoc/128-thuocTL-pri.jpg', TRUE, NOW()),

    (339,130, 'src/main/webapp/assets/images/products/Thuoc/130-thuocTL-1.jpg', FALSE, NOW()),
    (340,130, 'src/main/webapp/assets/images/products/Thuoc/130-thuocTL-pri.jpg', TRUE, NOW()),

    (341,131, 'src/main/webapp/assets/images/products/Thuoc/131-thuocTL-1.jpg', FALSE, NOW()),
    (342,131, 'src/main/webapp/assets/images/products/Thuoc/131-thuocTL-pri.jpg', TRUE, NOW()),

    (343,132, 'src/main/webapp/assets/images/products/Tay/132-tay-pri.jpg', TRUE, NOW()),

    (344,133, 'src/main/webapp/assets/images/products/Tay/133-tay-1.jpg', FALSE, NOW()),
    (345,133, 'src/main/webapp/assets/images/products/Tay/133-tay-pri.jpg', TRUE, NOW()),

    (346,134, 'src/main/webapp/assets/images/products/Tay/134-tay-1.jpg', FALSE, NOW()),
    (347,134, 'src/main/webapp/assets/images/products/Tay/134-tay-pri.jpg', TRUE, NOW()),

    (348,135, 'src/main/webapp/assets/images/products/Tay/135-tay-1.jpg', FALSE, NOW()),
    (349,135, 'src/main/webapp/assets/images/products/Tay/135-tay-pri.jpg', TRUE, NOW()),

    (350,136, 'src/main/webapp/assets/images/products/Tay/136-tay-pri.jpg', TRUE, NOW()),

    (351,137, 'src/main/webapp/assets/images/products/Tay/137-tay-1.jpg', FALSE, NOW()),
    (352,137, 'src/main/webapp/assets/images/products/Tay/137-tay-pri.jpg', TRUE, NOW()),

    (353,138, 'src/main/webapp/assets/images/products/Tay/138-tay-1.jpg', FALSE, NOW()),
    (354,138, 'src/main/webapp/assets/images/products/Tay/138-tay-pri.jpg', TRUE, NOW()),

    (355,139, 'src/main/webapp/assets/images/products/Tay/139-tay-1.jpg', FALSE, NOW()),
    (356,139, 'src/main/webapp/assets/images/products/Tay/139-tay-pri.jpg', TRUE, NOW()),

    (358,140, 'src/main/webapp/assets/images/products/Tay/140-tay-1.jpg', FALSE, NOW()),
    (359,140, 'src/main/webapp/assets/images/products/Tay/140-tay-pri.jpg', TRUE, NOW()),

    (360,141, 'src/main/webapp/assets/images/products/Tay/141-tay-1.jpg', FALSE, NOW()),
    (361,141, 'src/main/webapp/assets/images/products/Tay/141-tay-pri.jpg', TRUE, NOW()),

    (362,142, 'src/main/webapp/assets/images/products/Tay/142-tay-pri.jpg', TRUE, NOW()),

    (363,143, 'src/main/webapp/assets/images/products/Tay/143-tay-1.jpg', FALSE, NOW()),
    (364,143, 'src/main/webapp/assets/images/products/Tay/143-tay-pri.jpg', TRUE, NOW()),

    (365,144, 'src/main/webapp/assets/images/products/Tay/144-tay-pri.jpg', TRUE, NOW());














-- Tin nhắn xác nhận
SELECT 'Dữ liệu sản phẩm đã tải thành công' AS message;