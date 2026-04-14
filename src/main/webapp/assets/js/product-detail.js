let currentImageIndex = 0;
let productImages = [];
let isSubmitting = false;
let justValidated = false;


function initGallery() {
    const mainContainer = document.querySelector('.main-image-container');
    if (!mainContainer) return;

    const thumbnails = document.querySelectorAll('.thumbnail');
    productImages = Array.from(thumbnails).map(thumb => thumb.src);

    if (productImages.length === 0) {
        const mainImage = document.getElementById('mainImage');
        if (mainImage) productImages = [mainImage.src];
    }

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

function changeImage(index) {
    if (index < 0 || index >= productImages.length) return;

    currentImageIndex = index;
    const mainImage = document.getElementById('mainImage');
    if (mainImage) {
        mainImage.style.opacity = '0';
        setTimeout(() => {
            mainImage.src = productImages[index];
            mainImage.style.opacity = '1';
        }, 150);
    }

    const counter = document.querySelector('.current-index');
    if (counter) counter.textContent = index + 1;

    updateArrowState();
}

function nextImage() {
    if (currentImageIndex < productImages.length - 1) {
        changeImage(currentImageIndex + 1);
    }
}

function prevImage() {
    if (currentImageIndex > 0) {
        changeImage(currentImageIndex - 1);
    }
}

function updateArrowState() {
    const prevBtn = document.querySelector('.arrow-prev');
    const nextBtn = document.querySelector('.arrow-next');
    if (prevBtn) prevBtn.classList.toggle('disabled', currentImageIndex === 0);
    if (nextBtn) nextBtn.classList.toggle('disabled', currentImageIndex === productImages.length - 1);
}

function changeMainImage(imageUrl) {
    const index = productImages.indexOf(imageUrl);
    if (index !== -1) changeImage(index);
}


function increaseQuantity(maxStock) {
    const input = document.getElementById('quantity');
    if (!input) return;
    const val = parseInt(input.value) || 1;
    const max = maxStock > 0 ? maxStock : 999;
    if (val < max) {
        input.value = val + 1;
    } else {
        showNotification(`Số lượng tối đa là ${max}`, 'error');
    }
}

function decreaseQuantity() {
    const input = document.getElementById('quantity');
    if (!input) return;
    const val = parseInt(input.value) || 1;
    if (val > 1) input.value = val - 1;
}

function clampQuantity(val, min, max, input) {
    if (isNaN(val) || input.value === '' || val < min) {
        input.value = min;
        showNotification('Số lượng không hợp lệ! Đã đặt về mặc định.', 'error');
        return false;
    }
    if (val > max) {
        input.value = max;
        showNotification(`Số lượng tối đa là ${max}`, 'error');
        return false;
    }
    return true;
}

function validateQuantity(input) {
    const val = parseInt(input.value);
    const min = parseInt(input.getAttribute('min')) || 1;
    const max = parseInt(input.getAttribute('max')) || 999;
    const valid = clampQuantity(val, min, max, input);
    if (!valid) {
        justValidated = true;
        setTimeout(() => { justValidated = false; }, 100);
    }
    return valid;
}


// Fix: Truyền clickedHeader thay vì dùng global event object
function switchTab(tabId, clickedHeader) {
    document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
    document.querySelectorAll('.tab-header').forEach(h => h.classList.remove('active'));
    const tab = document.getElementById(tabId);
    if (tab) tab.classList.add('active');
    if (clickedHeader) clickedHeader.classList.add('active');
}
// Usage in HTML: onclick="switchTab('tab1', this)"


function buyNow() {
    const form = document.querySelector('.add-to-cart-form');
    if (!form) return;

    const actionUrl = form.getAttribute('action');
    const params = buildCartParams(form);

    fetch(actionUrl, buildFetchOptions(params))
        .then(res => parseResponse(res))
        .then(data => {
            if (typeof data === 'object' && data.success) {
                window.location.href = actionUrl.replace('/cart', '/cart/checkout');
            } else {
                window.location.href = actionUrl;
            }
        })
        .catch(() => showNotification('Có lỗi xảy ra', 'error'));
}


function showNotification(message, type = 'success') {
    let notification = document.getElementById('cart-notification');
    if (!notification) {
        notification = document.createElement('div');
        notification.className = 'cart-notification';
        notification.id = 'cart-notification';
        document.body.appendChild(notification);
    }
    notification.innerHTML = `<span class="notification-message">${message}</span>`;
    notification.className = `cart-notification ${type} show`;
    setTimeout(() => notification.classList.remove('show'), 2500);
}


function createCartBadge(count) {
    const cartButton = document.querySelector('.cart-button');
    if (!cartButton) return;
    const badge = document.createElement('span');
    badge.className = 'cart-badge';
    badge.textContent = count;
    cartButton.appendChild(badge);
}

function updateCartCount(addedQty) {
    const cartBadge = document.querySelector('.cart-badge');
    if (cartBadge) {
        const currentCount = parseInt(cartBadge.textContent) || 0;
        cartBadge.textContent = currentCount + addedQty;
        cartBadge.classList.add('updating');
        setTimeout(() => cartBadge.classList.remove('updating'), 500);
    } else {
        createCartBadge(addedQty);
    }
    btn.textContent = originalText;
    btn.disabled = false;
    isSubmitting = false;
}

function setCartCount(count) {
    const badge = document.querySelector('.cart-badge');
    if (badge) {
        badge.textContent = count;
    } else {
        createCartBadge(count);
    }
}


function buildCartParams(form) {
    const pidInput = form.querySelector('input[name="productId"]');
    const qtyInput = form.querySelector('input[name="quantity"]');
    const params = new URLSearchParams();
    params.set('action', 'add');
    params.set('productId', pidInput ? pidInput.value : '');
    params.set('quantity', qtyInput ? qtyInput.value : '1');
    return params;
}

function buildFetchOptions(params) {
    return {
        method: 'POST',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest',
            'Accept': 'application/json'
        },
        body: params.toString()
    };
}

