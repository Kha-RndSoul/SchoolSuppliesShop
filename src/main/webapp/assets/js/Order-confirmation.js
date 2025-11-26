// order-confirmation.js — phiên bản siêu ngắn
(function(){
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const root = document.getElementById('root');
    const printBtn = document.getElementById('printBtn');

    const fmt = n => (Number(n)||0).toLocaleString('vi-VN') + '₫';

    // tạo mã đơn đơn giản
    const orderId = 'ORD-' + Date.now();

    // tính tổng
    const subtotal = cart.reduce((s,i)=> s + i.price * i.qty, 0);
    const shipping = 25000;
    const total = subtotal + shipping;

    // render HTML
    root.innerHTML = `
    <div class="card">
      <h2>Xác nhận đơn hàng</h2>
      <p>Mã đơn: <strong>${orderId}</strong></p>
      <p>Ngày tạo: ${new Date().toLocaleString()}</p>

      <div style="margin:12px 0;border-top:1px solid #ddd;padding-top:12px;">
        ${cart.map(i=>`
          <div style="display:flex;justify-content:space-between;margin-bottom:6px">
            <span>${i.name} (x${i.qty})</span>
            <span>${fmt(i.price * i.qty)}</span>
          </div>
        `).join('')}
      </div>

      <div style="border-top:1px solid #ddd;padding-top:12px;">
        <div style="display:flex;justify-content:space-between"><span>Tạm tính</span><span>${fmt(subtotal)}</span></div>
        <div style="display:flex;justify-content:space-between"><span>Phí vận chuyển</span><span>${fmt(shipping)}</span></div>
        <div style="display:flex;justify-content:space-between;font-weight:bold"><span>Tổng</span><span>${fmt(total)}</span></div>
      </div>
    </div>
  `;

    // nút in hóa đơn → hiện thông báo
    printBtn.addEventListener('click', () => {
        alert('In hóa đơn thành công');
    });

})();
