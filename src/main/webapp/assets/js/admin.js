
console.log('admin.js loaded');
// Lấy tên project  từ URL
const CONTEXT_PATH = window.location.pathname.split('/')[1];
let currentProductPage = 1;
let productsPerPage = 20;
let isEditMode = false;
let currentEditProductId = null;
let allProductsCache = [];
let currentSearchKeyword = '';

// DOM READY
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOMContentLoaded event fired');
    restoreSectionFromHash();
    window.addEventListener('hashchange', handleHashChange);

    initNavigation();
    initProductForm();
    initFilterForm();
});
//  SHARED VIEW HELPERS
function createProductRowHtml(product, highlightKeyword = null) {
    const price = product.price || 0;
    const stockQuantity = product.stockQuantity || 0;

    let stockBadgeClass = 'low';
    if (stockQuantity >= 500) stockBadgeClass = 'high';
    else if (stockQuantity >= 100) stockBadgeClass = 'medium';

    let productName = product.productName || 'N/A';
    if (highlightKeyword) {
        const regex = new RegExp(`(${escapeRegex(highlightKeyword)})`, 'gi');
        productName = productName.replace(regex, '<span class="search-highlight">$1</span>');
    }
    return `
        <tr>
            <td><span class="order-code">#SP${product.id}</span></td>
            <td><strong>${productName}</strong></td>
            <td>${product.categoryName || 'N/A'}</td>
            <td>${product.brandName || 'N/A'}</td>
            <td><strong>${formatCurrency(price)}</strong></td>
            <td><span class="stock-badge ${stockBadgeClass}">${formatNumber(stockQuantity)}</span></td>
            <td>
                <button class="btn-edit" onclick="editProduct(${product.id})" title="Sửa"> Sửa</button>
                <button class="btn-delete" onclick="deleteProduct(${product.id}, '${escapeHtml(product.productName)}')" title="Xóa"> Xóa</button>
            </td>
        </tr>
    `;
}
// Hàm hiển thị thông báo trong bảng
function renderTableMessage(message, type = 'info') {
    const tbody = document.getElementById('productTableBody');
    if (!tbody) return;

    let color = '#6b7280';
    if (type === 'error') color = '#dc2626';

    const loadingHtml = type === 'loading' ? '<div style="font-size: 2rem;"></div>' : '';

    tbody.innerHTML = `
        <tr>
            <td colspan="7" style="text-align: center; padding: 2rem; color: ${color};">
                ${loadingHtml}
                <div style="margin-top: ${type === 'loading' ? '1rem' : '0'};">${message}</div>
            </td>
        </tr>
    `;
}
// Hàm làm mới dữ liệu sau khi Thêm/Sửa/Xóa
function refreshDataAfterAction() {
    allProductsCache = [];
    if (currentSearchKeyword.length > 0) {
        clearSearch();
    } else {
        loadProductsPage(currentProductPage);
    }
}
//  AJAX PRODUCTS PAGINATION
function loadProductsPage(page) {
    if (currentSearchKeyword.length > 0) return;
    renderTableMessage('Đang tải dữ liệu...', 'loading');

    const apiUrl = `/${CONTEXT_PATH}/admin/api/products?page=${page}&pageSize=${productsPerPage}`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                renderProductsTable(data.products);
                renderPagination(data.pagination);
                currentProductPage = data.pagination.currentPage;
                updateHashWithPage(data.pagination.currentPage);

                const productsSection = document.getElementById('products-section');
                if (productsSection) productsSection.dataset.ajaxLoaded = 'true';
            } else {
                renderTableMessage(data.message || 'Có lỗi xảy ra', 'error');
            }
        })
        .catch(error => {
            console.error(error);
            renderTableMessage('Không thể tải dữ liệu sản phẩm', 'error');
        });
}
//
function renderProductsTable(products) {
    const tbody = document.getElementById('productTableBody');
    if (products.length === 0) {
        renderTableMessage('Chưa có sản phẩm nào', 'info');
        return;
    }
    tbody.innerHTML = products.map(p => createProductRowHtml(p)).join('');
}
//
function renderPagination(pagination) {
    const paginationContainer = document.querySelector('#products-section .pagination');
    if (!paginationContainer) return;
    const { currentPage, totalPages } = pagination;

    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }
    let html = '<div class="pagination-controls">';
    html += `<button class="pagination-btn" onclick="changeProductPage(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''}>◀ Trước</button>`;

    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            html += `<button class="pagination-btn ${i === currentPage ? 'active' : ''}" onclick="changeProductPage(${i})">${i}</button>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            html += '<span class="pagination-dots">...</span>';
        }
    }

    html += `<button class="pagination-btn" onclick="changeProductPage(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''}>Sau ▶</button>`;
    html += '</div>';
    paginationContainer.innerHTML = html;
}
// Hàm chuyển trang
function changeProductPage(page) {
    if (page < 1) return;
    loadProductsPage(page);
    document.getElementById('products-section').scrollIntoView({ behavior: 'smooth', block: 'start' });
}
// Cập nhật URL hash với trang hiện tại
function updateHashWithPage(page) {
    const currentHash = window.location.hash;
    const baseHash = currentHash.split('?')[0] || '#products-section';
    const newHash = page > 1 ? `${baseHash}?page=${page}` : baseHash;
    if (window.location.hash !== newHash) history.replaceState(null, '', newHash);
}
//  PRODUCT SEARCH
function searchProducts() {
    const input = document.getElementById('productSearchInput');
    const keyword = input.value.trim().toLowerCase();
    document.getElementById('searchClearBtn').style.display = keyword.length > 0 ? 'flex' : 'none';
    currentSearchKeyword = keyword;
    if (keyword.length === 0) {
        loadProductsPage(currentProductPage);
        return;
    }

    if (allProductsCache.length === 0) {
        loadAllProductsForSearch(keyword);
    } else {
        performSearch(keyword);
    }
}
// Tải tất cả sản phẩm để tìm kiếm
function loadAllProductsForSearch(keyword) {
    renderTableMessage('Đang tải dữ liệu tìm kiếm...', 'loading');
    fetch(`/${CONTEXT_PATH}/admin/api/products?page=1&pageSize=10000`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                allProductsCache = data.products;
                performSearch(keyword);
            } else {
                renderTableMessage('Không thể tải dữ liệu sản phẩm', 'error');
            }
        })
        .catch(() => renderTableMessage('Có lỗi xảy ra khi tải dữ liệu', 'error'));
}

