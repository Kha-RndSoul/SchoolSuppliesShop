
let currentImageIndex = 0;
let productImages = [];
let isSubmitting = false;
let justValidated = false;
// Biến để ngăn submit nhiều lần và ngăn submit ngay sau khi validate sửa giá trị
function initGallery() {
    const mainContainer = document.querySelector('.main-image-container');
    if (!mainContainer) return;
// Lấy tất cả ảnh thumbnail
    const thumbnails = document.querySelectorAll('.thumbnail');
    productImages = Array.from(thumbnails).map(thumb => thumb.src);
// Nếu không có thumbnails, lấy từ ảnh chính
    if (productImages.length === 0) {
        const mainImage = document.getElementById('mainImage');
        if (mainImage) productImages = [mainImage.src];
    }
// Tạo điều hướng nếu có nhiều hơn 1 ảnh
    if (productImages.length > 1) {
        const navHTML = `
            <div class="gallery-nav">
                <button class="gallery-arrow arrow-prev" onclick="prevImage()">‹</button>
                <button class="gallery-arrow arrow-next" onclick="nextImage()">›</button>
            </div>
            <div class="image-counter">
                <span class="current-index">1</span> / ${productImages.length}
            </div>
        `;
        mainContainer.insertAdjacentHTML('beforeend', navHTML);
        updateArrowState();
    }
}
// Chuyển ảnh chính
function changeImage(index) {
    if (index < 0 || index >= productImages.length) return;
// Hiệu ứng chuyển ảnh
    currentImageIndex = index;
    const mainImage = document.getElementById('mainImage');
    if (mainImage) {
        mainImage.style.opacity = '0';
        setTimeout(() => {
            mainImage.src = productImages[index];
            mainImage.style.opacity = '1';
        }, 150);
    }
// Cập nhật bộ đếm ảnh
    const counter = document.querySelector('.current-index');
    if (counter) counter.textContent = index + 1;

    updateArrowState();
}
// Chuyển sang ảnh tiếp theo
function nextImage() {
    if (currentImageIndex < productImages.length - 1) {
        changeImage(currentImageIndex + 1);
    }
}
// Chuyển sang ảnh trước đó
function prevImage() {
    if (currentImageIndex > 0) {
        changeImage(currentImageIndex - 1);
    }
}
// Cập nhật trạng thái nút điều hướng
function updateArrowState() {
    const prevBtn = document.querySelector('.arrow-prev');
    const nextBtn = document.querySelector('.arrow-next');

    if (prevBtn) prevBtn.classList.toggle('disabled', currentImageIndex === 0);
    if (nextBtn) nextBtn.classList.toggle('disabled', currentImageIndex === productImages.length - 1);
}
// Thay đổi ảnh chính khi click thumbnail
function changeMainImage(imageUrl, thumbnail) {
    const index = productImages.indexOf(imageUrl);
    if (index !== -1) changeImage(index);
}
// Tăng số lượng
function increaseQuantity(maxStock) {
    const input = document.getElementById('quantity');
    if (!input) return;

    let val = parseInt(input.value) || 1;
    const max = maxStock > 0 ? maxStock : 999;

    if (val < max) {
        input.value = val + 1;
    } else {
        showNotification(`Số lượng tối đa là ${max}`, 'error');
    }
}
// Giảm số lượng
function decreaseQuantity() {
    const input = document.getElementById('quantity');
    if (!input) return;

    let val = parseInt(input.value) || 1;
    if (val > 1) input.value = val - 1;
}
// Chuyển tab
function switchTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
    document.querySelectorAll('.tab-header').forEach(h => h.classList.remove('active'));

    const tab = document.getElementById(tabId);
    if (tab) tab.classList.add('active');

    if (event.target) event.target.classList.add('active');
}
// Mua ngay
function buyNow() {
    const form = document.querySelector('.add-to-cart-form');
    if (!form) return;

    const actionUrl = form.getAttribute('action');
    const formData = new FormData();
    formData.append('action', 'add');
    formData.append('productId', form.querySelector('input[name="productId"]').value);
    formData.append('quantity', form.querySelector('input[name="quantity"]').value);
// Gửi yêu cầu mua ngay
    fetch(actionUrl, { method: 'POST', body: formData })
        .then(res => res.ok ? window.location.href = actionUrl.replace('?', '') : showNotification('Có lỗi xảy ra', 'error'))
        .catch(() => showNotification('Có lỗi xảy ra', 'error'));
}
// Hệ thống thông báo
function showNotification(message, type = 'success') {
    let notification = document.getElementById('cart-notification');
    if (!notification) {

        notification = document.createElement('div');
        notification.className = 'cart-notification';
        notification.id = 'cart-notification';

        document.body.appendChild(notification);
    }
    // TẠO SPAN RIÊNG cho message thay vì dùng textContent trực tiếp
    notification.innerHTML = `<span class="notification-message">${message}</span>`;
    notification.className = `cart-notification ${type} show`;

    setTimeout(() => notification.classList.remove('show'), 2500);
}
// Cập nhật số lượng giỏ hàng trên giao diện
function updateCartCount(addedQty) {
    const cartBadge = document.querySelector('.cart-badge');

    if (cartBadge) {
        const currentCount = parseInt(cartBadge.textContent) || 0;
        cartBadge.textContent = currentCount + addedQty;

        cartBadge.classList.add('updating');
        setTimeout(() => cartBadge.classList.remove('updating'), 500);
    } else {
        const cartButton = document.querySelector('.cart-button');
        if (cartButton) {
            const badge = document.createElement('span');
            badge.className = 'cart-badge';
            badge.textContent = addedQty;
            cartButton.appendChild(badge);
        }
    }
}
// Validate số lượng
function validateQuantity(input) {
    let val = parseInt(input.value);
    const min = parseInt(input.getAttribute('min')) || 1;
    const max = parseInt(input.getAttribute('max')) || 999;
// Kiểm tra giá trị nhập
    if (isNaN(val) || input.value === '' || val < min) {
        input.value = min;
        showNotification('Số lượng không hợp lệ! Đã đặt về mặc định.', 'error');
        justValidated = true; // Đánh dấu vừa mới validate
        setTimeout(() => justValidated = false, 100);
        return false;
    } else if (val > max) {
        input.value = max;
        showNotification(`Số lượng tối đa là ${max}`, 'error');
        justValidated = true; // Đánh dấu vừa mới validate
        setTimeout(() => justValidated = false, 100);
        return false;
    }
    return true;
}
// Khởi tạo khi tải trang
document.addEventListener('DOMContentLoaded', function() {
    initGallery();
// Xử lý submit form thêm vào giỏ hàng
    const form = document.querySelector('.add-to-cart-form');
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            // Ngăn submit nhiều lần
            if (isSubmitting) return false;
            // Ngăn submit ngay sau khi validate sửa giá trị
            if (justValidated) {
                justValidated = false;
                return false;
            }
            const input = document.getElementById('quantity');
            let val = parseInt(input.value);
            const min = parseInt(input.getAttribute('min')) || 1;
            const max = parseInt(input.getAttribute('max')) || 999;
            // Validate lần cuối trước submit
            if (isNaN(val) || input.value === '' || val < min) {
                input.value = min;
                showNotification('Số lượng không hợp lệ! Đã đặt về mặc định.', 'error');
                return false;
            }
// Validate số lượng không vượt quá tồn kho
            if (val > max) {
                input.value = max;
                showNotification(`Số lượng tối đa là ${max}. Vui lòng thêm lại!`, 'error');
                return false;
            }
            const btn = form.querySelector('button[type="submit"]');
// Thực hiện thêm vào giỏ hàng
            if (btn) {
                isSubmitting = true;
                const txt = btn.textContent;
                btn.textContent = 'Đang thêm...';
                btn.disabled = true;

                const formData = new FormData(form);
                const actionUrl = form.getAttribute('action');

                fetch(actionUrl, {
                    method: 'POST',
                    body: formData,
                    headers: { 'X-Requested-With': 'XMLHttpRequest' }
                })
                    .then(res => {
                        if (res.ok) {
                            showNotification('✓ Đã thêm vào giỏ hàng thành công!', 'success');
                            updateCartCount(val);
                            input.value = 1;
                        } else {
                            showNotification('Có lỗi xảy ra. Vui lòng thử lại.', 'error');
                        }
                        btn.textContent = txt;
                        btn.disabled = false;
                        isSubmitting = false;
                    })
                    .catch(() => {
                        showNotification('Có lỗi xảy ra. Vui lòng thử lại.', 'error');
                        btn.textContent = txt;
                        btn.disabled = false;
                        isSubmitting = false;
                    });
            }
        });
    }
// Xử lý input số lượng(chỉ cho phép nhập số,validate khi rời khỏi input, nhấn enter hoặc thay đổi giá trị)
    const qtyInput = document.getElementById('quantity');
    if (qtyInput) {
        qtyInput.removeAttribute('readonly');

        qtyInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
        });

        qtyInput.addEventListener('blur', function() {
            validateQuantity(this);
        });

        qtyInput.addEventListener('change', function() {
            validateQuantity(this);
        });

        qtyInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                e.stopPropagation();
                this.blur();
            }
        });

        qtyInput.addEventListener('paste', function(e) {
            e.preventDefault();
            const pastedText = (e.clipboardData || window.clipboardData).getData('text');
            const numbersOnly = pastedText.replace(/[^0-9]/g, '');
            if (numbersOnly) {
                this.value = numbersOnly;
                this.dispatchEvent(new Event('change'));
            }
        });
    }
// Xử lý phím mũi tên trái/phải để chuyển ảnh
    document.addEventListener('keydown', function(e) {
        if (productImages.length > 1) {
            if (e.key === 'ArrowLeft') prevImage();
            if (e.key === 'ArrowRight') nextImage();
        }
    });
});
//Log
console.log(' Product Detail JS loaded');