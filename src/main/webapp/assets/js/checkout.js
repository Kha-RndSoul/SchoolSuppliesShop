// assets/js/checkout.js — cập nhật subtotal/shipping/total từ localStorage.cart
(function(){
    const q = s => document.querySelector(s);
    const get = id => document.getElementById(id);
    const fmt = n => new Intl.NumberFormat('vi-VN').format(Number(n)||0) + ' ₫';

    function render(){
        const cart = JSON.parse(localStorage.getItem('cart') || '[]');
        const sub = cart.reduce((s,i)=> s + (Number(i.price)||0)*(Number(i.qty)||1), 0);
        const shippingMode = (q('input[name="shipping"]:checked') || {value:'standard'}).value;
        const ship = shippingMode === 'express' ? 50000 : 25000;
        get('subtotal').textContent = fmt(sub);
        get('shippingFee').textContent = fmt(ship);
        get('total').textContent = fmt(sub + ship);
    }

    // render lúc load và khi thay đổi radio shipping
    document.addEventListener('DOMContentLoaded', render);
    document.querySelectorAll('input[name="shipping"]').forEach(r => r.addEventListener('change', render));
})();