function performSearch(keyword) {
    const results = allProductsCache.filter(product => {
        const id = String(product.id).toLowerCase();
        const name = (product.productName || '').toLowerCase();
        return id.includes(keyword) || name.includes(keyword);
    });
    displaySearchResults(results, keyword);
}

function displaySearchResults(results, keyword) {
    const tbody = document.getElementById('productTableBody');
    const pagination = document.querySelector('#products-section .pagination');
    if (pagination) pagination.innerHTML = '';

    if (results.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="no-search-results">Không tìm thấy sản phẩm "${escapeHtml(keyword)}"</td></tr>`;
        return;
    }

    tbody.innerHTML = results.map(p => createProductRowHtml(p, keyword)).join('');
    if (pagination) {
        pagination.innerHTML = `<div style="text-align: center; color: #6b7280;">Tìm thấy <strong>${results.length}</strong> kết quả</div>`;
    }
}
function clearSearch() {
    document.getElementById('productSearchInput').value = '';
    document.getElementById('searchClearBtn').style.display = 'none';
    currentSearchKeyword = '';
    loadProductsPage(currentProductPage);
}
//  PRODUCT FORM (ADD/EDIT)
function initProductForm() {
    const productForm = document.getElementById('productForm');
    if (productForm) {
        productForm.addEventListener('submit', function(e) {
            e.preventDefault();
            submitProductForm();
        });
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
        document.querySelector('.product-form-container h3').textContent = 'Thêm Sản Phẩm Mới';
        document.getElementById('submitBtn').innerHTML = 'Lưu Sản Phẩm';
    } else {
        formContainer.style.display = 'none';
        toggleBtn.innerHTML = 'Thêm Sản Phẩm';
        toggleBtn.classList.remove('btn-close');
        resetForm();
    }
}
function resetForm() {
    const form = document.getElementById('productForm');
    if (form) {
        form.reset();
        document.getElementById('imagePreview').innerHTML = '';
        const actionInput = form.querySelector('input[name="action"]');
        if (actionInput) actionInput.value = 'add';
        const productIdInput = form.querySelector('input[name="productId"]');
        if (productIdInput) productIdInput.remove();
    }
}
function editProduct(productId) {
    console.log('Edit product:', productId);
    const url = `/${CONTEXT_PATH}/admin/products?action=getProduct&productId=${productId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                loadProductDataToForm(data.product);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(() => showToast('Lỗi tải dữ liệu', 'error'));
}
//  LOAD DỮ LIỆU VÀ HIỂN THỊ ẢNH
function loadProductDataToForm(product) {
    isEditMode = true;
    currentEditProductId = product.id;

    const formContainer = document.getElementById('productFormContainer');
    const toggleBtn = document.getElementById('toggleFormBtn');
    formContainer.style.display = 'block';
    toggleBtn.innerHTML = '✕ Đóng Form';
    toggleBtn.classList.add('btn-close');
    document.querySelector('.product-form-container h3').textContent = 'Chỉnh Sửa Sản Phẩm';
    document.getElementById('submitBtn').innerHTML = 'Cập Nhật';
    // Đổ dữ liệu vào input
    document.getElementById('productName').value = product.productName || '';
    document.getElementById('description').value = product.description || '';
    document.getElementById('categoryId').value = product.categoryId || '';
    document.getElementById('brandId').value = product.brandId || '';
    document.getElementById('price').value = product.price || '';
    document.getElementById('salePrice').value = product.salePrice || '';
    document.getElementById('stockQuantity').value = product.stockQuantity || '';
    //  XỬ LÝ HIỂN THỊ DANH SÁCH ẢNH
    const imagePreview = document.getElementById('imagePreview');
    imagePreview.innerHTML = ''; // Xóa cũ
    let imagesHtml = '';

    if (product.images && product.images.length > 0) {
        imagesHtml += '<div style="display: flex; gap: 10px; flex-wrap: wrap;">';

        product.images.forEach(img => {
            let imgUrl = img.imageUrl;
            let finalSrc = imgUrl;
            // Xử lý đường dẫn
            if (imgUrl.startsWith('/') && CONTEXT_PATH) {
                finalSrc = `/${CONTEXT_PATH}${imgUrl}`;
            } else if (!imgUrl.startsWith('http') && !imgUrl.startsWith('/')) {
                finalSrc = `/${CONTEXT_PATH}/${imgUrl}`;
            }
            // Tạo border xanh cho ảnh chính
            const borderStyle = img.isPrimary ? 'border: 2px solid #059669;' : 'border: 1px solid #ddd;';
            const badge = img.isPrimary ? '<span style="position: absolute; top: -5px; right: -5px; background: #059669; color: white; font-size: 10px; padding: 2px 5px; border-radius: 4px;">Chính</span>' : '';

            imagesHtml += `
                <div style="position: relative; width: 100px; height: 100px;">
                    <img src="${finalSrc}" 
                         style="width: 100%; height: 100%; object-fit: cover; border-radius: 6px; ${borderStyle}">
                    ${badge}
                </div>
            `;
        });
        imagesHtml += '</div>';
        imagesHtml += '<p style="font-size: 0.85rem; color: #6b7280; margin-top: 0.5rem;">Ảnh hiện tại (Chọn file mới để thay thế hoặc thêm ảnh)</p>';
    } else {
        //  Nếu không có list images, thử hiển thị ảnh đơn lẻ
        const singleImg = product.imageUrl || product.image;
        if (singleImg) {
            let finalSrc = singleImg.startsWith('/') ? `/${CONTEXT_PATH}${singleImg}` : `/${CONTEXT_PATH}/${singleImg}`;
            imagesHtml = `<img src="${finalSrc}" style="max-width: 150px; border-radius: 8px;">`;
        }
    }
    imagePreview.innerHTML = imagesHtml;
    // Set input hidden
    const form = document.getElementById('productForm');
    let actionInput = form.querySelector('input[name="action"]');
    if (!actionInput) {
        actionInput = document.createElement('input'); actionInput.type='hidden'; actionInput.name='action'; form.appendChild(actionInput);
    }
    actionInput.value = 'update';
    let pidInput = form.querySelector('input[name="productId"]');
    if (!pidInput) {
        pidInput = document.createElement('input'); pidInput.type = 'hidden'; pidInput.name = 'productId'; form.appendChild(pidInput);
    }
    pidInput.value = product.id;
    formContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
}
function submitProductForm() {
    const form = document.getElementById('productForm');
    const submitBtn = document.getElementById('submitBtn');
    const formData = new FormData(form);

    submitBtn.disabled = true;
    submitBtn.innerHTML = 'Đang xử lý...';

    fetch(`/${CONTEXT_PATH}/admin/products`, {
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
                    refreshDataAfterAction();
                }, 1000);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(() => showToast('Lỗi lưu sản phẩm', 'error'))
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.innerHTML = isEditMode ? 'Cập Nhật' : 'Lưu Sản Phẩm';
        });
}
// ========== DELETE PRODUCT ==========
function deleteProduct(productId, productName) {
    showConfirmDialog(
        'Xác nhận xóa',
        `Bạn xóa sản phẩm "${productName}"?`,
        'Không thể hoàn tác!',
        () => {
            const formData = new FormData();
            formData.append('action', 'delete');
            formData.append('productId', productId);

            fetch(`/${CONTEXT_PATH}/admin/products`, { method: 'POST', body: formData })
                .then(r => r.json())
                .then(data => {
                    if(data.success) {
                        showToast(data.message, 'success');
                        setTimeout(refreshDataAfterAction, 1000);
                    } else showToast(data.message, 'error');
                })
                .catch(() => showToast('Lỗi khi xóa', 'error'));
        }
    );
}
// ========== HELPERS & UTILS ==========
function showToast(message, type) {
    const toast = document.getElementById('toast');
    if (!toast) return;
    const icons = { success: '✓', error: '✕', warning: '⚠️', info: 'ℹ' };
    toast.innerHTML = `${icons[type] || ''} ${message}`;
    toast.className = `toast toast-${type} show`;
    setTimeout(() => toast.className = 'toast', 4000);
}
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount || 0);
}
function formatNumber(num) {
    return new Intl.NumberFormat('vi-VN').format(num || 0);
}
function escapeHtml(text) {
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' };
    return text.replace(/[&<>"']/g, m => map[m]);
}
function escapeRegex(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}
function previewImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById('imagePreview');
    if (file && file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = e => {
            preview.innerHTML = `<img src="${e.target.result}" style="max-width: 200px; max-height: 200px; border-radius: 8px; margin-top: 10px;">`;
        };
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
}
// Nav handling helpers
function restoreSectionFromHash() {
    const hash = window.location.hash.substring(1);
    if (hash) {
        const sectionName = hash.split('?')[0].replace('-section', '');
        showSection(sectionName);
    } else showSection('dashboard');
}
function showSection(name) {
    document.querySelectorAll('.admin-section').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

    const target = document.getElementById(name + '-section');
    const nav = document.querySelector(`.nav-item[data-section="${name}"]`);

    if (target) target.classList.add('active');
    if (nav) nav.classList.add('active');

    if (name === 'products' && target && !target.dataset.ajaxLoaded) loadProductsPage(1);
    if (name === 'orders' && target && !target.dataset.ajaxLoaded) {
        loadOrdersPage(1);
    }
}

function handleHashChange() { restoreSectionFromHash(); }
function initNavigation() {
    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const section = this.getAttribute('data-section');
            window.location.hash = section + '-section';
        });
    });
}
function initFilterForm() { /* Logic filter form nếu cần */ }
// Hàm xử lý bật/tắt banner
function toggleBannerStatus(checkboxElement) {
    const bannerId = checkboxElement.getAttribute('data-id');
    const isChecked = checkboxElement.checked; // Trả về true hoặc false
    // Gửi AJAX request
    const params = new URLSearchParams();
    params.append('action', 'toggleStatus');
    params.append('id', bannerId);
    params.append('status', isChecked);
    fetch('admin/banners', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: params
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showToast(isChecked ? "Đã bật hiển thị banner" : "Đã ẩn banner", "success");
                console.log("Update success: ID " + bannerId + " -> " + isChecked);
            } else {
                // Nếu lỗi server, revert trạng thái nút switch về như cũ
                checkboxElement.checked = !isChecked;
                showToast("Lỗi: " + data.message, "error");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            checkboxElement.checked = !isChecked; // Revert
            showToast("Lỗi kết nối server", "error");
        });
}
// Hàm hiển thị thông báo Toast đơn giản
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    if(toast) {
        toast.textContent = message;
        toast.className = 'toast show ' + type;
        setTimeout(() => { toast.className = toast.className.replace('show', ''); }, 3000);
    } else {
        alert(message);
    }
}
// Confirm Dialog
function showConfirmDialog(title, msg, sub, onConfirm) {
    if(confirm(`${title}\n${msg}`)) onConfirm();
}
/**
 * Hiển thị form thêm banner
 */
