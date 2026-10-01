<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toan VNPay</title>
</head>
<body>

<h2>THANH TOAN VNPAY</h2>

<%
    if (request.getAttribute("error") != null) {
%>

<p style="color:red;">
    <%= request.getAttribute("error") %>
</p>

<%
    }
%>

<form action="payment" method="post">

    <p>
        Email nhan xac nhan:
        <br>
        <input
            type="email"
            name="email"
            required
            size="40"
            placeholder="yourmail@gmail.com"
        >
    </p>

    <p>
        So tien:
        <br>
        <input
            type="number"
            name="amount"
            value="10000"
            min="1000"
            required
        >
        VND
    </p>

    <button type="submit">
        THANH TOAN VNPAY
    </button>

</form>

</body>
</html>
