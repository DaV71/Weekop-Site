<%--
  Created by IntelliJ IDEA.
  User: Dawid
  Date: 20.03.2019
  Time: 09:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<c:if test="${not empty requestScope.discoveriesByUserId }">
    <c:forEach var="discovery" items="${requestScope.discoveriesByUserId }">
        <div class = "container">
            <div class = "row bs-callout bs-callout-primary">
                <div class="col col-md-1 col-sm-2">
                    <!-- Kolumna głosowania -->
                    <a href="${pageContext.request.contextPath }/vote?discovery_id=${discovery.id}&vote=VOTE_UP" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-arrow-up"></span>+</a>
                    <div class="well well-sm centered"><c:out value="${discovery.upVote-discovery.downVote }"></c:out></div>
                    <a href="${pageContext.request.contextPath }/vote?discovery_id=${discovery.id}&vote=VOTE_DOWN" class="btn btn-block btn-primary btn btn-danger"><span class="glyphicon glyphicon-arrow-down"></span>-</a>
                </div>
                <div class= "col col-md-11 col-sm-10">
                    <!-- Kołumna z treścią -->
                    <h3 class="centered"><a href="<c:out value = "${discovery.url }"/>" target="_blank"><c:out value="${discovery.name }"></c:out></a></h3>
                    <h6><small>Dodane przez:<c:out value="${discovery.user.username }"></c:out>, Dnia:
                        <fmt:formatDate value="${discovery.timestamp }" pattern="dd/MM/YYYY"/></small></h6>
                    <p><c:out value="${discovery.description }"></c:out></p>
                    <a href = "<c:out value = "${discovery.url }"/>" class="btn btn-default btn-xs" target="_blank">Przejdz do strony</a>
                    <a href = "${pageContext.request.contextPath}/comment?discovery_id=${discovery.id}" class="btn btn-default btn-xs">Komentarze(<c:out value="${discovery.numberOfComments}"></c:out>)</a>
                    <a href = "${pageContext.request.contextPath}/edit?discovery_id=${discovery.id}" class="btn btn-default btn-xs">Edytuj</a>
                </div>
            </div>
        </div>
    </c:forEach>
</c:if>
<a href = "${pageContext.request.contextPath}/deleteAccount" class="btn btn-danger btn-xs" style="margin-left: 100px;margin-top: 30px">Usuń konto</a>


<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>
