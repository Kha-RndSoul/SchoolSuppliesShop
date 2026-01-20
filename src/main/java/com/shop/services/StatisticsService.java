package com.shop.services;

import com.shop.dao.order.OrderDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service xử lý thống kê cho Dashboard
 */
public class StatisticsService {

    private final OrderDAO orderDAO;

    public StatisticsService() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Lấy thống kê theo khoảng thời gian
     */
    public Map<String, Object> getStatistics(String filterType, String startDate, String endDate) {
        Map<String, Object> stats = new HashMap<>();

        // Tính toán khoảng thời gian dựa trên filterType
        LocalDateTime[] dateRange = calculateDateRange(filterType, startDate, endDate);
        LocalDateTime startDateTime = dateRange[0];
        LocalDateTime endDateTime = dateRange[1];

        // Lấy các thống kê
        stats.put("revenue", getRevenue(startDateTime, endDateTime));
        stats.put("newOrders", getNewOrders(startDateTime, endDateTime));

        // Thông tin filter để hiển thị
        stats.put("filterType", filterType != null ? filterType : "30days");
        stats.put("startDate", formatDate(startDateTime));
        stats.put("endDate", formatDate(endDateTime));
        stats.put("displayFilter", getDisplayFilterText(filterType));

        return stats;
    }

    /**
     * Tính toán khoảng thời gian dựa trên loại filter
     */
    private LocalDateTime[] calculateDateRange(String filterType, String startDate, String endDate) {
        LocalDateTime start;
        LocalDateTime end;

        // Nếu không có filterType, mặc định là 30 ngày
        if (filterType == null || filterType.trim().isEmpty()) {
            filterType = "30days";
        }

        switch (filterType) {
            case "today":
                start = LocalDate.now().atStartOfDay();
                end = LocalDate.now().atTime(LocalTime.MAX);
                break;

            case "yesterday":
                start = LocalDate.now().minusDays(1).atStartOfDay();
                end = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
                break;

            case "7days":
                start = LocalDate.now().minusDays(6).atStartOfDay();
                end = LocalDate.now().atTime(LocalTime.MAX);
                break;

            case "30days":
                start = LocalDate.now().minusDays(29).atStartOfDay();
                end = LocalDate.now().atTime(LocalTime.MAX);
                break;

            case "custom":
                if (startDate != null && !startDate.trim().isEmpty()
                        && endDate != null && !endDate.trim().isEmpty()) {
                    try {
                        start = LocalDate.parse(startDate).atStartOfDay();
                        end = LocalDate.parse(endDate).atTime(LocalTime.MAX);
                    } catch (Exception e) {
                        // Nếu parse lỗi, fallback về 30 ngày
                        start = LocalDate.now().minusDays(29).atStartOfDay();
                        end = LocalDate.now().atTime(LocalTime.MAX);
                    }
                } else {
                    // Không có date, fallback về 30 ngày
                    start = LocalDate.now().minusDays(29).atStartOfDay();
                    end = LocalDate.now().atTime(LocalTime.MAX);
                }
                break;

            default:
                // Default: 30 ngày gần nhất
                start = LocalDate.now().minusDays(29).atStartOfDay();
                end = LocalDate.now().atTime(LocalTime.MAX);
        }

        return new LocalDateTime[]{start, end};
    }

    /**
     * Tính tổng doanh thu (chỉ đơn hàng COMPLETED)
     */
    private String getRevenue(LocalDateTime start, LocalDateTime end) {
        double revenue = orderDAO.getRevenueByDateRange(start, end);
        return formatCurrency(revenue);
    }

    /**
     * Đếm số đơn hàng mới (tất cả trạng thái trừ CANCELLED)
     */
    private int getNewOrders(LocalDateTime start, LocalDateTime end) {
        return orderDAO.countOrdersByDateRange(start, end);
    }

    /**
     * Format số tiền thành chuỗi VND
     */
    private String formatCurrency(double amount) {
        if (amount >= 1_000_000_000) {
            return String.format("%.1f tỷ đ", amount / 1_000_000_000);
        } else if (amount >= 1_000_000) {
            return String.format("%.1f triệu đ", amount / 1_000_000);
        } else if (amount >= 1_000) {
            return String.format("%,,.0f đ", amount);
        } else {
            return String.format("%.0f đ", amount);
        }
    }

    /**
     * Format LocalDateTime thành String
     */
    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.toLocalDate().format(formatter);
    }

    /**
     * Lấy text hiển thị cho filter
     */
    private String getDisplayFilterText(String filterType) {
        if (filterType == null) return "30 ngày qua";

        switch (filterType) {
            case "today":
                return "Hôm nay";
            case "yesterday":
                return "Hôm qua";
            case "7days":
                return "7 ngày qua";
            case "30days":
                return "30 ngày qua";
            case "custom":
                return "Tùy chỉnh";
            default:
                return "30 ngày qua";
        }
    }

    /**
     * Lấy thống kê mặc định (30 ngày gần nhất)
     */
    public Map<String, Object> getDefaultStatistics() {
        return getStatistics("30days", null, null);
    }
}