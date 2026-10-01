<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Thanh toán VNPay</title>

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


    <!-- CONTENT -->
    <main class="bank-main">

        <div class="payment-container">

            <!-- TITLE -->
            <div class="payment-title">

                <div class="payment-title-icon">
                    $
                </div>

                <div>
                    <h1>
                        Thanh toán VNPay
                    </h1>

                    <p>
                        Vui lòng nhập thông tin để tiếp tục thanh toán
                    </p>
                </div>

            </div>


            <!-- STEP -->
            <div class="payment-step">

                <div class="step active">
                    <span>1</span>
                    <label>Thông tin</label>
                </div>

                <div class="step-line"></div>

                <div class="step">
                    <span>2</span>
                    <label>Thanh toán</label>
                </div>

                <div class="step-line"></div>

                <div class="step">
                    <span>3</span>
                    <label>Hoàn tất</label>
                </div>

            </div>


            <!-- FORM -->
            <form action="${pageContext.request.contextPath}/payment"
                  method="post"
                  class="payment-form">


                <!-- EMAIL -->
                <div class="form-section">

                    <div class="section-title">
                        <span class="section-number">01</span>

                        <div>
                            <strong>Thông tin người thanh toán</strong>

                            <small>
                                Thông tin dùng để nhận kết quả giao dịch
                            </small>
                        </div>
                    </div>


                    <div class="form-group">

                        <label for="email">
                            Email nhận thông báo
                            <span>*</span>
                        </label>

                        <div class="input-box">

                            <span class="input-icon">
                                ✉
                            </span>

                            <input
                                type="email"
                                id="email"
                                name="email"
                                placeholder="Nhập địa chỉ email"
                                required
                            >

                        </div>

                    </div>

                </div>


                <!-- AMOUNT -->
                <div class="form-section">

                    <div class="section-title">

                        <span class="section-number">02</span>

                        <div>
                            <strong>Thông tin thanh toán</strong>

                            <small>
                                Nhập số tiền cần thanh toán
                            </small>
                        </div>

                    </div>


                    <div class="form-group">

                        <label for="amount">
                            Số tiền thanh toán
                            <span>*</span>
                        </label>

                        <div class="input-box">

                            <span class="input-icon">
                                ₫
                            </span>

                            <input
                                type="number"
                                id="amount"
                                name="amount"
                                min="1000"
                                step="1000"
                                value="10000"
                                required
                            >

                            <span class="input-unit">
                                VND
                            </span>

                        </div>

                    </div>


                    <div class="amount-note">
                        Số tiền tối thiểu: 1.000 VND
                    </div>

                </div>


                <!-- PAYMENT SUMMARY -->
                <div class="summary-box">

                    <div class="summary-row">

                        <span>
                            Phương thức thanh toán
                        </span>

                        <strong>
                            VNPay
                        </strong>

                    </div>


                    <div class="summary-row">

                        <span>
                            Loại giao dịch
                        </span>

                        <strong>
                            Thanh toán trực tuyến
                        </strong>

                    </div>


                    <div class="summary-row total">

                        <span>
                            Tổng tiền thanh toán
                        </span>

                        <strong>
                            Theo số tiền nhập
                        </strong>

                    </div>

                </div>


                <!-- BUTTON -->
                <button type="submit"
                        class="btn-payment">

                    <span>
                        Tiếp tục thanh toán
                    </span>

                    <span class="btn-arrow">
                        →
                    </span>

                </button>


                <!-- SECURITY -->
                <div class="security-note">

                    <span class="security-icon">
                        🔒
                    </span>

                    <span>
                        Thông tin giao dịch được bảo mật và xử lý
                        thông qua hệ thống VNPay Sandbox.
                    </span>

                </div>

            </form>


            <!-- SUPPORTED -->
            <div class="supported">

                <span>
                    Được hỗ trợ bởi
                </span>

                <strong>
                    VNPay
                </strong>

                <span class="dot">•</span>

                <span>
                    MySQL
                </span>

                <span class="dot">•</span>

                <span>
                    Brevo
                </span>

            </div>

        </div>

    </main>


    <!-- FOOTER -->
    <footer class="bank-footer">

        © 2026 ThanhToan VNPay
        <span> | </span>
        Hệ thống thanh toán điện tử

    </footer>

</div>

</body>
</html>