function showAddBannerForm() {
    const overlay = document.getElementById('bannerModalOverlay');
    const modal = document.getElementById('bannerModal');

    // Reset form
    document.getElementById('bannerForm').reset();
    document.getElementById('bannerImagePreview').innerHTML = '';

    // Show modal
    setTimeout(function() {
        overlay.classList.add('show');
        modal.classList.add('show');
    }, 10);
}

/**
 * Đóng modal thêm banner
 */
function closeBannerModal() {
    const overlay = document.getElementById('bannerModalOverlay');
    const modal = document.getElementById('bannerModal');

    overlay.classList.remove('show');
    modal.classList.remove('show');

    // Reset form sau khi đóng
    setTimeout(function() {
        document.getElementById('bannerForm').reset();
        document.getElementById('bannerImagePreview').innerHTML = '';
    }, 300);
}

/**
 * Preview ảnh banner trước khi upload
 */
function previewBannerImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById('bannerImagePreview');

    if (file) {
        // Validate size
        if (file.size > 10 * 1024 * 1024) {
            showToast('Kích thước file không được vượt quá 10MB', 'error');
            event.target.value = '';
            preview.innerHTML = '';
            return;
        }

        // Validate type
        const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
        if (!validTypes.includes(file.type)) {
            showToast('Chỉ chấp nhận file ảnh (JPG, PNG, GIF, WEBP)', 'error');
            event.target.value = '';
            preview.innerHTML = '';
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            preview.innerHTML = '<img src="' + e.target.result + '" alt="Preview" style="max-width: 100%; max-height: 300px; border-radius: 8px; margin-top: 10px;">';
        };
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
}

