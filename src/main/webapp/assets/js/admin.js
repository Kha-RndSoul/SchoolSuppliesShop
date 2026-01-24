// Admin Dashboard JavaScript
console.log(' admin.js loaded');

// ========== GLOBAL VARIABLES ==========
let currentProductPage = 1;
let productsPerPage = 20;
let isEditMode = false;
let currentEditProductId = null;
let allProductsCache = [];
let currentSearchKeyword = '';

// ========== DOM READY ==========
document.addEventListener('DOMContentLoaded', function() {
    console.log(' DOMContentLoaded event fired');

    restoreSectionFromHash();
    window.addEventListener('hashchange', handleHashChange);

    initNavigation();
    initProductForm();
    initFilterForm();
});

// ========== NAVIGATION WITH HASH ==========
function initNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const sectionName = this.getAttribute('data-section');
            showSection(sectionName);
        });
    });
}

function showSection(sectionName) {
    console.log(' Showing section:', sectionName);

    const navItems = document.querySelectorAll('.nav-item');
    const sections = document.querySelectorAll('.admin-section');

    navItems.forEach(nav => {
        if (nav.getAttribute('data-section') === sectionName) {
            nav.classList.add('active');
        } else {
            nav.classList.remove('active');
        }
    });

    sections.forEach(section => section.classList.remove('active'));

    const targetSection = document.getElementById(sectionName + '-section');
    if (targetSection) {
        targetSection.classList.add('active');
    }

    const newHash = sectionName + '-section';
    if (window.location.hash !== '#' + newHash) {
        window.location.hash = newHash;
    }

    if (sectionName === 'products' && !targetSection.dataset.ajaxLoaded) {
        loadProductsPage(1);
    }
}

function restoreSectionFromHash() {
    const hash = window.location.hash.substring(1);

    if (hash) {
        const parts = hash.split('?');
        const sectionId = parts[0];
        const sectionName = sectionId.replace('-section', '');

        if (parts[1]) {
            const params = new URLSearchParams(parts[1]);
            const page = params.get('page');
            if (page && sectionName === 'products') {
                currentProductPage = parseInt(page) || 1;
            }
        }

        showSection(sectionName);
    } else {
        showSection('dashboard');
    }
}

function handleHashChange() {
    console.log(' Hash changed:', window.location.hash);
    restoreSectionFromHash();
}

