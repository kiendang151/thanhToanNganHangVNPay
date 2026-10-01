# sendmailC14 - VNPay + Gmail - Web Application

Project được dựng theo cấu trúc Maven WAR của repo mẫu VNPay-integration:
- src/main/java
- src/main/webapp
- pom.xml

Được điều chỉnh để chạy với:
- Java 17
- NetBeans
- Tomcat 10.1
- Jakarta Servlet
- MySQL
- VNPay Sandbox
- Brevo API

## 1. Database

Chạy database.sql.

Database:
PaymentDemo

Table:
Payments

## 2. Cấu hình

Mở:

src/main/java/com/phamchien/payment/config/Config.java

Điền:

VNP_TMN_CODE
VNP_HASH_SECRET
DB_PASSWORD
BREVO_API_KEY
BREVO_SENDER_EMAIL

## 3. Gmail

Không dùng mật khẩu Gmail thường.

Dùng Google App Password 16 ký tự sau khi bật 2-Step Verification.

## 4. Chạy

NetBeans:
Clean and Build
Run

Tomcat:
10.1

URL:

http://localhost:8080/sendmailC14/

## 5. Thanh toán

Mở:

http://localhost:8080/sendmailC14/payment.jsp

Nhập:
- Email
- Số tiền

Bấm THANH TOAN VNPAY.

## 6. Luồng

payment.jsp
-> PaymentServlet
-> MySQL PENDING
-> VNPay Sandbox
-> VNPay Return
-> kiểm tra chữ ký
-> kiểm tra số tiền
-> MySQL PAID
-> EmailService
-> Gmail

IPN đã được chuẩn bị tại:

/vnpay-ipn

IPN cần URL public để VNPAY gọi tới. Localhost phù hợp để test Return URL trước.

## 7. Kiểm tra database

SELECT * FROM Payments ORDER BY id DESC;

## Brevo

Project nay dung Brevo Transactional Email API, khong dung Gmail SMTP.

Endpoint:
https://api.brevo.com/v3/smtp/email

Config.java can:
- BREVO_API_KEY
- BREVO_SENDER_NAME
- BREVO_SENDER_EMAIL

BREVO_SENDER_EMAIL phai la sender da duoc xac minh trong Brevo.

API key phai duoc giu bi mat, khong commit len GitHub. Khi deploy Render, nen dat BREVO_API_KEY bang Environment Variable.
