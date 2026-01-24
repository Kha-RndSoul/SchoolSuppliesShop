(function () {
    function q(id) { return document.getElementById(id); }
    function formatCurrency(n) {
        // n is integer (VNĐ)
        return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "₫";
    }
    function parseIntSafe(v) {
        var n = parseInt(v, 10);
        return isNaN(n) ? 0 : n;
    }

    var subtotalEl = q("subtotal");
    var shippingFeeEl = q("shippingFee");
    var discountRowEl = q("discountRow");
    var discountAmtEl = q("discountAmount");
    var totalEl = q("total");
    var appliedCouponCodeEl = q("appliedCouponCode");
    var couponHidden = q("couponCodeHidden");
    var couponMessage = q("couponMessage");
    var couponListEl = document.getElementById("couponList");

    function getSubtotal() {
        return parseIntSafe(subtotalEl.getAttribute("data-value"));
    }
    function getShippingFee() {
        return parseIntSafe(shippingFeeEl.getAttribute("data-value"));
    }
    function getDiscount() {
        return parseIntSafe(discountAmtEl.getAttribute("data-value"));
    }

    function recalcTotal() {
        var sub = getSubtotal();
        var ship = getShippingFee();
        var disc = getDiscount();
        var total = sub + ship - disc;
        if (total < 0) total = 0;
        totalEl.textContent = formatCurrency(total);
    }

    function initShippingRadios() {
        var radios = document.querySelectorAll('input[name="shipping"]');
        radios.forEach(function (r) {
            r.addEventListener("change", function () {
                var fee = 0;
                try {
                    fee = parseIntSafe(r.getAttribute("data-fee"));
                } catch (e) { fee = r.value === 'express' ? 50000 : 25000; }
                shippingFeeEl.setAttribute("data-value", fee);
                shippingFeeEl.textContent = formatCurrency(fee);
                recalcTotal();
            });
        });
    }

    function computeDiscountFromCoupon(coupon, orderAmount) {
        var discount = 0;
        if (!coupon || orderAmount <= 0) return 0;
        var type = (coupon.type || "").toUpperCase();
        var val = Number(coupon.value) || 0;
        if (type === "PERCENTAGE") {
            discount = Math.round(orderAmount * (val / 100));
        } else if (type === "FIXED_AMOUNT") {
            discount = Math.round(val);
        } else {
            discount = 0;
        }
        if (discount > orderAmount) discount = orderAmount;
        return discount;
    }

    function initCouponCards() {
        if (!couponListEl) return;
        var cards = couponListEl.querySelectorAll('.coupon-card');
        var subtotal = getSubtotal();

        cards.forEach(function (card) {
            var minOrder = parseIntSafe(card.getAttribute('data-min-order'));
            var btn = card.querySelector('.btn-select-coupon');
            // mark disabled if subtotal < minOrder
            if (minOrder > 0 && subtotal < minOrder) {
                card.classList.add('disabled');
                if (btn) btn.textContent = 'Yêu cầu';
            } else {
                card.classList.remove('disabled');
                if (btn) btn.textContent = 'Chọn';
            }

            card.addEventListener('click', function (e) {
                if (card.classList.contains('disabled')) return;
                selectCouponCard(card);
            });

            if (btn) {
                btn.addEventListener('click', function (e) {
                    e.stopPropagation();
                    if (card.classList.contains('disabled')) return;
                    selectCouponCard(card);
                });
            }
        });
    }

    function clearSelectedCouponUI() {
        var selected = document.querySelectorAll('.coupon-card.selected');
        selected.forEach(function (c) { c.classList.remove('selected'); });
        discountAmtEl.setAttribute('data-value', 0);
        discountAmtEl.textContent = "-0₫";
        discountRowEl.style.display = "none";
        appliedCouponCodeEl.textContent = "";
        couponHidden.value = "";
        if (couponMessage) {
            couponMessage.style.color = "#064e3b";
            couponMessage.textContent = "";
        }
        recalcTotal();
    }

    function selectCouponCard(card) {
        // remove previous selection
        var prev = document.querySelector('.coupon-card.selected');
        if (prev) prev.classList.remove('selected');

        card.classList.add('selected');

        var code = card.getAttribute('data-code') || "";
        var dtype = card.getAttribute('data-discount-type') || "";
        var dval = card.getAttribute('data-discount-value') || "0";
        var minOrder = parseIntSafe(card.getAttribute('data-min-order'));

        var orderAmount = getSubtotal();

        // check minOrder again
        if (minOrder > 0 && orderAmount < minOrder) {
            // should not happen because disabled, but guard
            if (couponMessage) {
                couponMessage.style.color = "#b91c1c";
                couponMessage.textContent = "Đơn hàng chưa đủ điều kiện cho mã này.";
            }
            return;
        }

        var coupon = { type: dtype, value: Number(dval) };
        var discount = computeDiscountFromCoupon(coupon, orderAmount);
        discountAmtEl.setAttribute('data-value', discount);
        discountAmtEl.textContent = "-" + formatCurrency(discount);
        discountRowEl.style.display = "flex";
        appliedCouponCodeEl.textContent = code;
        couponHidden.value = code;

        if (couponMessage) {
            couponMessage.style.color = "#065f46";
            if (discount > 0) {
                couponMessage.textContent = "Áp dụng mã " + code + ": giảm " + formatCurrency(discount);
            } else {
                couponMessage.textContent = "Mã hợp lệ nhưng không giảm thêm.";
            }
        }
        recalcTotal();
    }

    function validateCouponAjax(code, cb) {
        if (!code || !cb) return;
        var orderAmount = getSubtotal();
        var url = CONTEXT_PATH + "/checkout?validateCoupon=1&code=" + encodeURIComponent(code) + "&orderAmount=" + orderAmount;
        fetch(url, { credentials: 'same-origin' })
            .then(function (resp) { return resp.json(); })
            .then(function (data) {
                cb(null, data);
            })
            .catch(function (err) {
                cb(err);
            });
    }

    // Init
    document.addEventListener("DOMContentLoaded", function () {

        shippingFeeEl.textContent = formatCurrency(parseIntSafe(shippingFeeEl.getAttribute("data-value")));
        subtotalEl.textContent = formatCurrency(parseIntSafe(subtotalEl.getAttribute("data-value")));

        initShippingRadios();
        initCouponCards();


        document.addEventListener('click', function (e) {
            var couponSection = document.querySelector('.coupon-list');
            if (!couponSection) return;
            if (!couponSection.contains(e.target)) {
            }
        });

        recalcTotal();
    });
})();