<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Kết quả thanh toán VNPay</title>

    <!-- CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/styles/style.css">

</head>


<body>

<div class="bank-page">

    <!-- HEADER -->
    <header class="bank-header">

        <div class="bank-header-inner">

            <div class="bank-logo">

                <div class="logo-symbol">
                    V
                </div>

                <div>

                    <div class="logo-title">
                        THANH TOÁN ĐIỆN TỬ
                    </div>

                    <div class="logo-subtitle">
                        Cổng thanh toán VNPay
                    </div>

                </div>

            </div>


            <div class="header-secure">
                🔒 Giao dịch an toàn
            </div>

        </div>

    </header>


    <!-- MAIN -->
    <main class="result-main">

        <div class="result-container">

            <!-- RESULT CARD -->
            <div class="result-card">


                <% if (Boolean.TRUE.equals(
                        request.getAttribute("success"))) { %>


                    <!-- SUCCESS -->

                    <div class="result-icon success-icon">
                        ✓
                    </div>

                    <div class="result-status success-text">
                        THANH TOÁN THÀNH CÔNG
                    </div>

                    <h1>
                        Giao dịch của bạn đã hoàn tất
                    </h1>

                    <p class="result-description">
                        Cảm ơn bạn đã sử dụng dịch vụ.
                        Thông tin xác nhận thanh toán đã được
                        hệ thống xử lý.
                    </p>


                <% } else { %>


                    <!-- FAILED -->

                    <div class="result-icon failed-icon">
                        !
                    </div>

                    <div class="result-status failed-text">
                        THANH TOÁN KHÔNG THÀNH CÔNG
                    </div>

                    <h1>
                        Giao dịch chưa hoàn tất
                    </h1>

                    <p class="result-description">
                        Giao dịch không thể hoàn tất.
                        Vui lòng kiểm tra lại thông tin
                        hoặc thực hiện thanh toán lại.
                    </p>


                <% } %>


                <!-- THÔNG TIN GIAO DỊCH -->

                <div class="result-section-title">

                    <span>
                        THÔNG TIN GIAO DỊCH
                    </span>

                </div>


                <div class="order-box">


                    <!-- MÃ ĐƠN HÀNG -->

                    <div class="order-row">

                        <span>
                            Mã đơn hàng
                        </span>

                        <strong>
                            <%= request.getAttribute("orderCode") %>
                        </strong>

                    </div>


                    <!-- RESPONSE CODE -->

                    <div class="order-row">

                        <span>
                            Mã phản hồi VNPay
                        </span>

                        <strong>
                            <%= request.getAttribute("responseCode") %>
                        </strong>

                    </div>


                    <!-- TRANSACTION STATUS -->

                    <div class="order-row">

                        <span>
                            Trạng thái giao dịch
                        </span>

                        <strong>
                            <%= request.getAttribute("transactionStatus") %>
                        </strong>

                    </div>


                    <!-- TRANSACTION NO -->

                    <div class="order-row">

                        <span>
                            Mã giao dịch VNPay
                        </span>

                        <strong>
                            <%= request.getAttribute("transactionNo") %>
                        </strong>

                    </div>


                    <!-- HASH -->

                    <div class="order-row">

                        <span>
                            Xác thực chữ ký
                        </span>

                        <strong>

                            <% if (Boolean.TRUE.equals(
                                    request.getAttribute(
                                        "validHash"))) { %>

                                <span class="status-valid">
                                    HỢP LỆ
                                </span>

                            <% } else { %>

                                <span class="status-invalid">
                                    KHÔNG HỢP LỆ
                                </span>

                            <% } %>

                        </strong>

                    </div>


                    <!-- EMAIL -->

                    <div class="order-row">

                        <span>
                            Email xác nhận
                        </span>

                        <strong>

                            <% String emailStatus =
                                    String.valueOf(
                                        request.getAttribute(
                                            "emailStatus"
                                        )
                                    );
                            %>


                            <% if ("SENT".equalsIgnoreCase(
                                    emailStatus)) { %>

                                <span class="status-valid">
                                    ĐÃ GỬI
                                </span>

                            <% } else { %>

                                <span class="status-invalid">
                                    <%= emailStatus %>
                                </span>

                            <% } %>

                        </strong>

                    </div>

                </div>


                <!-- ERROR -->

                <% if (request.getAttribute("error") != null
                        && !String.valueOf(
                            request.getAttribute("error")
                        ).isBlank()) { %>

                    <div class="error-box">

                        <strong>
                            Thông báo:
                        </strong>

                        <span>
                            <%= request.getAttribute("error") %>
                        </span>

                    </div>

                <% } %>


                <!-- BUTTON -->

                <div class="result-actions">

                    <a href="${pageContext.request.contextPath}/"
                       class="primary-button">

                        Thanh toán lại

                    </a>

                    <a href="${pageContext.request.contextPath}/"
                       class="secondary-button">

                        Về trang chủ

                    </a>

                </div>


                <!-- SECURITY -->

                <div class="result-secure">

                    🔒
                    Giao dịch được xử lý an toàn
                    thông qua hệ thống VNPay.

                </div>


            </div>

        </div>

    </main>


    <!-- FOOTER -->

    <footer class="bank-footer">

        © 2026 ThanhToan VNPay

        <span>|</span>

        Hệ thống thanh toán điện tử

    </footer>

</div>

</body>

</html>