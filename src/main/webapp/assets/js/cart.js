(() => {
    const CART_KEY = 'cart';
    const listEl = document.getElementById('list');
    const emptyBlock = document.getElementById('emptyBlock');
    const subTotalEl = document.getElementById('subTotal');
    const demoBtn = document.getElementById('demoAdd');
    const checkoutBtn = document.getElementById('checkoutBtn');

    const fmt = n => (Number(n)||0).toLocaleString('vi-VN') + '₫';

    // utils đơn giản
    const load = () => {
        try { return JSON.parse(localStorage.getItem(CART_KEY) || '[]'); }
        catch { return []; }
    };
    const save = arr => { localStorage.setItem(CART_KEY, JSON.stringify(arr)); };

    const genId = (base='demo') => `${base}-${Date.now().toString(36)}-${Math.floor(Math.random()*9000+1000).toString(36)}`;

    function render() {
        const cart = load();
        if (!cart.length) {
            emptyBlock && (emptyBlock.hidden = false);
            if (listEl) listEl.innerHTML = '';
            if (subTotalEl) subTotalEl.textContent = fmt(0);
            if (checkoutBtn) checkoutBtn.disabled = true;
            return;
        }
        emptyBlock && (emptyBlock.hidden = true);
        listEl.innerHTML = '';
        cart.forEach(item => {
            const row = document.createElement('div');
            row.className = 'cart-item';
            row.dataset.id = item.id;
            row.innerHTML = `
        <div class="item-left">
          <div class="title">${item.name}</div>
          <div class="price">${fmt(item.price)}</div>
        </div>
        <div class="item-right">
          <div class="qty">
            <button class="dec">-</button>
            <span class="qty-val">${item.qty}</span>
            <button class="inc">+</button>
          </div>
          <div class="line-total">${fmt(item.price * item.qty)}</div>
          <button class="remove">X</button>
        </div>
      `;
            row.querySelector('.inc').addEventListener('click', () => changeQty(item.id, +1));
            row.querySelector('.dec').addEventListener('click', () => changeQty(item.id, -1));
            row.querySelector('.remove').addEventListener('click', () => removeItem(item.id));
            listEl.appendChild(row);
        });

        const subtotal = cart.reduce((s, it) => s + (Number(it.price)||0) * (Number(it.qty)||0), 0);
        if (subTotalEl) subTotalEl.textContent = fmt(subtotal);
        if (checkoutBtn) checkoutBtn.disabled = false;
    }

    function changeQty(id, delta) {
        const cart = load();
        const i = cart.findIndex(x => String(x.id) === String(id));
        if (i === -1) return;
        cart[i].qty = Math.max(1, (Number(cart[i].qty)||1) + delta);
        save(cart);
        render();
    }

    function removeItem(id) {
        const cart = load().filter(x => String(x.id) !== String(id));
        save(cart);
        render();
    }

    function addDemoItem() {
        const id = genId('demo-pencil');
        const item = { id, name: `Bút chì HB (demo)`, price: 12000, qty: 1 };
        const cart = load();
        cart.push(item); // mỗi lần push 1 dòng mới (id khác)
        save(cart);
        render();
    }

    // checkout button: chuyển sang checkout nếu có hàng
    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', () => {
            const cart = load();
            if (!cart.length) {
                alert('Giỏ hàng trống — không thể thanh toán.');
                return;
            }
            // lưu canonical cart và chuyển trang
            save(cart);
            window.location.href = 'checkout.html';
        });
    }

    demoBtn && demoBtn.addEventListener('click', addDemoItem);

    // init
    render();

    // exposed for debug
    window.appCart = { load, save, addDemoItem, render };
})();
