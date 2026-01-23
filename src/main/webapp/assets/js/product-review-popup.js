
let currentRating = 0;
let isReviewSubmitting = false;
// Khởi tạo review popup khi DOM load
document.addEventListener('DOMContentLoaded', function() {
    initReviewStars();
    initReviewPopup();
});
// Khởi tạo các ngôi sao interactive trong phần summary
function initReviewStars() {
    const starBtns = document.querySelectorAll('.star-btn');

    starBtns.forEach((star, index) => {
        // Hover effect
        star.addEventListener('mouseenter', function() {
            highlightStars(index + 1, starBtns);
        });
        // Click để mở popup với rating được chọn
        star.addEventListener('click', function() {
            currentRating = index + 1;
            openReviewPopup(currentRating);
        });
    });
    // Reset khi rời chuột khỏi vùng stars
    const starsContainer = document.querySelector('.rating-stars-interactive');
    if (starsContainer) {
        starsContainer.addEventListener('mouseleave', function() {
            resetStars(starBtns);
        });
    }
}
// Highlight stars khi hover
function highlightStars(rating, stars) {
    stars.forEach((star, index) => {
        if (index < rating) {
            star.classList.add('hovered');
        } else {
            star.classList.remove('hovered');
        }
    });
}
// Reset stars về trạng thái ban đầu
function resetStars(stars) {
    stars.forEach(star => {
        star.classList.remove('hovered');
    });
}
// Mở popup review
function openReviewPopup(initialRating = 0) {
    const overlay = document.getElementById('reviewPopupOverlay');
    if (!overlay) {
        console.error('Review popup overlay not found!');
        return;
    }
    // Set rating ban đầu nếu có
    if (initialRating > 0) {
        const radioInput = document.getElementById(`popup-star${initialRating}`);
        if (radioInput) {
            radioInput.checked = true;
            highlightPopupStars(initialRating);
            updateRatingText(initialRating);
        }
    }
    // Reset form
    const form = document.getElementById('reviewPopupForm');
    if (form && initialRating === 0) {
        form.reset();
        highlightPopupStars(0);
    }

    // Hiển thị popup
    overlay.classList.add('active');
    document.body.style.overflow = 'hidden'; // Ngăn scroll trang chính
}