/**
 * Submit form thêm banner
 */
function submitBannerForm() {
    const form = document.getElementById('bannerForm');
    const saveBtn = document.getElementById('saveBannerBtn');
    const formData = new FormData(form);

    // Validate
    const title = document.getElementById('bannerTitle').value.trim();
    const imageFile = document.getElementById('bannerImage').files[0];

    if (!title) {
        showToast('Vui lòng nhập tiêu đề banner', 'error');
        return;
    }

    if (!imageFile) {
        showToast('Vui lòng chọn hình ảnh banner', 'error');
        return;
    }

    // Disable button
    saveBtn.disabled = true;
    saveBtn.innerHTML = '⏳ Đang xử lý...';

    const contextPath = window.location.pathname.split('/')[1];
    const url = '/' + contextPath + '/admin/api/banner/add';

    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showToast(data.message, 'success');
                closeBannerModal();
                // Reload page sau 1s
                setTimeout(function() {
                    window.location.reload();
                }, 1000);
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi thêm banner', 'error');
        })
        .finally(function() {
            saveBtn.disabled = false;
            saveBtn.innerHTML = '💾 Lưu Banner';
        });
}

/**
 * Xóa banner với confirm
 */
function deleteBanner(bannerId, bannerTitle) {
    showConfirmDialog(
        'Xác nhận xóa banner',
        `Bạn có chắc chắn muốn xóa banner "${bannerTitle}"?`,
        'Hành động này không thể hoàn tác!',
        function() {
            performDeleteBanner(bannerId);
        }
    );
}

