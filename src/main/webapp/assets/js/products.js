// Sample products data - Đồ Dùng Học Tập
const allProducts = [
    // ==================== VĂN PHÒNG PHẨM (ID 1-5) ====================
    {
        id: 1,
        name: 'Bút Bi Thiên Long TL-027',
        brand: 'Thiên Long',
        category: 'van-phong-pham',
        price: 3500,
        oldPrice: 5000,
        image: 'https://images.unsplash.com/photo-1586281380117-5a60ae2050cc?w=400&h=400&fit=crop',
        rating: 4.8,
        soldCount: 2500,
        description: 'Bút bi Thiên Long TL-027 với ngòi bút mượt mà, mực đậm, không bị lem. Thiết kế nhỏ gọn, dễ cầm, phù hợp cho học sinh, sinh viên. Mực xanh đậm, bền màu, viết được trên nhiều loại giấy.',
        specs: {
            'Loại': 'Bút bi',
            'Màu mực': 'Xanh đậm',
            'Ngòi': '0.7mm',
            'Thương hiệu': 'Thiên Long',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 2,
        name: 'Bút Chì Gỗ Thiên Long GP-02',
        brand: 'Thiên Long',
        category: 'van-phong-pham',
        price: 2500,
        oldPrice: 3500,
        image: 'https://images.unsplash.com/photo-1564951434112-64d74cc2a2d7?w=400&h=400&fit=crop',
        rating: 4.6,
        soldCount: 1800,
        description: 'Bút chì gỗ cao cấp với ruột chì đen đậm, không gãy dễ dàng. Vỏ gỗ tự nhiên thân thiện môi trường, dễ chuốt. Phù hợp cho học sinh tiểu học và trung học cơ sở.',
        specs: {
            'Loại': 'Bút chì gỗ',
            'Độ cứng': 'HB',
            'Chất liệu vỏ': 'Gỗ tự nhiên',
            'Độ dài': '18cm',
            'Thương hiệu': 'Thiên Long',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 3,
        name: 'Tẩy Thiên Long E-013',
        brand: 'Thiên Long',
        category: 'van-phong-pham',
        price: 1500,
        oldPrice: 2000,
        image: 'https://images.unsplash.com/photo-1611532736597-de2d4265fba3?w=400&h=400&fit=crop',
        rating: 4.7,
        soldCount: 3200,
        description: 'Tẩy cao su chất lượng cao, tẩy sạch không để vết, không làm rách giấy. Màu trắng, hình chữ nhật nhỏ gọn, dễ sử dụng và mang theo.',
        specs: {
            'Loại': 'Tẩy cao su',
            'Màu sắc': 'Trắng',
            'Kích thước': '4cm x 2cm x 1cm',
            'Trọng lượng': '15g',
            'Thương hiệu': 'Thiên Long',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 4,
        name: 'Thước Kẻ Nhựa 30cm',
        brand: 'Thiên Long',
        category: 'van-phong-pham',
        price: 4000,
        oldPrice: 6000,
        image: 'https://images.unsplash.com/photo-1562543732-9582c2efe8e5?w=400&h=400&fit=crop',
        rating: 4.5,
        soldCount: 1200,
        description: 'Thước kẻ nhựa trong suốt 30cm với vạch chia chính xác đến từng mm. Chất liệu nhựa dẻo dai, không gãy dễ. Phù hợp cho học sinh và văn phòng.',
        specs: {
            'Loại': 'Thước kẻ',
            'Chiều dài': '30cm',
            'Chất liệu': 'Nhựa trong suốt',
            'Độ chính xác': '±0.5mm',
            'Thương hiệu': 'Thiên Long',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 5,
        name: 'Bút Dạ Quang Stabilo Boss',
        brand: 'Stabilo',
        category: 'van-phong-pham',
        price: 15000,
        oldPrice: 20000,
        image: 'https://images.unsplash.com/photo-1587466125770-f8cf5b3c96e4?w=400&h=400&fit=crop',
        rating: 4.9,
        soldCount: 2800,
        description: 'Bút dạ quang Stabilo Boss màu vàng neon nổi bật, không thấm qua giấy. Đầu bút dẹt 2-5mm tiện lợi cho việc highlight. Mực không độc hại, an toàn cho sức khỏe.',
        specs: {
            'Loại': 'Bút dạ quang',
            'Màu sắc': 'Vàng neon',
            'Độ dày nét': '2-5mm',
            'Dung tích mực': '3ml',
            'Thương hiệu': 'Stabilo',
            'Xuất xứ': 'Đức'
        }
    },

    // ==================== SÁCH VỞ (ID 6-10) ====================
    {
        id: 6,
        name: 'Vở Kẻ Ngang Campus 200 Trang',
        brand: 'Campus',
        category: 'sach-vo',
        price: 25000,
        oldPrice: 30000,
        image: 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=400&fit=crop',
        rating: 4.8,
        soldCount: 1800,
        description: 'Vở 200 trang Campus với giấy dày 80gsm, không thấm mực, kẻ ngang chuẩn. Bìa cứng màu sắc đa dạng, bảo vệ giấy bên trong tốt. Lò xo chắc chắn, dễ gấp và viết.',
        specs: {
            'Loại': 'Vở kẻ ngang',
            'Số trang': '200 trang',
            'Loại giấy': 'Giấy dày 80gsm',
            'Kích thước': '17cm x 24cm',
            'Loại bìa': 'Bìa cứng màu',
            'Thương hiệu': 'Campus',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 7,
        name: 'Vở Ô Ly Hong Ha 96 Trang',
        brand: 'Hồng Hà',
        category: 'sach-vo',
        price: 12000,
        oldPrice: 15000,
        image: 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&h=400&fit=crop',
        rating: 4.6,
        soldCount: 2100,
        description: 'Vở ô ly 96 trang Hồng Hà dành cho môn Toán, giấy trắng mịn, ô li 5mm chuẩn. Bìa màu bóng đẹp mắt, giá thành phải chăng, phù hợp cho học sinh.',
        specs: {
            'Loại': 'Vở ô ly',
            'Số trang': '96 trang',
            'Loại giấy': 'Giấy trắng 70gsm',
            'Kích thước ô': '5mm x 5mm',
            'Kích thước': '17cm x 24cm',
            'Thương hiệu': 'Hồng Hà',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 8,
        name: 'Sổ Tay Bìa Da A5',
        brand: 'Thái Hà Books',
        category: 'sach-vo',
        price: 45000,
        oldPrice: 60000,
        image: 'https://images.unsplash.com/photo-1517842645767-c639042777db?w=400&h=400&fit=crop',
        rating: 4.7,
        soldCount: 950,
        description: 'Sổ tay bìa da A5 cao cấp với 120 trang giấy dày, có đường kẻ ngang nhẹ. Bìa da PU sang trọng, có dây đánh dấu trang và khóa nam châm. Phù hợp làm quà tặng hoặc ghi chú công việc.',
        specs: {
            'Loại': 'Sổ tay bìa da',
            'Số trang': '120 trang',
            'Loại giấy': 'Giấy kem 100gsm',
            'Kích thước': 'A5 (14.8cm x 21cm)',
            'Chất liệu bìa': 'Da PU cao cấp',
            'Thương hiệu': 'Thái Hà Books',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 9,
        name: 'Giấy Note Sticker 3M Post-it',
        brand: '3M',
        category: 'sach-vo',
        price: 35000,
        oldPrice: 45000,
        image: 'https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=400&h=400&fit=crop',
        rating: 4.9,
        soldCount: 1650,
        description: 'Giấy note sticker 3M Post-it 76x76mm màu vàng pastel, dán và bóc dễ dàng không để vết keo. Có thể viết, xóa và dán lại nhiều lần. Tiện dụng cho việc ghi chú nhanh.',
        specs: {
            'Loại': 'Giấy note sticker',
            'Kích thước': '76mm x 76mm',
            'Số tờ': '100 tờ/xấp',
            'Màu sắc': 'Vàng pastel',
            'Chất liệu': 'Giấy cao cấp có keo dính',
            'Thương hiệu': '3M',
            'Xuất xứ': 'USA'
        }
    },
    {
        id: 10,
        name: 'Bìa Lá Plastic Deli',
        brand: 'Deli',
        category: 'sach-vo',
        price: 8000,
        oldPrice: 12000,
        image: 'https://images.unsplash.com/photo-1593642532842-98d0fd5ebc1a?w=400&h=400&fit=crop',
        rating: 4.5,
        soldCount: 2400,
        description: 'Bìa lá plastic trong suốt Deli dùng để bảo quản tài liệu A4. Chất liệu nhựa PP dày dặn, chống nước, chống bụi. Có lỗ khóa 11 lỗ tiêu chuẩn.',
        specs: {
            'Loại': 'Bìa lá plastic',
            'Kích thước': 'A4 (21cm x 29.7cm)',
            'Chất liệu': 'Nhựa PP 0.08mm',
            'Số lỗ': '11 lỗ',
            'Màu sắc': 'Trong suốt',
            'Thương hiệu': 'Deli',
            'Xuất xứ': 'Trung Quốc'
        }
    },

    // ==================== ĐỒ DÙNG VẼ (ID 11-14) ====================
    {
        id: 11,
        name: 'Bút Chì Màu Faber-Castell 24 Màu',
        brand: 'Faber-Castell',
        category: 'do-dung-ve',
        price: 89000,
        oldPrice: 120000,
        image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=400&fit=crop',
        rating: 4.9,
        soldCount: 3200,
        description: 'Bộ 24 màu bút chì màu Faber-Castell với sắc màu tươi sáng, dễ tô, dễ blend. Ruột chì chắc chắn không gãy dễ. Vỏ gỗ tự nhiên thân thiện môi trường, hộp thiết kế sang trọng.',
        specs: {
            'Loại': 'Bút chì màu',
            'Số lượng': '24 màu',
            'Độ dày ruột': '3.3mm',
            'Chất liệu vỏ': 'Gỗ tự nhiên',
            'Đóng gói': 'Hộp thiết kế',
            'Thương hiệu': 'Faber-Castell',
            'Xuất xứ': 'Đức'
        }
    },
    {
        id: 12,
        name: 'Màu Nước Thiên Long 12 Màu',
        brand: 'Thiên Long',
        category: 'do-dung-ve',
        price: 25000,
        oldPrice: 35000,
        image: 'https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?w=400&h=400&fit=crop',
        rating: 4.6,
        soldCount: 1500,
        description: 'Bộ màu nước 12 màu Thiên Long trong hộp nhựa chắc chắn, kèm cọ vẽ. Màu sắc tươi sáng, dễ pha trộn, không độc hại. Phù hợp cho trẻ em và người mới học vẽ.',
        specs: {
            'Loại': 'Màu nước',
            'Số lượng': '12 màu',
            'Dung tích mỗi viên': '3ml',
            'Đi kèm': 'Cọ vẽ',
            'Chất liệu': 'Màu nước không độc',
            'Thương hiệu': 'Thiên Long',
            'Xuất xứ': 'Việt Nam'
        }
    },
    {
        id: 13,
        name: 'Bút Vẽ Kỹ Thuật Rotring 0.5mm',
        brand: 'Rotring',
        category: 'do-dung-ve',
        price: 125000,
        oldPrice: 180000,
        image: 'https://images.unsplash.com/photo-1607827448387-a67db1383b59?w=400&h=400&fit=crop',
        rating: 4.9,
        soldCount: 680,
        description: 'Bút vẽ kỹ thuật Rotring 0.5mm với ngòi kim loại chính xác, đường nét đều. Thân kim loại chắc chắn, có clip cài túi. Phù hợp cho kiến trúc sư, kỹ sư và họa sĩ chuyên nghiệp.',
        specs: {
            'Loại': 'Bút vẽ kỹ thuật',
            'Độ dày nét': '0.5mm',
            'Chất liệu thân': 'Kim loại',
            'Chất liệu ngòi': 'Kim loại cao cấp',
            'Màu mực': 'Đen',
            'Thương hiệu': 'Rotring',
            'Xuất xứ': 'Đức'
        }
    },
    {
        id: 14,
        name: 'Sổ Vẽ Sketch Maruman A4',
        brand: 'Maruman',
        category: 'do-dung-ve',
        price: 65000,
        oldPrice: 85000,
        image: 'https://images.unsplash.com/photo-1452860606245-08befc0ff44b?w=400&h=400&fit=crop',
        rating: 4.8,
        soldCount: 890,
        description: 'Sổ vẽ sketch Maruman A4 với 50 tờ giấy dày 100gsm, bề mặt nhám phù hợp cho bút chì, than, màu. Bìa cứng bảo vệ giấy, có khả năng tháo rời từng tờ dễ dàng.',
        specs: {
            'Loại': 'Sổ vẽ sketch',
            'Số trang': '50 tờ',
            'Loại giấy': 'Giấy vẽ 100gsm',
            'Kích thước': 'A4 (21cm x 29.7cm)',
            'Bề mặt': 'Nhám',
            'Thương hiệu': 'Maruman',
            'Xuất xứ': 'Nhật Bản'
        }
    },
    // ==================== BALO & CẶP (ID 15-17) ====================
    {
        id: 15,
        name: 'Balo Học Sinh Jansport SuperBreak',
        brand: 'Jansport',
        category: 'balo-cap',
        price: 890000,
        oldPrice: 1200000,
        image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop',
        rating: 4.7,
        soldCount: 956,
        description: 'Balo Jansport SuperBreak với thiết kế đơn giản, năng động. Chất liệu vải polyester chống nước, dây đeo đệm êm ái. Ngăn chính rộng rãi, ngăn phụ trước, phù hợp cho học sinh cấp 2, 3.',
        specs: {
            'Loại': 'Balo học sinh',
            'Kích thước': '42cm x 33cm x 21cm',
            'Dung tích': '25 lít',
            'Chất liệu': 'Polyester chống nước',
            'Số ngăn': '2 ngăn chính',
            'Thương hiệu': 'Jansport',
            'Xuất xứ': 'USA'
        }
    },
    {
        id: 16,
        name: 'Cặp Da Đựng Laptop 15.6 inch',
        brand: 'Samsonite',
        category: 'balo-cap',
        price: 650000,
        oldPrice: 900000,
        image: 'https://images.unsplash.com/photo-1491637639811-60e2756cc1c7?w=400&h=400&fit=crop',
        rating: 4.8,
        soldCount: 720,
        description: 'Cặp da Samsonite cao cấp đựng laptop 15.6 inch với ngăn đệm chống sốc. Thiết kế thanh lịch, chuyên nghiệp. Da PU bền đẹp, chống thấm nước, có quai xách và dây đeo vai.',
        specs: {
            'Loại': 'Cặp laptop',
            'Kích thước phù hợp': 'Laptop 15.6 inch',
            'Kích thước': '40cm x 30cm x 8cm',
            'Chất liệu': 'Da PU cao cấp',
            'Ngăn chống sốc': 'Có',
            'Thương hiệu': 'Samsonite',
            'Xuất xứ': 'Thái Lan'
        }
    },
    {
        id: 17,
        name: 'Túi Đeo Chéo Mini Unisex',
        brand: 'Anello',
        category: 'balo-cap',
        price: 350000,
        oldPrice: 480000,
        image: 'https://images.unsplash.com/photo-1590874103328-eac38a683ce7?w=400&h=400&fit=crop',
        rating: 4.6,
        soldCount: 1150,
        description: 'Túi đeo chéo mini Anello unisex phong cách Hàn Quốc, nhỏ gọn tiện lợi. Chất liệu vải canvas bền, nhiều ngăn nhỏ đựng điện thoại, tiền, thẻ. Dây đeo điều chỉnh được.',
        specs: {
            'Loại': 'Túi đeo chéo',
            'Kích thước': '18cm x 13cm x 6cm',
            'Chất liệu': 'Vải canvas',
            'Số ngăn': '3 ngăn',
            'Phong cách': 'Unisex',
            'Thương hiệu': 'Anello',
            'Xuất xứ': 'Nhật Bản'
        }
    },

    // ==================== MÁY TÍNH (ID 18-19) ====================
    {
        id: 18,
        name: 'Máy Tính Casio FX-580VN X',
        brand: 'Casio',
        category: 'may-tinh',
        price: 590000,
        oldPrice: 750000,
        image: 'https://images.unsplash.com/photo-1611224923853-80b023f02d71?w=400&h=400&fit=crop',
        rating: 4.9,
        soldCount: 1500,
        description: 'Máy tính khoa học Casio FX-580VN X với 552 chức năng, màn hình 2 dòng hiển thị rõ ràng. Được phép sử dụng trong các kỳ thi THPT Quốc gia. Pin bền, thiết kế chắc chắn.',
        specs: {
            'Loại': 'Máy tính khoa học',
            'Số chức năng': '552 chức năng',
            'Màn hình': 'LCD 2 dòng',
            'Nguồn điện': 'Pin AAA',
            'Kích thước': '16.6cm x 7.7cm x 1.4cm',
            'Thương hiệu': 'Casio',
            'Xuất xứ': 'Thái Lan'
        }
    },
    {
        id: 19,
        name: 'Máy Tính Bỏ Túi Casio MH-12',
        brand: 'Casio',
        category: 'may-tinh',
        price: 180000,
        oldPrice: 250000,
        image: 'https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=400&h=400&fit=crop',
        rating: 4.7,
        soldCount: 2100,
        description: 'Máy tính bỏ túi Casio MH-12 với màn hình lớn 12 chữ số, phím bấm êm. Có chức năng tính thuế, lãi suất, quy đổi tiền tệ. Thiết kế nhỏ gọn, pin năng lượng mặt trời kết hợp pin dự phòng.',
        specs: {
            'Loại': 'Máy tính bỏ túi',
            'Màn hình': '12 chữ số',
            'Nguồn điện': 'Pin mặt trời + pin dự phòng',
            'Kích thước': '15cm x 10cm x 2cm',
            'Trọng lượng': '120g',
            'Thương hiệu': 'Casio',
            'Xuất xứ': 'Trung Quốc'
        }
    },

    // ==================== ĐÈN HỌC (ID 20) ====================
    {
        id: 20,
        name: 'Đèn Bàn Học LED Philips EyeCare',
        brand: 'Philips',
        category: 'den-hoc',
        price: 450000,
        oldPrice: 600000,
        image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400&h=400&fit=crop',
        rating: 4.8,
        soldCount: 780,
        description: 'Đèn bàn học LED Philips EyeCare bảo vệ mắt với 3 chế độ ánh sáng (trắng, vàng, trung tính). Cần đèn điều chỉnh độ cao và góc chiếu linh hoạt. Tiết kiệm điện, tuổi thọ 25,000 giờ.',
        specs: {
            'Loại': 'Đèn bàn học LED',
            'Công suất': '12W',
            'Nhiệt độ màu': '3000K - 6500K',
            'Chế độ ánh sáng': '3 chế độ',
            'Tuổi thọ': '25,000 giờ',
            'Chiều cao điều chỉnh': '30-50cm',
            'Thương hiệu': 'Philips',
            'Xuất xứ': 'Trung Quốc'
        }
    }
];
let currentPage = 1;
const productsPerPage = 9;
let filteredProducts = [...allProducts];

function getUrlParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        search: params.get('search') || '',
        category: params.get('category') || '',
        page: parseInt(params.get('page')) || 1
    };
}

function applyFilters() {
    const urlParams = getUrlParams();
    const searchTerm = urlParams.search.toLowerCase() || document.getElementById('searchInput')?.value.toLowerCase() || '';

    const selectedCategories = [];
    document.querySelectorAll('#sidebar input[type="checkbox"][id^="cat-"]:checked').forEach(checkbox => {
        selectedCategories.push(checkbox.value);
    });

    const selectedBrands = [];
    document.querySelectorAll('#sidebar input[type="checkbox"][id^="brand-"]:checked').forEach(checkbox => {
        selectedBrands.push(checkbox.value);
    });

    filteredProducts = allProducts.filter(product => {
        const matchesSearch = !searchTerm ||
            product.name.toLowerCase().includes(searchTerm) ||
            product.brand.toLowerCase().includes(searchTerm);

        let matchesCategory = true;
        if (urlParams.category) {
            matchesCategory = product.category === urlParams.category;
        } else if (selectedCategories.length > 0) {
            matchesCategory = selectedCategories.includes(product.category);
        }

        const matchesBrand = selectedBrands.length === 0 ||
            selectedBrands.includes(product.brand);

        return matchesSearch && matchesCategory && matchesBrand;
    });

    currentPage = 1;
    renderProducts();
    renderPagination();
    updateProductsCount();
}

function resetFilters() {
    document.querySelectorAll('#sidebar input[type="checkbox"]').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.getElementById('searchInput').value = '';
    window.history.pushState({}, '', 'products.html');
    applyFilters();
}

function renderProducts() {
    const container = document.getElementById('productsGrid');
    const startIndex = (currentPage - 1) * productsPerPage;
    const endIndex = startIndex + productsPerPage;
    const productsToShow = filteredProducts.slice(startIndex, endIndex);

    if (productsToShow.length === 0) {
        container.innerHTML = '<p style="grid-column: 1/-1; text-align: center; padding: 3rem; color: var(--text-light);">Không tìm thấy sản phẩm nào.</p>';
        return;
    }
    container.innerHTML = productsToShow.map(product => `
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

function renderPagination() {
    const container = document.getElementById('pagination');
    const totalPages = Math.ceil(filteredProducts.length / productsPerPage);
    if (totalPages <= 1) {
        container.innerHTML = '';
        return;
    }
    let paginationHTML = '';
    paginationHTML += `<button onclick="goToPage(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''}>‹ Trước</button>`;
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            paginationHTML += `<button class="${i === currentPage ? 'active' : ''}" onclick="goToPage(${i})">${i}</button>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            paginationHTML += `<span>...</span>`;
        }
    }
    paginationHTML += `<button onclick="goToPage(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''}>Sau ›</button>`;
    container.innerHTML = paginationHTML;
}

function goToPage(page) {
    const totalPages = Math.ceil(filteredProducts.length / productsPerPage);
    if (page < 1 || page > totalPages) return;
    currentPage = page;
    renderProducts();
    renderPagination();
    window.scrollTo({ top: 0, behavior: 'smooth' });
    const url = new URL(window.location);
    url.searchParams.set('page', page);
    window.history.pushState({}, '', url);
}

function updateProductsCount() {
    const showingCount = Math.min(currentPage * productsPerPage, filteredProducts.length);
    const startCount = (currentPage - 1) * productsPerPage + 1;
    document.getElementById('showingCount').textContent =
        filteredProducts.length > 0 ? `${startCount}-${showingCount}` : '0';
    document.getElementById('totalCount').textContent = filteredProducts.length;
}

document.addEventListener('DOMContentLoaded', function() {
    const urlParams = getUrlParams();
    if (urlParams.search && document.getElementById('searchInput')) {
        document.getElementById('searchInput').value = urlParams.search;
    }
    if (urlParams.category) {
        const categoryCheckbox = document.getElementById(`cat-${urlParams.category}`);
        if (categoryCheckbox) {
            categoryCheckbox.checked = true;
        }
    }
    if (urlParams.page) {
        currentPage = urlParams.page;
    }
    applyFilters();
});

document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar) {
        sidebar.id = 'sidebar';
    }
});