// Đóng popup review
function closeReviewPopup() {
    const overlay = document.getElementById('reviewPopupOverlay');
    if (overlay) {
        overlay.classList.remove('active');
        document.body.style.overflow = ''; // Cho phép scroll lại
    }
    // Reset form
    const form = document.getElementById('reviewPopupForm');
    if (form) {
        form.reset();
    }
    currentRating = 0;
    highlightPopupStars(0);
}
// Khởi tạo popup interactions
function initReviewPopup() {
    // Close khi click overlay
    const overlay = document.getElementById('reviewPopupOverlay');
    if (overlay) {
        overlay.addEventListener('click', function(e) {
            if (e.target === overlay) {
                closeReviewPopup();
            }
        });
    }
    // Close khi nhấn ESC
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeReviewPopup();
        }
    });

    // Xử lý stars trong popup
    const popupStars = document.querySelectorAll('.popup-stars label');
    const popupStarsArray = Array.from(popupStars);

    popupStars.forEach((label, index) => {
        label.addEventListener('mouseenter', function() {
            const rating = 5 - index;
            highlightPopupStars(rating);
        });
        label.addEventListener('click', function() {
            const rating = 5 - index;
            currentRating = rating;
            updateRatingText(currentRating);
        });
    });

    const popupStarsContainer = document.querySelector('.popup-stars');
    if (popupStarsContainer) {
        popupStarsContainer.addEventListener('mouseleave', function() {
            const checkedStar = document.querySelector('.popup-stars input[type="radio"]:checked');
            const rating = checkedStar ? parseInt(checkedStar.value) : 0;
            highlightPopupStars(rating);
        });
    }
    // Character counter cho textarea
    const textarea = document.getElementById('reviewComment');
    const charCounter = document.getElementById('charCounter');
    if (textarea && charCounter) {
        textarea.addEventListener('input', function() {
            charCounter.textContent = this.value.length;
        });
    }
    // Xử lý submit form
    const form = document.getElementById('reviewPopupForm');
    if (form) {
        form.addEventListener('submit', handleReviewSubmit);
    }
}
// Highlight stars trong popup (từ 1 đến rating)
function highlightPopupStars(rating) {
    const labels = document.querySelectorAll('.popup-stars label');
    labels.forEach((label, index) => {
        const starValue = 5 - index;
        if (starValue <= rating) {
            label.classList.add('hovered');
        } else {
            label.classList.remove('hovered');
        }
    });
}
// Cập nhật text mô tả rating
function updateRatingText(rating) {
    const textLabel = document.querySelector('.rating-text-label');
    if (!textLabel) return;

    const ratingTexts = {
        1: 'Rất tệ',
        2: 'Tệ',
        3: 'Bình thường',
        4: 'Tốt',
        5: 'Tuyệt vời'
    };
    textLabel.textContent = ratingTexts[rating] || 'Chọn đánh giá';
    textLabel.style.color = rating > 0 ? 'var(--primary-color)' : '#6b7280';
}
// Xử lý submit review
function handleReviewSubmit(e) {
    e.preventDefault();
    // Ngăn submit nhiều lần
    if (isReviewSubmitting) {
        return false;
    }
    // Lấy dữ liệu form
    const formData = new FormData(e.target);
    const rating = formData.get('rating');
    const comment = formData.get('comment').trim();
    // Validate
    if (!rating) {
        showReviewAlert('Vui lòng chọn số sao đánh giá!', 'error');
        return false;
    }

    if (comment.length < 10) {
        showReviewAlert('Nhận xét phải có ít nhất 10 ký tự!', 'error');
        return false;
    }
    // Hiển thị loading
    const submitBtn = document.querySelector('.btn-submit-review');
    if (submitBtn) {
        isReviewSubmitting = true;
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span>⏳</span> Đang gửi...';
    }
    // 1. Chuyển FormData sang URLSearchParams để Servlet đọc được bằng getParameter()
    const params = new URLSearchParams(formData);
    // 2. Gửi request kèm Header định danh AJAX
    fetch(e.target.action, {
        method: 'POST',
        body: params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
        .then(response => {
            if (response.ok) {
                // Thành công
                showReviewAlert(' Cảm ơn bạn đã đánh giá! Đánh giá của bạn sẽ được hiển thị sau khi được duyệt.', 'success');
                // Đóng popup sau 2 giây
                setTimeout(() => {
                    closeReviewPopup();
                    // Reload trang để cập nhật đánh giá
                    window.location.reload();
                }, 2000);
            } else {
                return response.text().then(text => {
                    throw new Error(text || 'Có lỗi xảy ra khi gửi đánh giá');
                });
            }
        })
        .catch(error => {
            console.error('Review submission error:', error);
            showReviewAlert(error.message || 'Có lỗi xảy ra. Vui lòng thử lại!', 'error');
        })
        .finally(() => {
            // Reset button
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerHTML = '<span>️</span> Gửi đánh giá';
                isReviewSubmitting = false;
            }
        });
}
// Hiển thị alert trong popup
function showReviewAlert(message, type = 'success') {
    // Xóa alert cũ nếu có
    const oldAlert = document.querySelector('.review-popup-body .alert');
    if (oldAlert) {
        oldAlert.remove();
    }
    // Tạo alert mới
    const alert = document.createElement('div');
    alert.className = `alert alert-${type}`;
    alert.innerHTML = `
        <span style="font-size: 1.2rem;">${type === 'success' ? '✓' : '✗'}</span>
        <span>${message}</span>
    `;
    // Thêm vào đầu popup body
    const popupBody = document.querySelector('.review-popup-body');
    if (popupBody) {
        popupBody.insertBefore(alert, popupBody.firstChild);
        // Auto remove sau 5 giây
        setTimeout(() => {
            alert.remove();
        }, 5000);
    }
}
// Log
console.log(' Product Review Popup JS loaded');