function parseResponse(res) {
    const ct = res.headers.get('Content-Type') || '';
    return ct.includes('application/json') ? res.json() : res.text();
}


// Fix: Tách reset button thành hàm riêng để giảm complexity
function resetButton(btn, originalText) {
    btn.textContent = originalText;
    btn.disabled = false;
    isSubmitting = false;
}

// Fix: Tách lock button thành hàm riêng
function lockButton(btn) {
    const originalText = btn.textContent;
    btn.textContent = 'Đang thêm...';
    btn.disabled = true;
    return originalText;
}

// Fix: Gom 6 params vào 1 context object để giảm số lượng tham số
function handleAddToCartResult(result, ctx) {
    const { actionUrl, val, btn, originalText, input } = ctx;

    if (result && typeof result === 'object' && result.success) {
        showNotification('✓ Đã thêm vào giỏ hàng thành công!', 'success');
        if (typeof result.cartCount !== 'undefined') {
            setCartCount(result.cartCount);
        } else {
            updateCartCount(val);
        }
        input.value = 1;
    } else if (result && (result.html || typeof result === 'string')) {
        window.location.href = actionUrl;
    } else {
        showNotification('Có lỗi xảy ra. Vui lòng thử lại.', 'error');
    }

    resetButton(btn, originalText);
}

// Fix: Tách build params từ FormData thành hàm riêng
function buildParamsFromForm(form) {
    const fd = new FormData(form);
    const params = new URLSearchParams();
    for (const [k, v] of fd.entries()) params.append(k, v);
    return params;
}

function handleFormSubmit(e, form) {
    e.preventDefault();
    if (isSubmitting || justValidated) {
        justValidated = false;
        return;
    }

    const input = document.getElementById('quantity');
    const val = parseInt(input.value);
    const min = parseInt(input.getAttribute('min')) || 1;
    const max = parseInt(input.getAttribute('max')) || 999;

    if (!clampQuantity(val, min, max, input)) return;

    const btn = form.querySelector('button[type="submit"]');
    if (!btn) return;

    isSubmitting = true;
    const originalText = lockButton(btn);
    const actionUrl = form.getAttribute('action');
    const params = buildParamsFromForm(form);
    const ctx = { actionUrl, val, btn, originalText, input };

    fetch(actionUrl, buildFetchOptions(params))
        .then(res => parseResponse(res))
        .then(result => handleAddToCartResult(result, ctx))
        .catch(() => {
            showNotification('Có lỗi xảy ra. Vui lòng thử lại.', 'error');
            resetButton(btn, originalText);
        });
}


// Fix: Tách setup quantity listeners thành hàm riêng để giảm complexity DOMContentLoaded
function setupQuantityInput(qtyInput) {
    qtyInput.removeAttribute('readonly');

    qtyInput.addEventListener('input', function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
    qtyInput.addEventListener('blur', function () { validateQuantity(this); });
    qtyInput.addEventListener('change', function () { validateQuantity(this); });
    qtyInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            e.stopPropagation();
            this.blur();
        }
    });
    qtyInput.addEventListener('paste', function (e) {
        e.preventDefault();
        const pastedText = (e.clipboardData || window.clipboardData).getData('text');
        const numbersOnly = pastedText.replace(/[^0-9]/g, '');
        if (numbersOnly) {
            this.value = numbersOnly;
            this.dispatchEvent(new Event('change'));
        }
    });
}

// Fix: Tách keyboard navigation thành hàm riêng
function setupKeyboardNavigation() {
    document.addEventListener('keydown', function (e) {
        if (productImages.length <= 1) return;
        if (e.key === 'ArrowLeft') prevImage();
        if (e.key === 'ArrowRight') nextImage();
    });
}

document.addEventListener('DOMContentLoaded', function () {
    initGallery();

    const form = document.querySelector('.add-to-cart-form');
    if (form) {
        form.addEventListener('submit', e => handleFormSubmit(e, form));
    }

    const qtyInput = document.getElementById('quantity');
    if (qtyInput) setupQuantityInput(qtyInput);

    setupKeyboardNavigation();
});

console.log('Product Detail JS loaded');