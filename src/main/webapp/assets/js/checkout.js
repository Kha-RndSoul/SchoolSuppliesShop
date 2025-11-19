(function(){
    const CART = 'cart';
    const ORDERS = 'orders';

    const el = id => document.getElementById(id);
    function loadCart(){
        return JSON.parse(localStorage.getItem(CART)||'[]');
    }
    function saveOrders(v){
        localStorage.setItem(ORDERS, JSON.stringify(v));
    }

    function fmt(n){
        return Number(n||0).toLocaleString('vi-VN') + '₫';
    }
    function subtotal(items){
        return items.reduce((s,i)=> s + (Number(i.price)||0)*(Number(i.qty)||0),0);
    }
    function shippingFee(mode){
        return mode==='express'?50000:25000;
    }

    document.addEventListener('DOMContentLoaded', function(){
        const review = el('orderReview');
        const sub = el('subtotal');
        const ship = el('shippingFee');
        const tot = el('total');
        const place = el('placeOrderBtn');
        const form = el('checkoutForm');
        if(!review || !place || !form) return;

        function render(){
            const cart = loadCart();
            if(!cart.length){ review.innerHTML = '<div class="empty">Giỏ hàng trống</div>'; place.disabled = true; sub.textContent = ship.textContent = tot.textContent = fmt(0); return; }
            review.innerHTML = cart.map(i=>`<div>${i.name} x ${i.qty} — ${fmt(i.price*i.qty)}</div>`).join('');
            const s = subtotal(cart);
            const mode = document.querySelector('input[name="shipping"]:checked')?.value || 'standard';
            const sh = shippingFee(mode);
            sub.textContent = fmt(s); ship.textContent = fmt(sh); tot.textContent = fmt(s+sh); place.disabled = false;
        }

        document.querySelectorAll('input[name="shipping"]').forEach(r=> r.onchange = render);
        render();

        let submitting = false;
        place.addEventListener('click', function(e){
            e.preventDefault();
            if(submitting) return;
            const cart = loadCart();
            if(!cart.length){ alert('Giỏ hàng trống'); return; }

            const name = el('name').value.trim();
            const phone = el('phone').value.trim();
            const address = el('address').value.trim();
            if(!name || !phone || !address){
                alert('Vui lòng điền tên, điện thoại và địa chỉ'); return;
            }

            submitting = true; place.disabled = true;

            const shippingMethod = document.querySelector('input[name="shipping"]:checked')?.value || 'standard';
            const payment = document.querySelector('input[name="payment"]:checked')?.value || 'cod';
            const shFee = shippingFee(shippingMethod);
            const subVal = subtotal(cart);
            const order = {
                orderId: 'ORD' + Date.now(),
                createdAt: new Date().toISOString(),
                items: cart,
                subtotal: subVal,
                shippingFee: shFee,
                total: subVal + shFee,
                shippingMethod, payment,
                customer: { name, phone, email: el('email') ? el('email').value.trim() : '', address }
            };
            const orders = JSON.parse(localStorage.getItem(ORDERS)||'[]');
            orders.push(order);
            saveOrders(orders);

            // clear cart
            localStorage.removeItem(CART);
            // notify other pages
            try{ window.dispatchEvent(new CustomEvent('cart:updated', { detail: { items: [] } })); }catch(e){}
            // redirect to confirmation
            location.href = `order-confirmation.html?orderId=${encodeURIComponent(order.orderId)}`;
        });
    });
})();