/**
 * Thực hiện xóa banner qua AJAX
 */
function performDeleteBanner(bannerId) {
    console.log('️ Deleting banner ID:', bannerId);

    const contextPath = window.location.pathname.split('/')[1];
    const url = '/' + contextPath + '/admin/banners';

    // Dùng URLSearchParams thay vì FormData
    const params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', bannerId);

    console.log(' Parameters:', params.toString());
    console.log(' Sending POST request to:', url);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params.toString()
    })
        .then(response => {
            console.log('📡 Response status:', response.status);
            return response.json();
        })
        .then(data => {
            console.log(' Response data:', data);

            if (data.success) {
                showToast(data.message || 'Xóa banner thành công!', 'success');

                const row = document.getElementById('banner-row-' + bannerId);
                if (row) {
                    row.style.opacity = '0';
                    row.style.transition = 'opacity 0.3s';
                    setTimeout(function() {
                        row.remove();

                        const tbody = document.getElementById('bannerTableBody');
                        if (tbody && tbody.children.length === 0) {
                            tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;padding:20px;">Không có banner nào</td></tr>';
                        }
                    }, 300);
                }
            } else {
                showToast(data.message || 'Lỗi khi xóa banner', 'error');
            }
        })
        .catch(error => {
            console.error(' Error:', error);
            showToast('Có lỗi xảy ra khi xóa banner', 'error');
        });
}
let currentOrderPage         = 1;
let ordersPerPage            = 10;
let currentOrderStatusFilter = 'ALL';