// ========== AJAX PRODUCTS PAGINATION ==========
function loadProductsPage(page) {
    // Nếu đang search, không load
    if (currentSearchKeyword.length > 0) {
        return;
    }

    console.log('📦 Loading products page:', page);

    const tbody = document.getElementById('productTableBody');

    if (!tbody) {
        console.error(' Product table body not found');
        return;
    }

    tbody.innerHTML = `
        <tr>
            <td colspan="7" style="text-align: center; padding: 3rem;">
                <div style="font-size: 2rem;">⏳</div>
                <div style="margin-top: 1rem; color: #6b7280;">Đang tải dữ liệu...</div>
            </td>
        </tr>
    `;

    const contextPath = window.location.pathname.split('/')[1];
    const apiUrl = `/${contextPath}/admin/api/products?page=${page}&pageSize=${productsPerPage}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                renderProductsTable(data.products);
                renderPagination(data.pagination);
                currentProductPage = data.pagination.currentPage;
                updateHashWithPage(data.pagination.currentPage);

                const productsSection = document.getElementById('products-section');
                if (productsSection) {
                    productsSection.dataset.ajaxLoaded = 'true';
                }
            } else {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="7" style="text-align: center; padding: 2rem; color: #dc2626;">
                            ❌ ${data.message || 'Có lỗi xảy ra'}
                        </td>
                    </tr>
                `;
            }
        })
        .catch(error => {
            console.error('❌ Error loading products:', error);
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" style="text-align: center; padding: 2rem; color: #dc2626;">
                        ❌ Không thể tải dữ liệu sản phẩm
                    </td>
                </tr>
            `;
        });
}

function renderProductsTable(products) {
    const tbody = document.getElementById('productTableBody');

    if (products.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align: center; padding: 2rem; color: #6b7280;">
                    Chưa có sản phẩm nào
                </td>
            </tr>
        `;
        return;
    }

    let html = '';
    products.forEach(product => {
        const price = product.price || 0;
        const stockQuantity = product.stockQuantity || 0;

        let stockBadgeClass = 'low';
        if (stockQuantity >= 500) stockBadgeClass = 'high';
        else if (stockQuantity >= 100) stockBadgeClass = 'medium';

        html += `
            <tr>
                <td><span class="order-code">#SP${product.id}</span></td>
                <td><strong>${product.productName || 'N/A'}</strong></td>
                <td>${product.categoryName || 'N/A'}</td>
                <td>${product.brandName || 'N/A'}</td>
                <td><strong>${formatCurrency(price)}</strong></td>
                <td><span class="stock-badge ${stockBadgeClass}">${formatNumber(stockQuantity)}</span></td>
                <td>
                    <button class="btn-edit" onclick="editProduct(${product.id})" title="Sửa">✏️ Sửa</button>
                    <button class="btn-delete" onclick="deleteProduct(${product.id}, '${escapeHtml(product.productName)}')" title="Xóa">🗑️ Xóa</button>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = html;
}

function renderPagination(pagination) {
    const paginationContainer = document.querySelector('#products-section .pagination');

    if (!paginationContainer) {
        console.warn('⚠️ Pagination container not found');
        return;
    }

    const { currentPage, totalPages, startIndex, endIndex, totalItems } = pagination;

    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }

    let html = '<div class="pagination-controls">';

    html += `<button class="pagination-btn" onclick="changeProductPage(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''}>◀ Trước</button>`;

    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            const activeClass = i === currentPage ? 'active' : '';
            html += `<button class="pagination-btn ${activeClass}" onclick="changeProductPage(${i})">${i}</button>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            html += '<span class="pagination-dots">...</span>';
        }
    }

    html += `<button class="pagination-btn" onclick="changeProductPage(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''}>Sau ▶</button>`;
    html += '</div>';

    paginationContainer.innerHTML = html;
}

function changeProductPage(page) {
    if (page < 1) return;
    loadProductsPage(page);

    const productsSection = document.getElementById('products-section');
    if (productsSection) {
        productsSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

function updateHashWithPage(page) {
    const currentHash = window.location.hash;
    const baseHash = currentHash.split('?')[0] || '#products-section';
    const newHash = page > 1 ? `${baseHash}?page=${page}` : baseHash;

    if (window.location.hash !== newHash) {
        history.replaceState(null, '', newHash);
    }
}

// ========== PRODUCT SEARCH ==========
function searchProducts() {
    const input = document.getElementById('productSearchInput');
    const clearBtn = document.getElementById('searchClearBtn');
    const keyword = input.value.trim().toLowerCase();

    if (keyword.length > 0) {
        clearBtn.style.display = 'flex';
    } else {
        clearBtn.style.display = 'none';
    }

    currentSearchKeyword = keyword;

    if (keyword.length === 0) {
        loadProductsPage(currentProductPage);
        return;
    }

    if (allProductsCache.length === 0) {
        loadAllProductsForSearch(keyword);
        return;
    }

    performSearch(keyword);
}

function loadAllProductsForSearch(keyword) {
    const tbody = document.getElementById('productTableBody');

    tbody.innerHTML = `
        <tr>
            <td colspan="7" style="text-align: center; padding: 2rem;">
                <div style="font-size: 1.5rem;">🔍</div>
                <div style="margin-top: 0.5rem; color: #6b7280;">Đang tải dữ liệu tìm kiếm...</div>
            </td>
        </tr>
    `;

    const contextPath = window.location.pathname.split('/')[1];
    const apiUrl = `/${contextPath}/admin/api/products?page=1&pageSize=10000`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                allProductsCache = data.products;
                performSearch(keyword);
            } else {
                showSearchError('Không thể tải dữ liệu sản phẩm');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showSearchError('Có lỗi xảy ra khi tải dữ liệu');
        });
}

function performSearch(keyword) {
    const results = allProductsCache.filter(product => {
        const id = String(product.id).toLowerCase();
        const name = (product.productName || '').toLowerCase();
        const category = (product.categoryName || '').toLowerCase();
        const brand = (product.brandName || '').toLowerCase();

        return id.includes(keyword) ||
            name.includes(keyword) ||
            category.includes(keyword) ||
            brand.includes(keyword);
    });

    displaySearchResults(results, keyword);
}

function displaySearchResults(results, keyword) {
    const tbody = document.getElementById('productTableBody');
    const paginationContainer = document.querySelector('#products-section .pagination');

    if (paginationContainer) {
        paginationContainer.innerHTML = '';
    }

    if (results.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="no-search-results">
                    <div class="no-search-results-icon">🔍</div>
                    <div style="font-size: 1.1rem; font-weight: 500; color: #374151; margin-bottom: 0.5rem;">
                        Không tìm thấy sản phẩm
                    </div>
                    <div style="color: #6b7280;">
                        Không có sản phẩm nào khớp với từ khóa "<strong>${escapeHtml(keyword)}</strong>"
                    </div>
                </td>
            </tr>
        `;
        return;
    }

    let html = '';
    results.forEach(product => {
        const price = product.price || 0;
        const stockQuantity = product.stockQuantity || 0;

        let stockBadgeClass = 'low';
        if (stockQuantity >= 500) stockBadgeClass = 'high';
        else if (stockQuantity >= 100) stockBadgeClass = 'medium';

        let productName = product.productName || 'N/A';
        if (keyword) {
            const regex = new RegExp(`(${escapeRegex(keyword)})`, 'gi');
            productName = productName.replace(regex, '<span class="search-highlight">$1</span>');
        }

        html += `
            <tr>
                <td><span class="order-code">#SP${product.id}</span></td>
                <td><strong>${productName}</strong></td>
                <td>${product.categoryName || 'N/A'}</td>
                <td>${product.brandName || 'N/A'}</td>
                <td><strong>${formatCurrency(price)}</strong></td>
                <td><span class="stock-badge ${stockBadgeClass}">${formatNumber(stockQuantity)}</span></td>
                <td>
                    <button class="btn-edit" onclick="editProduct(${product.id})" title="Sửa">✏️ Sửa</button>
                    <button class="btn-delete" onclick="deleteProduct(${product.id}, '${escapeHtml(product.productName)}')" title="Xóa">🗑️ Xóa</button>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = html;

    if (paginationContainer) {
        paginationContainer.innerHTML = `
            <div style="text-align: center; padding: 1rem 0; color: #6b7280; font-size: 0.9rem;">
                Tìm thấy <strong style="color: #059669;">${results.length}</strong> sản phẩm khớp với từ khóa "<strong>${escapeHtml(keyword)}</strong>"
            </div>
        `;
    }
}

function clearSearch() {
    const input = document.getElementById('productSearchInput');
    const clearBtn = document.getElementById('searchClearBtn');

    input.value = '';
    clearBtn.style.display = 'none';
    currentSearchKeyword = '';

    loadProductsPage(currentProductPage);
}

function showSearchError(message) {
    const tbody = document.getElementById('productTableBody');
    tbody.innerHTML = `
        <tr>
            <td colspan="7" style="text-align: center; padding: 2rem; color: #dc2626;">
                ❌ ${message}
            </td>
        </tr>
    `;
}

function escapeRegex(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

// ========== FILTER FORM AUTO SUBMIT ==========
function initFilterForm() {
    const filterSelect = document.querySelector('.filter-select');
    if (filterSelect) {
        filterSelect.addEventListener('change', function() {
            const filterValue = this.value;
            if (filterValue !== 'custom') {
                const form = this.closest('form') || this.parentElement.querySelector('form');
                if (form) {
                    form.submit();
                } else {
                    const contextPath = window.location.pathname.split('/')[1];
                    window.location.href = `/${contextPath}/admin/dashboard?filter=${filterValue}#dashboard-section`;
                }
            }
        });
    }
}

// ========== PRODUCT FORM ==========
function initProductForm() {
    const productForm = document.getElementById('productForm');
    if (productForm) {
        productForm.addEventListener('submit', function(e) {
            e.preventDefault();
            submitProductForm();
        });
        console.log('✅ Product form event listener attached');
    }
}

function toggleProductForm() {
    const formContainer = document.getElementById('productFormContainer');
    const toggleBtn = document.getElementById('toggleFormBtn');

    if (!formContainer || !toggleBtn) return;

    if (formContainer.style.display === 'none' || !formContainer.style.display) {
        isEditMode = false;
        currentEditProductId = null;
        resetForm();

        formContainer.style.display = 'block';
        toggleBtn.innerHTML = '✕ Đóng Form';
        toggleBtn.classList.add('btn-close');

        document.querySelector('.product-form-container h3').textContent = '📦 Thêm Sản Phẩm Mới';
        document.getElementById('submitBtn').innerHTML = '💾 Lưu Sản Phẩm';
    } else {
        formContainer.style.display = 'none';
        toggleBtn.innerHTML = '➕ Thêm Sản Phẩm';
        toggleBtn.classList.remove('btn-close');
        resetForm();
    }
}

function resetForm() {
    const form = document.getElementById('productForm');
    if (form) {
        form.reset();
        const imagePreview = document.getElementById('imagePreview');
        if (imagePreview) imagePreview.innerHTML = '';

        const actionInput = form.querySelector('input[name="action"]');
        if (actionInput) actionInput.value = 'add';

        const productIdInput = form.querySelector('input[name="productId"]');
        if (productIdInput) productIdInput.remove();
    }

    isEditMode = false;
    currentEditProductId = null;
}

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
            preview.innerHTML = `<img src="${e.target.result}" alt="Preview" style="max-width: 200px; max-height: 200px; border-radius: 8px; margin-top: 10px;">`;
        };
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
}

function submitProductForm() {
    const form = document.getElementById('productForm');
    const submitBtn = document.getElementById('submitBtn');
    const formData = new FormData(form);

    submitBtn.disabled = true;
    submitBtn.innerHTML = '⏳ Đang xử lý...';

    const contextPath = window.location.pathname.split('/')[1];
    const url = `/${contextPath}/admin/products`;

    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showToast(data.message, 'success');
                resetForm();
                setTimeout(() => {
                    toggleProductForm();
                    // Clear cache để reload data mới
                    allProductsCache = [];
                    if (currentSearchKeyword.length > 0) {
                        clearSearch();
                    } else {
                        loadProductsPage(currentProductPage);
                    }
                }, 1500);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi lưu sản phẩm', 'error');
        })
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.innerHTML = isEditMode ? '💾 Cập Nhật' : '💾 Lưu Sản Phẩm';
        });
}

