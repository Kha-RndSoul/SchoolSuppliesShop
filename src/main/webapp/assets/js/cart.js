// javascript
(() => {
    const KEY = 'cart';
    const listEl = document.getElementById('list');
    const emptyBlock = document.getElementById('emptyBlock');
    const countEl = document.getElementById('count');
    const subTotalEl = document.getElementById('subTotal');
    const grandTotalEl = document.getElementById('grandTotal');
    const checkoutBtn = document.getElementById('checkoutBtn');
    const demoAdd = document.getElementById('demoAdd');

    function loadCart() {
        try {
            const raw = localStorage.getItem(KEY);
            return raw ? JSON.parse(raw) : [];
        } catch {
            return [];
        }
    }

    function saveCart(cart) {
        localStorage.setItem(KEY, JSON.stringify(cart));
    }

    function formatVND(n) {
        return n.toLocaleString('vi-VN') + '₫';
    }

    function calcTotals(cart) {
        const subtotal = cart.reduce((s, it) => s + it.price * it.qty, 0);
        // Shipping not calculated here; grandTotal = subtotal
        return { subtotal, grandTotal: subtotal };
    }

    function render() {
        const cart = loadCart();
        if (!cart.length) {
            emptyBlock.removeAttribute('hidden');
            emptyBlock.setAttribute('aria-hidden', 'false');
            listEl.hidden = true;
            countEl.textContent = '0';
            subTotalEl.textContent = formatVND(0);
            grandTotalEl.textContent = formatVND(0);
            checkoutBtn.disabled = true;
            return;
        }

        emptyBlock.hidden = true;
        emptyBlock.setAttribute('aria-hidden', 'true');
        listEl.hidden = false;
        listEl.innerHTML = '';

        cart.forEach(item => {
            const row = document.createElement('div');
            row.className = 'cart-item';
            row.dataset.id = item.id;
            row.innerHTML = `
        <div class="item-left">
          <div class="title">${escapeHtml(item.name)}</div>
          <div class="price">${formatVND(item.price)}</div>
        </div>
        <div class="item-right">
          <div class="qty">
            <button class="dec" aria-label="Giảm">-</button>
            <span class="qty-val" aria-live="polite">${item.qty}</span>
            <button class="inc" aria-label="Tăng">+</button>
          </div>
          <div class="line-total">${formatVND(item.price * item.qty)}</div>
          <button class="remove" aria-label="Xóa">X</button>
        </div>
      `;
            // event handlers
            row.querySelector('.inc').addEventListener('click', () => changeQty(item.id, +1));
            row.querySelector('.dec').addEventListener('click', () => changeQty(item.id, -1));
            row.querySelector('.remove').addEventListener('click', () => removeItem(item.id));
            listEl.appendChild(row);
        });

        countEl.textContent = cart.reduce((c, it) => c + it.qty, 0);
        const totals = calcTotals(cart);
        subTotalEl.textContent = formatVND(totals.subtotal);
        grandTotalEl.textContent = formatVND(totals.grandTotal);
        checkoutBtn.disabled = false;
    }

    function changeQty(id, delta) {
        const cart = loadCart();
        const idx = cart.findIndex(i => i.id === id);
        if (idx === -1) return;
        cart[idx].qty = Math.max(1, cart[idx].qty + delta);
        saveCart(cart);
        render();
    }

    function removeItem(id) {
        let cart = loadCart();
        cart = cart.filter(i => i.id !== id);
        saveCart(cart);
        render();
    }

    function addItem(item) {
        const cart = loadCart();
        const existing = cart.find(i => i.id === item.id);
        if (existing) {
            existing.qty += item.qty;
        } else {
            cart.push(item);
        }
        saveCart(cart);
        render();
    }

    // demo Add handler
    demoAdd.addEventListener('click', () => {
        const demo = {
            id: 'demo-' + Date.now(),
            name: 'Món demo',
            price: 50000,
            qty: 1
        };
        addItem(demo);
    });

    // checkout handler
    checkoutBtn.addEventListener('click', () => {
        const cart = loadCart();
        if (!cart.length) return;
        // replace with real checkout flow
        alert('Proceed to checkout — total: ' + formatVND(calcTotals(cart).grandTotal));
    });

    // create "Tiếp tục mua hàng" button if not present
    function ensureContinueButton() {
        if (document.getElementById('continueBtn')) return;
        const container = document.querySelector('.sum-box') || document.body;
        const btn = document.createElement('button');
        btn.id = 'continueBtn';
        btn.type = 'button';
        btn.className = 'btn';
        btn.textContent = 'Tiếp tục mua hàng';
        btn.style.width = '100%';
        btn.style.marginTop = '8px';
        btn.addEventListener('click', () => {
            // go to home
            window.location.href = '/';
        });
        container.appendChild(btn);
    }

    // minimal HTML escaping for names
    function escapeHtml(s) {
        return String(s)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    // init
    ensureContinueButton();
    render();
})();