function loadOrdersPage(page) {
    const tbody = document.getElementById('orderTableBody');
    tbody.innerHTML = `<tr><td colspan="7" style="text-align:center; padding:2rem;">
                            Đang tải...</td></tr>`;

    const statusParam = currentOrderStatusFilter !== 'ALL'
        ? `&status=${currentOrderStatusFilter}` : '';

    fetch(`/${CONTEXT_PATH}/admin/api/orders?action=list&page=${page}&pageSize=${ordersPerPage}${statusParam}`)
        .then(r => r.json())
        .then(data => {
            if (data.success) {
                renderOrdersTable(data.orders);
                renderOrderPagination(data.pagination);
                currentOrderPage = data.pagination.currentPage;
                document.getElementById('orders-section').dataset.ajaxLoaded = 'true';
            } else {
                tbody.innerHTML = `<tr><td colspan="7" style="text-align:center;
                    padding:2rem; color:#dc2626;">${data.message}</td></tr>`;
            }
        })
        .catch(() => {
            tbody.innerHTML = `<tr><td colspan="7" style="text-align:center;
                padding:2rem; color:#dc2626;">Không thể tải dữ liệu</td></tr>`;
        });
}

function renderOrdersTable(orders) {
    const tbody = document.getElementById('orderTableBody');

    if (orders.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" style="text-align:center;
            padding:2rem; color:#6b7280;">Không có đơn hàng nào</td></tr>`;
        return;
    }

    tbody.innerHTML = orders.map(o => {

        const statusMap = {
            PENDING:   ['pending',    'Chờ xử lý'],
            CONFIRMED: ['processing', 'Đã xác nhận'],
            SHIPPING:  ['shipping',   'Đang giao'],
            DELIVERED: ['completed',  'Hoàn thành'],
            CANCELLED: ['cancelled',  'Đã hủy']
        };
        const [sCls, sLabel] = statusMap[o.order_status] || ['', o.order_status];
        const statusBadge = `<span class="status-badge ${sCls}">${sLabel}</span>`;

        let sigBadge;
        if (!o.signature) {
            sigBadge = `<span class="status-badge pending">Chưa ký</span>`;
        } else if (o.is_verified === 1) {
            sigBadge = `<span class="status-badge completed">✓ Hợp lệ</span>`;
        } else {
            sigBadge = `<span class="status-badge cancelled">✗ Sai / Bị chỉnh</span>`;
        }

        // Nút duyệt
        const canApprove = o.order_status === 'PENDING' && o.is_verified === 1;
        const btnApprove = canApprove
            ? `<button class="btn-edit"
                   onclick="approveOrder(${o.id}, '${escapeHtml(o.order_code)}')">
                    Duyệt
               </button>`
            : '';

        return `
            <tr>
                <td><span class="order-code">${o.order_code || 'N/A'}</span></td>
                <td>${o.customer_name || o.shipping_name || 'Khách vãng lai'}</td>
                <td><strong>${formatCurrency(o.total_amount || 0)}</strong></td>
                <td>${statusBadge}</td>
                <td>${sigBadge}</td>
                <td>${formatDateTime(o.created_at)}</td>
                <td>${btnApprove}</td>
            </tr>`;
    }).join('');
}

