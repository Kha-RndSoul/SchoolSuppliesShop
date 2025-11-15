// Admin Dashboard JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Navigation
    const navItems = document.querySelectorAll('.nav-item');
    const sections = document.querySelectorAll('.admin-section');

    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();

            // Remove active class from all nav items
            navItems.forEach(nav => nav.classList.remove('active'));

            // Add active class to clicked item
            this.classList.add('active');

            // Hide all sections
            sections.forEach(section => section.classList.remove('active'));

            // Show selected section
            const sectionId = this.getAttribute('data-section') + '-section';
            const targetSection = document.getElementById(sectionId);
            if (targetSection) {
                targetSection.classList.add('active');
            }
        });
    });
});