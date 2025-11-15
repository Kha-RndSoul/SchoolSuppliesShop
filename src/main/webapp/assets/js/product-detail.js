// Sample products data - Đồ Dùng Học Tập
    const allProducts = [
        { id: 1, name: 'Bút Bi Thiên Long TL-027', brand: 'Thiên Long', category: 'van-phong-pham', price: 3500, oldPrice: 5000, image: '', rating: 4.8,
            description: 'Bút bi Thiên Long TL-027 với ngòi bút mượt mà, mực đậm, không bị lem. Thiết kế nhỏ gọn, dễ cầm, phù hợp cho học sinh, sinh viên. Mực xanh đậm, bền màu, viết được trên nhiều loại giấy.',
            specs: { 'Loại': 'Bút bi', 'Màu mực': 'Xanh đậm', 'Ngòi': '0.7mm', 'Thương hiệu': 'Thiên Long', 'Xuất xứ': 'Việt Nam' } },

        { id: 2, name: 'Vở Hồng Hà 200 Trang', brand: 'Hồng Hà', category: 'sach-vo', price: 25000, oldPrice: 30000, image: 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=600&h=600&fit=crop', rating: 4.7,
            description: 'Vở Hồng Hà 200 trang với giấy trắng mịn, không bị thấm mực. Bìa cứng bảo vệ, kẻ ô ly rõ ràng, phù hợp cho học sinh tiểu học và trung học cơ sở.',
            specs: { 'Số trang': '200 trang', 'Kích thước': 'A5', 'Loại giấy': 'Giấy trắng 80gsm', 'Bìa': 'Bìa cứng', 'Xuất xứ': 'Việt Nam' } },

        { id: 3, name: 'Bút Chì Màu Faber-Castell 24 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 89000, oldPrice: 120000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=600&h=600&fit=crop', rating: 4.9,
            description: 'Bộ bút chì màu Faber-Castell 24 màu với màu sắc tươi sáng, bền màu. Ruột chì mềm, dễ tô, không dễ gãy. Hộp đựng tiện lợi, phù hợp cho học sinh và người yêu thích vẽ.',
            specs: { 'Số lượng': '24 màu', 'Độ cứng': 'HB', 'Xuất xứ': 'Đức', 'Bảo hành': '1 năm' } },

        { id: 4, name: 'Balo Jansport SuperBreak', brand: 'Jansport', category: 'balo-cap', price: 890000, oldPrice: 1200000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&h=600&fit=crop', rating: 4.6,
            description: 'Balo Jansport SuperBreak với thiết kế cổ điển, bền bỉ. Chất liệu vải bền, chống thấm nước nhẹ. Nhiều ngăn tiện lợi, đai vai đệm êm, phù hợp cho học sinh, sinh viên.',
            specs: { 'Dung tích': '31L', 'Chất liệu': 'Polyester', 'Kích thước': '43 x 30 x 13 cm', 'Màu sắc': 'Đa dạng', 'Bảo hành': 'Lifetime' } },

        { id: 5, name: 'Máy Tính Casio FX-580VN X', brand: 'Casio', category: 'may-tinh', price: 590000, oldPrice: 750000, image: '', rating: 4.8,
            description: 'Máy tính Casio FX-580VN X với 552 tính năng, màn hình LCD rõ nét. Hỗ trợ giải phương trình, tính toán phức tạp. Pin lâu dài, phù hợp cho học sinh THPT và sinh viên.',
            specs: { 'Tính năng': '552 chức năng', 'Màn hình': 'LCD 4 dòng', 'Pin': 'Pin năng lượng mặt trời + Pin', 'Kích thước': '161 x 80 x 11.1 mm', 'Xuất xứ': 'Nhật Bản' } },

        { id: 6, name: 'Đèn Bàn Học LED Điều Chỉnh', brand: 'Philips', category: 'den-hoc', price: 450000, oldPrice: 600000, image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=600&h=600&fit=crop', rating: 4.7,
            description: 'Đèn bàn học LED Philips với ánh sáng tự nhiên, không chói mắt. Điều chỉnh độ sáng và góc chiếu linh hoạt. Chống cận thị, bảo vệ mắt, phù hợp cho học tập lâu dài.',
            specs: { 'Công suất': '8W', 'Ánh sáng': 'LED 4000K', 'Điều chỉnh': '3 mức độ sáng', 'Kích thước': '40 x 20 x 50 cm', 'Bảo hành': '2 năm' } },

        { id: 7, name: 'Bút Máy Thiên Long TL-079', brand: 'Thiên Long', category: 'van-phong-pham', price: 45000, oldPrice: 60000, image: 'https://images.unsplash.com/photo-1561070791-2526d30994b5?w=600&h=600&fit=crop', rating: 4.6,
            description: 'Bút máy Thiên Long TL-079 với ngòi mực mượt mà, viết đẹp. Thiết kế sang trọng, phù hợp cho học sinh luyện chữ đẹp. Mực xanh, dễ thay thế.',
            specs: { 'Loại': 'Bút máy', 'Ngòi': '0.5mm', 'Màu mực': 'Xanh', 'Xuất xứ': 'Việt Nam' } },

        { id: 8, name: 'Vở Campus 120 Trang', brand: 'Campus', category: 'sach-vo', price: 18000, oldPrice: 25000, image: 'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=600&h=600&fit=crop', rating: 4.5,
            description: 'Vở Campus 120 trang với giấy chất lượng, không thấm mực. Bìa đẹp, kẻ ô ly chuẩn, phù hợp cho học sinh các cấp.',
            specs: { 'Số Trang': '120 trang', 'Kích thước': 'A5', 'Loại giấy': 'Giấy trắng 70gsm', 'Bìa': 'Bìa mềm' } },

        { id: 9, name: 'Bút Chì 2B Staedtler', brand: 'Staedtler', category: 'van-phong-pham', price: 12000, oldPrice: 15000, image:'' , rating: 4.7,
            description: 'Bút chì Staedtler 2B với ruột chì mềm, dễ tẩy. Phù hợp cho vẽ, tô đậm, làm bài thi. Chất lượng Đức, bền bỉ.',
            specs: { 'Độ cứng': '2B', 'Xuất xứ': 'Đức', 'Độ dài': '17.5cm' } },

        { id: 10, name: 'Thước Kẻ 30cm Nhựa', brand: 'Thiên Long', category: 'van-phong-pham', price: 8000, oldPrice: 12000, image: 'https://vanphongpham123.com/pic/products/thuoc-ke-_637740492089870680_HasThumb.jpg', rating: 4.4,
            description: 'Thước kẻ 30cm bằng nhựa trong suốt, vạch chia rõ ràng. Bền, không bị cong vênh, phù hợp cho học sinh.',
            specs: { 'Độ dài': '30cm', 'Chất liệu': 'Nhựa trong suốt', 'Vạch chia': 'mm và cm' } },

        { id: 11, name: 'Tẩy Gôm Pentel', brand: 'Pentel', category: 'van-phong-pham', price: 15000, oldPrice: 20000, image: 'https://cdn1.fahasa.com/media/catalog/product/4/0/4007817523865-1.jpg', rating: 4.6,
            description: 'Tẩy gôm Pentel với khả năng tẩy sạch, không để lại vết bẩn. Mềm, không làm rách giấy, phù hợp cho bút chì và bút chì màu.',
            specs: { 'Loại': 'Tẩy mềm', 'Xuất xứ': 'Nhật Bản', 'Kích thước': '5 x 2 x 1 cm' } },

        { id: 12, name: 'Bộ Màu Nước 12 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 125000, oldPrice: 180000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=600&h=600&fit=crop', rating: 4.8,
            description: 'Bộ màu nước Faber-Castell 12 màu với màu sắc tươi sáng, dễ pha trộn. Hộp đựng tiện lợi, kèm cọ vẽ, phù hợp cho học sinh và người yêu thích hội họa.',
            specs: { 'Số lượng': '12 màu', 'Kèm theo': 'Cọ vẽ', 'Xuất xứ': 'Đức' } },


        { id: 13, name: 'Giấy Vẽ A4 200 Tờ', brand: 'Double A', category: 'do-dung-ve', price: 45000, oldPrice: 60000, image: 'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=600&h=600&fit=crop', rating: 4.5,
            description: 'Giấy vẽ Double A A4 200 tờ với bề mặt mịn, dày dặn. Phù hợp cho vẽ bút chì, màu nước, màu sáp. Chất lượng cao, không thấm mực.',
            specs: { 'Kích thước': 'A4 (210 x 297mm)', 'Số tờ': '200 tờ', 'Định lượng': '80gsm', 'Xuất xứ': 'Thái Lan' } },

        { id: 14, name: 'Cặp Sách Học Sinh', brand: 'Hồng Hà', category: 'balo-cap', price: 350000, oldPrice: 450000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&h=600&fit=crop', rating: 4.6,
            description: 'Cặp sách Hồng Hà với thiết kế đẹp, nhiều ngăn tiện lợi. Chất liệu bền, đai vai đệm êm, phù hợp cho học sinh tiểu học.',
            specs: { 'Chất liệu': 'Polyester', 'Số ngăn': '3 ngăn chính', 'Kích thước': '38 x 28 x 15 cm', 'Màu sắc': 'Đa dạng' } },

        { id: 15, name: 'Balo Nike Heritage', brand: 'Nike', category: 'balo-cap', price: 1200000, oldPrice: 1500000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&h=600&fit=crop', rating: 4.7,
            description: 'Balo Nike Heritage với thiết kế thể thao, bền bỉ. Nhiều ngăn, túi đựng laptop, phù hợp cho sinh viên và người đi làm.', specs: { 'Dung tích': '25L', 'Chất liệu': 'Polyester', 'Kích thước': '45 x 30 x 15 cm', 'Bảo hành': '1 năm' } },

        { id: 18, name: 'Đèn Bàn Học Chống Cận', brand: 'Sunny', category: 'den-hoc', price: 320000, oldPrice: 450000, image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=600&h=600&fit=crop', rating: 4.5,
            description: 'Đèn bàn học Sunny chống cận với ánh sáng LED tự nhiên, không chói mắt. Điều chỉnh độ sáng và góc chiếu, bảo vệ mắt hiệu quả.',
            specs: { 'Công suất': '6W', 'Ánh sáng': 'LED 4000K', 'Điều chỉnh': '3 mức độ sáng', 'Kích thước': '35 x 18 x 45 cm' } },

        { id: 20, name: 'Bộ Bút Lông Màu 36 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 180000, oldPrice: 250000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=600&h=600&fit=crop', rating: 4.8,
            description: 'Bộ bút lông màu Faber-Castell 36 màu với màu sắc đa dạng, tươi sáng. Đầu bút mềm, dễ tô, không độc hại, phù hợp cho trẻ em và học sinh.',
            specs: { 'Số lượng': '36 màu', 'Đầu bút': 'Đầu tròn mềm', 'Xuất xứ': 'Đức', 'An toàn': 'Không độc hại' } },

        { id: 21, name: 'Balo đựng mèo', brand: 'Thiên Long', category: 'balo-cap', price: 330000, oldPrice: 380000, image: 'https://cunsieupham.com/wp-content/uploads/2023/06/22584100670_1106168684.jpg', rating: 4.8 },

        { id: 22, name: 'Máy Tính Casio FX-570VN Plus', brand: 'Casio', category: 'may-tinh', price: 490000, oldPrice: 650000, image: 'https://bizweb.dktcdn.net/100/379/648/products/may…plus-8-x-16-cm-l-1537255666-2.jpg?v=1587370028027',rating: 4.7},

        { id: 23, name: 'Máy Tính Casio DF-120 ', brand: 'Casio', category: 'may-tinh', price: 250000, oldPrice: 300000, image: 'https://bizweb.dktcdn.net/100/379/648/products/may…plus-8-x-16-cm-l-1537255666-2.jpg?v=1587370028027',rating: 4.7},

        { id: 24, name: 'Máy Tính Casio FX-880 ', brand: 'Casio', category: 'may-tinh', price: 800000, oldPrice: 900000, image: 'https://cdn1.fahasa.com/media/catalog/product/4/5/4549526613708.jpg',rating: 4.9},

        { id: 25, name: 'Máy Tính Casio FX-570ES Plus ', brand: 'Casio', category: 'may-tinh', price: 430000, oldPrice: 600000, image: 'https://www.bachdang.info/image/cache/catalog/revs…lder/6361ddff80f0dde2fd7141fb6f5772f9-500x524.jpg',rating: 4.8}
];
// Lấy id sản phẩm từ URL
function getProductId() {
    const params = new URLSearchParams(window.location.search);
    return parseInt(params.get('id')) || 1;
}
// hiển thị chi tiết sản phẩm
function renderProductDetail() {
    const productId = getProductId();
    const product = allProducts.find(p => p.id === productId);
    if (!product) {
        document.getElementById('productDetailContent').innerHTML = `
            <div style="text-align: center; padding: 3rem;">
                <h2>Sản phẩm không tồn tại</h2>
                <a href="products.html" class="btn-primary" style="margin-top: 1rem; display: inline-block;">Quay lại danh sách</a>
            </div>
        `;
        return;
    }
    // cập nhập tiêu đề trang
    document.title = `${product.name} - DPK Shop`;
    const specsHTML = Object.entries(product.specs || {}).map(([key, value]) => `
        <div class="spec-item">
            <span class="spec-label">${key}:</span>
            <span class="spec-value">${value}</span>
        </div>
    `).join('');
    document.getElementById('productDetailContent').innerHTML = `
        <div class="product-detail-layout">
            <div>
                <img src="${product.image}" alt="${product.name}" class="product-detail-image" onerror='this.src="https://via.placeholder.com/600" '>
            </div>
            <div class="product-detail-info">
                <h1>${product.name}</h1>
                <p class="product-detail-brand">Thương hiệu: ${product.brand}</p>
                <div class="product-detail-rating">
                    <span class="stars">${'★'.repeat(Math.floor(product.rating))}${'☆'.repeat(5 - Math.floor(product.rating))}</span>
                    <span>(${product.rating} / 5.0)</span>
                </div>
                <div class="product-detail-price">
                    ${product.price.toLocaleString('vi-VN')}đ
                    ${product.oldPrice ? `<span style="font-size: 1.2rem; color: var(--text-light); text-decoration: line-through; margin-left: 1rem;">${product.oldPrice.toLocaleString('vi-VN')}đ</span>` : ''}
                </div>
                <div style="background: #fef3c7; padding: 1rem; border-radius: 8px; margin: 1.5rem 0;">
                    <strong>🎁 Khuyến mãi:</strong> Giảm ${product.oldPrice ? Math.round((1 - product.price / product.oldPrice) * 100) : 0}% khi mua sản phẩm này!
                </div>
                <div class="product-description">
                    <h3 style="margin-bottom: 0.5rem;">Mô tả sản phẩm</h3>
                    <p>${product.description || 'Sản phẩm chất lượng cao, đảm bảo uy tín.'}</p>
                </div>
                <div class="product-actions">
                    <button class="btn-add-cart" onclick="addToCart(${product.id})">🛒 Thêm Vào Giỏ</button>
                    <button class="btn-buy-now" onclick="buyNow(${product.id})">Mua Ngay</button>
                </div>
            </div>
        </div>
        <div class="product-specs">
            <h2>Thông số kỹ thuật</h2>
            ${specsHTML}
        </div>
    `;
    // Hiển thị sản phẩm liên quan
    renderRelatedProducts(product);
}