function renderOrderPagination(pagination) {
    const container = document.getElementById('orderPagination');
    if (!container) return;

    const { currentPage, totalPages } = pagination;

    if (totalPages <= 1) {
        container.innerHTML = '';
        return;
    }

    let html = '<div class="pagination-controls">';
    html += `<button class="pagination-btn"
                 onclick="changeOrderPage(${currentPage - 1})"
                 ${currentPage === 1 ? 'disabled' : ''}>◀ Trước</button>`;

    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            html += `<button class="pagination-btn ${i === currentPage ? 'active' : ''}"
                         onclick="changeOrderPage(${i})">${i}</button>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            html += '<span class="pagination-dots">...</span>';
        }
    }

    html += `<button class="pagination-btn"
                 onclick="changeOrderPage(${currentPage + 1})"
                 ${currentPage === totalPages ? 'disabled' : ''}>Sau ▶</button>`;
    html += '</div>';
    container.innerHTML = html;
}

function changeOrderPage(page) {
    if (page < 1) return;
    loadOrdersPage(page);
    document.getElementById('orders-section')
        .scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function approveOrder(orderId, orderCode) {
    if (!confirm(`Duyệt đơn hàng "${orderCode}"?`)) return;

    const fd = new FormData();
    fd.append('action',    'updateStatus');
    fd.append('orderId',   orderId);
    fd.append('newStatus', 'CONFIRMED');

    fetch(`/${CONTEXT_PATH}/admin/api/orders`, { method: 'POST', body: fd })
        .then(r => r.json())
        .then(data => {
            if (data.success) {
                showToast(data.message || 'Đã duyệt đơn hàng!', 'success');
            } else {
                showToast(data.message || 'Không thể duyệt đơn hàng', 'error');
            }
            setTimeout(() => loadOrdersPage(currentOrderPage), 800);
        })
        .catch(() => showToast('Có lỗi xảy ra', 'error'));
}
function formatDateTime(timestamp) {
    if (!timestamp) return 'N/A';
    const date = new Date(timestamp);
    const day     = String(date.getDate()).padStart(2, '0');
    const month   = String(date.getMonth() + 1).padStart(2, '0');
    const year    = date.getFullYear();
    const hours   = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${day}/${month}/${year} ${hours}:${minutes}`;
}