function updateQuantity(productId, change) {
    if (!productId) {
        console.error('Invalid productId');
        return;
    }

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `${getContextPath()}/cart`;

    // Tạo hidden inputs
    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'update';
    form.appendChild(actionInput);

    const productIdInput = document.createElement('input');
    productIdInput.type = 'hidden';
    productIdInput.name = 'productId';
    productIdInput.value = productId;
    form.appendChild(productIdInput);

    const quantityInput = document.createElement('input');
    quantityInput.type = 'hidden';
    quantityInput.name = 'quantity';
    quantityInput.value = change;  // Gửi change (+1 hoặc -1)
    form.appendChild(quantityInput);

    document.body.appendChild(form);
    form.submit();
}

function removeItem(productId) {
    if (!productId) {
        console.error('Invalid productId');
        return;
    }

    if (!confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
        return;
    }

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `${getContextPath()}/cart`;

    // Tạo hidden inputs
    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'remove';
    form.appendChild(actionInput);

    const productIdInput = document.createElement('input');
    productIdInput.type = 'hidden';
    productIdInput.name = 'productId';
    productIdInput.value = productId;
    form.appendChild(productIdInput);

    document.body.appendChild(form);
    form.submit();
}

// Hàm helper lấy context path
function getContextPath() {
    let paths = location.pathname.split('/');
    if (paths.length > 1 && paths[1]) {
        return '/' + paths[1];
    }
    return '';
}