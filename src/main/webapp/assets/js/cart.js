(() => {
    const KEY = 'cart';
    const ORDERS = 'orders';
    const listEl = document.getElementById('list');
    const emptyBlock = document.getElementById('emptyBlock');
    const countEl = document.getElementById('count');
    const subTotalEl = document.getElementById('subTotal');
    const shippingFeeEl = document.getElementById('shippingFee');
    const grandTotalEl = document.getElementById('grandTotal');
    const checkoutBtn = document.getElementById('checkoutBtn');
    const continueBtn = document.getElementById('continueBtn');
    const demoAdd = document.getElementById('demoAdd');
    const mainEl = document.getElementById('main') || document.body;

    const load = () => {
        try { return JSON.parse(localStorage.getItem(KEY) || '[]'); }
        catch { return []; }
    };
    const save = cart => {
        localStorage.setItem(KEY, JSON.stringify(cart));
        window.dispatchEvent(new CustomEvent('cart:updated', { detail: { items: cart } }));
    };
    const clear = () => {
        localStorage.removeItem(KEY);
        window.dispatchEvent(new CustomEvent('cart:updated', { detail: { items: [] } }));
    };
    const fmt = n => (Number(n)||0).toLocaleString('vi-VN') + '₫';
    const subtotalOf = items => (items||[]).reduce((s,it)=>s + (Number(it.price)||0)*(Number(it.qty)||0), 0);

    function escapeHtml(s){ return String(s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;').replace(/'/g,'&#39;'); }

    function render() {
        const cart = load();
        if (!cart.length) {
            if (emptyBlock) emptyBlock.hidden = false;
            if (listEl) listEl.innerHTML = '';
            if (subTotalEl) subTotalEl.textContent = fmt(0);
            if (shippingFeeEl) shippingFeeEl.textContent = fmt(0);
            if (grandTotalEl) grandTotalEl.textContent = fmt(0);
            if (checkoutBtn) checkoutBtn.disabled = true;
            if (countEl) countEl.textContent = '0';
            return;
        }
        if (emptyBlock) emptyBlock.hidden = true;
        listEl.innerHTML = '';
        cart.forEach(it => {
            const title = escapeHtml(it.name || it.title || 'Sản phẩm');
            const price = Number(it.price) || 0;
            const qty = Number(it.qty) || 1;
            const row = document.createElement('div');
            row.className = 'cart-item';
            row.dataset.id = it.id;
            row.innerHTML = `
        <div class="item-left">
          <div class="title">${title}</div>
          <div class="price">${fmt(price)}</div>
        </div>
        <div class="item-right">
          <div class="qty">
            <button class="dec" aria-label="Giảm">-</button>
            <span class="qty-val">${qty}</span>
            <button class="inc" aria-label="Tăng">+</button>
            
          </div>
          <div class="line-total">${fmt(price * qty)}</div>
          <button class="remove" aria-label="Xóa">X</button>
        </div>`;
            row.querySelector('.inc').addEventListener('click', ()=>changeQty(it.id, +1));
            row.querySelector('.dec').addEventListener('click', ()=>changeQty(it.id, -1));
            row.querySelector('.remove').addEventListener('click', ()=>removeItem(it.id));
            listEl.appendChild(row);
        });
        const sub = subtotalOf(cart);
        if (subTotalEl) subTotalEl.textContent = fmt(sub);
        const shipping = sub > 0 ? 25000 : 0;
        if (shippingFeeEl) shippingFeeEl.textContent = fmt(shipping);
        if (grandTotalEl) grandTotalEl.textContent = fmt(sub + shipping);
        if (checkoutBtn) checkoutBtn.disabled = false;
        if (countEl) countEl.textContent = String(cart.reduce((c,i)=>c + (Number(i.qty)||0),0));
    }

    function changeQty(id, delta) {
        const cart = load();
        const idx = cart.findIndex(i => String(i.id) === String(id));
        if (idx === -1) return;
        cart[idx].qty = Math.max(1, (Number(cart[idx].qty)||1) + delta);
        save(cart);
        render();
    }
    function removeItem(id) {
        const cart = load().filter(i => String(i.id) !== String(id));
        save(cart);
        render();
    }
    function addDemoItem() {
        const demo = { id: 'demo-pencil', name: 'Bút chì HB (demo)', price: 12000, qty: 1 };
        const cart = load();
        const ex = cart.find(i => i.id === demo.id);
        if (ex) ex.qty = (Number(ex.qty)||0) + 1;
        else cart.push(demo);
        save(cart);
        render();
    }

    function genOrderId() {
        const dt = new Date().toISOString().replace(/[^0-9]/g,'').slice(0,14);
        return 'ORD-' + dt + '-' + Math.floor(Math.random()*9000+1000);
    }

    function showMsg(text, type='info') {
        // tạo banner bằng DOM nhưng style do CSS xử lý bởi class .msg-banner
        const old = document.getElementById('cartMsgBanner'); if (old) old.remove();
        const el = document.createElement('div');
        el.id = 'cartMsgBanner';
        el.className = 'msg-banner ' + (type || 'info');
        el.textContent = text;
        mainEl.insertBefore(el, mainEl.firstChild);
        setTimeout(()=>{ el.remove(); }, 3000);
    }


    function checkout() {
        const cart = load();
        if (!cart.length) {
            showMsg('Giỏ hàng trống — không thể thanh toán', 'error');
            return;
        }
        const order = { orderId: genOrderId(), createdAt: new Date().toISOString(), items: cart, subtotal: subtotalOf(cart) };
        const orders = JSON.parse(localStorage.getItem(ORDERS) || '[]');
        orders.push(order);
        localStorage.setItem(ORDERS, JSON.stringify(orders));
        showMsg('Thanh toán thành công — Mã đơn: ' + order.orderId, 'success');
        clear();
        render();
    }
    // bindings
    demoAdd && demoAdd.addEventListener('click', addDemoItem);
    checkoutBtn && checkoutBtn.addEventListener('click', checkout);
    continueBtn && continueBtn.addEventListener('click', ()=> window.location.href = 'products.html');

    // init
    render();

    // expose for debug
    window.appCart = { load, save, clear, render };
})();
