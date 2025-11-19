// assets/js/checkout.js  (thay thế toàn bộ bằng đoạn này)
(function(){
    const CART_KEY = 'cart';
    const ORDERS_KEY = 'orders';
    const get = id => document.getElementById(id);

    document.addEventListener('DOMContentLoaded', () => {
        const form = get('checkoutForm');
        const placeBtn = get('placeOrderBtn');
        const orderReview = get('orderReview');
        const subtotalEl = get('subtotal');
        const shippingFeeEl = get('shippingFee');
        const totalEl = get('total');
        const errBox = get('checkoutError');
        if(!form || !placeBtn) return;

        const fmt = n => Number(n||0).toLocaleString('vi-VN') + '₫';
        const loadCart = () => JSON.parse(localStorage.getItem(CART_KEY) || '[]');
        const subtotal = items => (items||[]).reduce((s,i)=> s + (Number(i.price)||0)*(Number(i.qty)||0),0);
        const shipFee = mode => mode === 'express' ? 50000 : 25000;

        // prevent inline navigation if any
        placeBtn.type = 'button';

        // helper: create/return inline error element after input
        function ensureErr(input){
            if(!input) return null;
            let el = input.nextElementSibling;
            if(el && el.classList && el.classList.contains('field-error')) return el;
            el = document.createElement('div');
            el.className = 'field-error';
            el.style.color = '#b00020';
            el.style.fontSize = '0.9rem';
            el.style.marginTop = '6px';
            input.parentNode.insertBefore(el, input.nextSibling);
            return el;
        }
        function setErr(input, msg){
            const e = ensureErr(input);
            if(e) e.textContent = msg || '';
            if(msg) input.classList && input.classList.add('invalid'); else input.classList && input.classList.remove('invalid');
        }

        // validators
        const validName = v => String(v||'').trim().length >= 2;
        const validPhone = v => /^((\+84|0)\d{9,10})$/.test(String(v||'').trim());
        const validEmail = v => !v || /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(v||'').trim());
        const validAddress = v => String(v||'').trim().length >= 6;

        // render order review & totals
        function escapeHtml(s){ return String(s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;'); }
        function render(){
            const cart = loadCart();
            if(!orderReview) return;
            if(!cart.length){
                orderReview.innerHTML = '<div class="empty">Giỏ hàng trống.</div>';
                subtotalEl && (subtotalEl.textContent = fmt(0));
                shippingFeeEl && (shippingFeeEl.textContent = fmt(0));
                totalEl && (totalEl.textContent = fmt(0));
                return;
            }
            orderReview.innerHTML = cart.map(i=>`<div>${escapeHtml(i.name)} x ${i.qty} — ${fmt((i.price||0)*i.qty)}</div>`).join('');
            const sub = subtotal(cart);
            const mode = document.querySelector('input[name="shipping"]:checked')?.value || 'standard';
            const ship = shipFee(mode);
            subtotalEl && (subtotalEl.textContent = fmt(sub));
            shippingFeeEl && (shippingFeeEl.textContent = fmt(ship));
            totalEl && (totalEl.textContent = fmt(sub + ship));
        }

        // blur validation
        const nameInput = get('name');
        const phoneInput = get('phone');
        const emailInput = get('email');
        const addressInput = get('address');
        [nameInput, phoneInput, emailInput, addressInput].forEach(inp=>{
            if(!inp) return;
            inp.addEventListener('blur', ()=>{
                if(inp === nameInput) setErr(inp, validName(inp.value) ? '' : 'Tên phải ít nhất 2 ký tự.');
                if(inp === phoneInput) setErr(inp, validPhone(inp.value) ? '' : 'Số điện thoại không hợp lệ. Ví dụ: 0912345678 hoặc +84912345678');
                if(inp === emailInput) setErr(inp, validEmail(inp.value) ? '' : 'Email không đúng định dạng.');
                if(inp === addressInput) setErr(inp, validAddress(inp.value) ? '' : 'Địa chỉ quá ngắn (>=6 ký tự).');
            });
        });

        // submit handler
        let submitting = false;
        placeBtn.addEventListener('click', ()=>{
            if(submitting) return;
            const cart = loadCart();
            if(!cart.length){ alert('Giỏ hàng trống — không thể đặt hàng.'); return; }

            // clear prev errors
            setErr(nameInput,''); setErr(phoneInput,''); setErr(emailInput,''); setErr(addressInput,'');
            if(errBox) errBox.textContent = '';

            // validate
            let ok = true;
            if(!validName(nameInput.value)){ setErr(nameInput,'Tên phải ít nhất 2 ký tự.'); ok = false; }
            if(!validPhone(phoneInput.value)){ setErr(phoneInput,'Số điện thoại không hợp lệ.'); ok = false; }
            if(!validEmail(emailInput.value)){ setErr(emailInput,'Email không đúng định dạng.'); ok = false; }
            if(!validAddress(addressInput.value)){ setErr(addressInput,'Địa chỉ quá ngắn (>=6 ký tự).'); ok = false; }
            if(!ok){
                if(errBox){ errBox.textContent = 'Vui lòng sửa các trường có lỗi.'; errBox.style.color = '#b00020'; }
                return;
            }

            // create order (mock)
            submitting = true;
            placeBtn.disabled = true;

            const shippingMode = document.querySelector('input[name="shipping"]:checked')?.value || 'standard';
            const payment = document.querySelector('input[name="payment"]:checked')?.value || 'cod';
            const sub = subtotal(cart);
            const ship = shipFee(shippingMode);

            const order = {
                orderId: 'ORD' + Date.now(),
                createdAt: new Date().toISOString(),
                items: cart,
                subtotal: sub,
                shippingFee: ship,
                total: sub + ship,
                payment,
                shippingMethod: shippingMode,
                customer: {
                    name: nameInput.value.trim(),
                    phone: phoneInput.value.trim(),
                    email: emailInput.value.trim(),
                    address: addressInput.value.trim()
                }
            };

            const orders = JSON.parse(localStorage.getItem(ORDERS_KEY) || '[]');
            orders.push(order);
            localStorage.setItem(ORDERS_KEY, JSON.stringify(orders));

            localStorage.removeItem(CART_KEY);
            location.href = `order-confirmation.html?orderId=${encodeURIComponent(order.orderId)}`;
        });

        // recalc totals when shipping changes
        document.querySelectorAll('input[name="shipping"]').forEach(r => r.addEventListener('change', render));

        // initial render
        render();
    });
})();
