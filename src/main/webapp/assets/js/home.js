
    (function () {
    const slider = document.querySelector('.hero-slider');
    if (!slider) return;
    const slides = Array.from(slider.querySelectorAll('.hero-slide'));
    let current = slides.findIndex(s => s.getAttribute('aria-hidden') === 'false');
    if (current < 0) current = 0;

    function show(index) {
    index = (index + slides.length) % slides.length;
    slides.forEach((s, i) => {
    const visible = i === index;
    s.style.display = visible ? '' : 'none';
    s.setAttribute('aria-hidden', visible ? 'false' : 'true');
});
    current = index;
}

    document.getElementById('slider-prev').addEventListener('click', () => show(current - 1));
    document.getElementById('slider-next').addEventListener('click', () => show(current + 1));

    // autoplay
    let timer = null;
    function startAutoplay() {
    if (slider.dataset.autoplay === 'false') return;
    const interval = parseInt(slider.dataset.interval, 10) || 4000;
    timer = setInterval(() => show(current + 1), interval);
}
    function stopAutoplay() {
    if (timer) { clearInterval(timer); timer = null; }
}

    slider.addEventListener('mouseenter', stopAutoplay);
    slider.addEventListener('mouseleave', startAutoplay);

    // initialize
    show(current);
    startAutoplay();
})();
