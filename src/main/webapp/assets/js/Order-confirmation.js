(function(){
    const root = document.getElementById('root');
    if(!root) return;
    const p = new URLSearchParams(location.search);
    const id = p.get('orderId');

    document.getElementById('printBtn')?.addEventListener('click', ()=> alert('In hóa đơn thành công'));
})();
