// Hero Slider
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.slider-dot');

function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.classList.toggle('active', i === index);
    });
    dots.forEach((dot, i) => {
        dot.classList.toggle('active', i === index);
    });
}
function changeSlide(direction) {
    currentSlide = (currentSlide + direction + slides.length) % slides.length;
    showSlide(currentSlide);
}
function goToSlide(index) {
    currentSlide = index;
    showSlide(currentSlide);
}

// Auto slide
setInterval(() => {
    changeSlide(1);
}, 5000);
// Search function
function handleSearch() {
    const searchTerm = document.getElementById('searchInput').value;
    if (searchTerm.trim()) {
        window.location.href = `products.html?search=${encodeURIComponent(searchTerm)}`;
    }
}

// Enter key search
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        handleSearch();
    }
});
const bestSellingProducts = [
    { id: 1, name: 'Bút Bi Thiên Long TL-027', brand: 'Thiên Long', price: 3500, oldPrice: 5000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.8 },
    { id: 2, name: 'Vở Hồng Hà 200 Trang', brand: 'Hồng Hà', price: 25000, oldPrice: 30000, image: 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=400&fit=crop', rating: 4.7 },
    { id: 3, name: 'Bút Chì Màu Faber-Castell 24 Màu', brand: 'Faber-Castell', price: 89000, oldPrice: 120000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=400&fit=crop', rating: 4.9 },
    { id: 4, name: 'Balo Jansport SuperBreak', brand: 'Jansport', price: 890000, oldPrice: 1200000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.6 },
    { id: 5, name: 'Máy Tính Casio FX-580VN X', brand: 'Casio', price: 590000, oldPrice: 750000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.8 },
    { id: 6, name: 'Đèn Bàn Học LED Điều Chỉnh', brand: 'Philips', price: 450000, oldPrice: 600000, image:'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop' , rating: 4.7 }
];

function renderProducts(products, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = products.map(product => `
                <a href="product-detail.html?id=${product.id}" class="product-card">
                    <img src="${product.image}" alt="${product.name}" class="product-image">
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
renderProducts(bestSellingProducts, 'bestSellingProducts');