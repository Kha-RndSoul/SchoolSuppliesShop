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