// ========== EDIT PRODUCT ==========
function editProduct(productId) {
    console.log('✏️ Edit product:', productId);

    const contextPath = window.location.pathname.split('/')[1];
    const url = `/${contextPath}/admin/products?action=getProduct&productId=${productId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                loadProductDataToForm(data.product);
            } else {
                showToast(data.message || 'Không thể tải dữ liệu sản phẩm', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi tải dữ liệu', 'error');
        });
}

function loadProductDataToForm(product) {
    isEditMode = true;
    currentEditProductId = product.id;

    const formContainer = document.getElementById('productFormContainer');
    const toggleBtn = document.getElementById('toggleFormBtn');

    formContainer.style.display = 'block';
    toggleBtn.innerHTML = '✕ Đóng Form';
    toggleBtn.classList.add('btn-close');

    document.querySelector('.product-form-container h3').textContent = '✏️ Chỉnh Sửa Sản Phẩm';
    document.getElementById('submitBtn').innerHTML = '💾 Cập Nhật';

    document.getElementById('productName').value = product.productName || '';
    document.getElementById('description').value = product.description || '';
    document.getElementById('categoryId').value = product.categoryId || '';
    document.getElementById('brandId').value = product.brandId || '';
    document.getElementById('price').value = product.price || '';
    document.getElementById('salePrice').value = product.salePrice || '';
    document.getElementById('stockQuantity').value = product.stockQuantity || '';

    const imagePreview = document.getElementById('imagePreview');
    if (product.imageUrl) {
        imagePreview.innerHTML = `
            <img src="${product.imageUrl}" alt="Current Image" 
                 style="max-width: 200px; max-height: 200px; border-radius: 8px; margin-top: 10px;">
            <p style="font-size: 0.85rem; color: #6b7280; margin-top: 0.5rem;">
                Ảnh hiện tại (chọn file mới để thay đổi)
            </p>
        `;
    }

    const form = document.getElementById('productForm');
    const actionInput = form.querySelector('input[name="action"]');
    if (actionInput) {
        actionInput.value = 'update';
    }

    let productIdInput = form.querySelector('input[name="productId"]');
    if (!productIdInput) {
        productIdInput = document.createElement('input');
        productIdInput.type = 'hidden';
        productIdInput.name = 'productId';
        form.appendChild(productIdInput);
    }
    productIdInput.value = product.id;

    formContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// ========== DELETE PRODUCT ==========
function deleteProduct(productId, productName) {
    showConfirmDialog(
        'Xác nhận xóa sản phẩm',
        `Bạn có chắc chắn muốn xóa sản phẩm "${productName}"?`,
        'Hành động này không thể hoàn tác!',
        () => performDeleteProduct(productId, productName)
    );
}

function performDeleteProduct(productId, productName) {
    const contextPath = window.location.pathname.split('/')[1];
    const url = `/${contextPath}/admin/products`;

    const formData = new FormData();
    formData.append('action', 'delete');
    formData.append('productId', productId);

    fetch(url, { method: 'POST', body: formData })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showToast(data.message, 'success');
                // Clear cache
                allProductsCache = [];
                setTimeout(() => {
                    if (currentSearchKeyword.length > 0) {
                        clearSearch();
                    } else {
                        loadProductsPage(currentProductPage);
                    }
                }, 1000);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi xóa sản phẩm', 'error');
        });
}

// ========== UTILITY FUNCTIONS ==========
function showToast(message, type) {
    const toast = document.getElementById('toast');
    if (!toast) return;

    const icons = { success: '✅', error: '❌', warning: '⚠️', info: 'ℹ️' };
    const icon = icons[type] || 'ℹ️';

    toast.innerHTML = `${icon} ${message}`;
    toast.className = `toast toast-${type} show`;

    setTimeout(() => toast.className = 'toast', 4000);
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount || 0);
}

function formatNumber(num) {
    return new Intl.NumberFormat('vi-VN').format(num || 0);
}

function escapeHtml(text) {
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' };
    return text.replace(/[&<>"']/g, m => map[m]);
}

function viewOrderDetail(orderId) {
    showToast('Chức năng đang phát triển', 'info');
}

// ========== CONFIRM DIALOG ==========
function showConfirmDialog(title, message, submessage, onConfirm) {
    const dialogHTML = `
        <div class="modal-overlay" id="confirmOverlay" onclick="closeConfirmDialog()"></div>
        <div class="modal-container" id="confirmDialog">
            <div class="modal-header">
                <h3 class="modal-title">${title}</h3>
                <button class="modal-close" onclick="closeConfirmDialog()">×</button>
            </div>
            <div class="modal-body">
                <div class="confirm-dialog">
                    <div class="confirm-icon warning">⚠️</div>
                    <p class="confirm-message">${message}</p>
                    <p class="confirm-submessage">${submessage}</p>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn-secondary" onclick="closeConfirmDialog()">✕ Hủy</button>
                <button class="btn-danger" id="confirmBtn">✓ Xác Nhận Xóa</button>
            </div>
        </div>
    `;

    const dialogContainer = document.createElement('div');
    dialogContainer.innerHTML = dialogHTML;
    document.body.appendChild(dialogContainer);

    setTimeout(() => {
        document.getElementById('confirmOverlay').classList.add('show');
        document.getElementById('confirmDialog').classList.add('show');
    }, 10);

    document.getElementById('confirmBtn').onclick = () => {
        closeConfirmDialog();
        if (onConfirm) onConfirm();
    };
}

function closeConfirmDialog() {
    const overlay = document.getElementById('confirmOverlay');
    const dialog = document.getElementById('confirmDialog');

    if (overlay) overlay.classList.remove('show');
    if (dialog) dialog.classList.remove('show');

    setTimeout(() => {
        if (overlay) overlay.remove();
        if (dialog) dialog.remove();
    }, 300);
}

console.log(' All functions defined');