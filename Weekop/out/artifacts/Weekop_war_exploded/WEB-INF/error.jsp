<%--
  Created by IntelliJ IDEA.
  User: Dawid
  Date: 18.03.2019
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name ="viewport" content="width=device-width,initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type = "text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
    <title>Weekop</title>

</head>
<body>

<jsp:include page="fragment/navbar.jspf"></jsp:include>

<div class = "container">
    <h2>Konto jest nieaktywne</h2><br>
    <h3>Zostałeś zbanowany lub nie aktywowałeś konta za pomocą maila</h3>
</div>

<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>
