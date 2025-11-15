// Hero Slider
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.slider-dot');
// Hiển thị slide hiện tại
function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.classList.toggle('active', i === index);
    });
    dots.forEach((dot, i) => {
        dot.classList.toggle('active', i === index);
    });
}
// Chuyển slide
function changeSlide(direction) {
    currentSlide = (currentSlide + direction + slides.length) % slides.length;
    showSlide(currentSlide);
}
// Chuyển đến slide cụ thể
function goToSlide(index) {
    currentSlide = index;
    showSlide(currentSlide);
}

// Auto slide
setInterval(() => {
    changeSlide(1);
}, 5000);
function getSoldCountClass(count) {
    if (count >= 3000) return 'hot';
    if (count >= 1000) return 'warm';
    return 'cool';
}