// Hiển thị sản phẩm liên quan
function renderRelatedProducts(currentProduct) {
    const relatedProducts = allProducts
        .filter(p => p.id !== currentProduct.id && (p.category === currentProduct.category || p.brand === currentProduct.brand))
        .slice(0, 4);
    // Nếu không có sản phẩm liên quan, ẩn phần này
    if (relatedProducts.length === 0) {
        document.getElementById('relatedProducts').innerHTML = '';
        return;
    }
    document.getElementById('relatedProducts').innerHTML = relatedProducts.map(product => `
        <a href="product-detail.html?id=${product.id}" class="product-card">
            <img src="${product.image}" alt="${product.name}" class="product-image" onerror="this.src='https://via.placeholder.com/400'">
            <div class="product-info">
                <h3 class="product-name">${product.name}</h3>
                <p class="product-brand">${product.brand}</p>
                <div>
                    <span class="product-price">${product.price.toLocaleString('vi-VN')}đ</span>
                    ${product.oldPrice ? `<span class="product-price-old">${product.oldPrice.toLocaleString('vi-VN')}đ</span>` : ''}
                </div>
                <div class="product-rating">
                    <span class="stars">${'★'.repeat(Math.floor(product.rating))}${'☆'.repeat(5 - Math.floor(product.rating))}</span>
                    <span>(${product.rating})</span>
                </div>
            </div>
        </a>
    `).join('');
}
// Thêm vào giỏ hàng
function addToCart(productId) {
    const product = allProducts.find(p => p.id === productId);
    if (product) {
        // In a real app, this would add to cart storage/API
        alert(`Đã thêm "${product.name}" vào giỏ hàng!`);
        console.log('Added to cart:', product);
    }
}
// Mua ngay
function buyNow(productId) {
    const product = allProducts.find(p => p.id === productId);
    if (product) {
        // In a real app, this would redirect to checkout
        if (confirm(`Bạn có muốn mua "${product.name}" ngay bây giờ?`)) {
            alert('Chức năng thanh toán đang được phát triển!');
            console.log('Buy now:', product);
        }
    }
}
// Khởi tạo trang khi tải xong
document.addEventListener('DOMContentLoaded', function() {
    renderProductDetail();
});

// Chức năng tìm kiếm
    function handleSearch() {
    const searchTerm = document.getElementById('searchInput').value;
    if (searchTerm.trim()) {
    window.location.href = `products.html?search=${encodeURIComponent(searchTerm)}`;
}
}
    // Nhập Enter để tìm kiếm
    document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
    handleSearch();
}
});
