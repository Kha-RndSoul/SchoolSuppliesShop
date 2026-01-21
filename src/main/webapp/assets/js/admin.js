// Admin Dashboard JavaScript
console.log(' admin.js loaded');

document.addEventListener('DOMContentLoaded', function() {
    console.log(' DOMContentLoaded event fired');

    // ========== NAVIGATION ==========
    const navItems = document.querySelectorAll('.nav-item');
    const sections = document.querySelectorAll('.admin-section');

    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            navItems.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');
            sections.forEach(section => section.classList.remove('active'));
            const sectionId = this.getAttribute('data-section') + '-section';
            const targetSection = document.getElementById(sectionId);
            if (targetSection) {
                targetSection.classList.add('active');
            }
        });
    });

    // ========== PRODUCT FORM AJAX ==========
    const productForm = document.getElementById('productForm');
    if (productForm) {
        productForm.addEventListener('submit', function(e) {
            e.preventDefault();
            submitProductForm();
        });
        console.log(' Product form event listener attached');
    } else {
        console.warn(' Product form not found');
    }
});

/**
 * Toggle hiển thị/ẩn form thêm sản phẩm
 */
function toggleProductForm() {
    console.log('toggleProductForm() called');

    const formContainer = document.getElementById('productFormContainer');
    const toggleBtn = document.getElementById('toggleFormBtn');

    if (!formContainer) {
        console.error(' productFormContainer not found');
        return;
    }

    if (!toggleBtn) {
        console.error(' toggleFormBtn not found');
        return;
    }

    if (formContainer.style.display === 'none' || !formContainer.style.display) {
        // Hiển thị form
        formContainer.style.display = 'block';
        toggleBtn.innerHTML = ' Đóng Form';
        toggleBtn.classList.add('btn-close');
        console.log(' Form opened');
    } else {
        // Ẩn form
        formContainer.style.display = 'none';
        toggleBtn.innerHTML = ' Thêm Sản Phẩm';
        toggleBtn.classList.remove('btn-close');
        resetForm();
        console.log(' Form closed');
    }
}

/**
 * Reset form về trạng thái ban đầu
 */
function resetForm() {
    console.log('resetForm() called');
    const form = document.getElementById('productForm');
    if (form) {
        form.reset();
        const imagePreview = document.getElementById('imagePreview');
        if (imagePreview) {
            imagePreview.innerHTML = '';
        }
        console.log(' Form reset');
    }
}

/**
 * Preview hình ảnh trước khi upload
 */
function previewImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById('imagePreview');

    if (file) {
        if (file.size > 10 * 1024 * 1024) {
            showToast('Kích thước file không được vượt quá 10MB', 'error');
            event.target.value = '';
            preview.innerHTML = '';
            return;
        }

        const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
        if (!validTypes.includes(file.type)) {
            showToast('Chỉ chấp nhận file ảnh (JPG, PNG, GIF, WEBP)', 'error');
            event.target.value = '';
            preview.innerHTML = '';
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            preview.innerHTML = '<img src="' + e.target.result + '" alt="Preview" style="max-width: 200px; max-height: 200px; border-radius: 8px; margin-top: 10px;">';
        };
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
}

/**
 * Submit form thêm sản phẩm bằng AJAX
 */
function submitProductForm() {
    console.log('submitProductForm() called');

    const form = document.getElementById('productForm');
    const submitBtn = document.getElementById('submitBtn');
    const formData = new FormData(form);

    submitBtn.disabled = true;
    submitBtn.innerHTML = '⏳ Đang xử lý...';

    const contextPath = window.location.pathname.split('/')[1];
    const url = '/' + contextPath + '/admin/products';

    console.log('Sending request to:', url);

    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            console.log('Response status:', response.status);
            return response.json();
        })
        .then(data => {
            console.log('Response data:', data);
            if (data.success) {
                showToast(data.message, 'success');
                resetForm();
                setTimeout(function() {
                    toggleProductForm();
                    window.location.reload();
                }, 1500);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi thêm sản phẩm', 'error');
        })
        .finally(function() {
            submitBtn.disabled = false;
            submitBtn.innerHTML = '💾 Lưu Sản Phẩm';
        });
}

/**
 * Hiển thị toast notification
 */
function showToast(message, type) {
    type = type || 'info';
    const toast = document.getElementById('toast');
    if (!toast) {
        console.warn('Toast element not found');
        return;
    }

    let icon = '';
    switch(type) {
        case 'success':
            icon = '✅';
            break;
        case 'error':
            icon = '❌';
            break;
        case 'warning':
            icon = '⚠️';
            break;
        default:
            icon = 'ℹ️';
    }

    toast.innerHTML = icon + ' ' + message;
    toast.className = 'toast toast-' + type + ' show';

    setTimeout(function() {
        toast.className = 'toast';
    }, 4000);
}

/**
 * Active nút chuyển trang
 */
function changePage(event) {
    document.querySelectorAll('.pagination-btn').forEach(function(btn) {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');
}

/**
 * Xem chi tiết đơn hàng
 */
function viewOrderDetail(orderId) {
    console.log('View order:', orderId);
    showToast('Chức năng đang phát triển', 'info');
}

/**
 * Helper function để show section
 */
function showSection(sectionName) {
    const sections = document.querySelectorAll('.admin-section');
    sections.forEach(function(section) {
        section.classList.remove('active');
    });

    const targetSection = document.getElementById(sectionName + '-section');
    if (targetSection) {
        targetSection.classList.add('active');
    }

    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(function(item) {
        if (item.getAttribute('data-section') === sectionName) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
}

console.log(' All functions defined');