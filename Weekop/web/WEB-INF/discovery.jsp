<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Dawid
  Date: 19.03.2019
  Time: 12:22
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

<c:if test="${not empty requestScope.discoveryById }">
        <div class = "container">
            <div class = "row bs-callout bs-callout-primary">
                <div class="col col-md-1 col-sm-2">
                    <!-- Kolumna głosowania -->
                    <a href="${pageContext.request.contextPath }/vote?discovery_id=${discoveryById.id}&vote=VOTE_UP" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-arrow-up"></span>+</a>
                    <div class="well well-sm centered"><c:out value="${discoveryById.upVote-discoveryById.downVote }"></c:out></div>
                    <a href="${pageContext.request.contextPath }/vote?discovery_id=${discoveryById.id}&vote=VOTE_DOWN" class="btn btn-block btn-primary btn btn-danger"><span class="glyphicon glyphicon-arrow-down"></span>-</a>
                </div>
                <div class= "col col-md-11 col-sm-10">
                    <!-- Kołumna z treścią -->
                    <h3 class="centered"><a href="<c:out value = "${discoveryById.url }"/>" target="_blank"><c:out value="${discoveryById.name }"></c:out></a></h3>
                    <h6><small>Dodane przez:<c:out value="${discoveryById.user.username }"></c:out>, Dnia:
                        <fmt:formatDate value="${discoveryById.timestamp }" pattern="dd/MM/YYYY"/></small></h6>
                    <p><c:out value="${discoveryById.description }"></c:out></p>
                    <a href = "<c:out value = "${discoveryById.url }"/>" class="btn btn-default btn-xs" target="_blank">Przejdz do strony</a>
                </div>
                <div>
                    <h3 class="centered">Komentarze:</h3>
                </div>
                <c:if test="${not empty requestScope.comments}">
                    <c:forEach var="comment" items="${requestScope.comments}">
                        <div class="container" style="margin-top: 10px">
                        <div class="alert alert-dark" role="alert">
                            <h5><c:out value="${comment.user.username}:"></c:out></h5>
                            <p><c:out value="${comment.content}"></c:out></p>
                            <hr>
                            <p class="mb-0">Dnia:
                                <fmt:formatDate value="${comment.date }" pattern="dd/MM/YYYY"/> </p>
                            <c:if test="${not empty sessionScope.admin or sessionScope.user.username eq comment.user.username}">
                                <a href = "${pageContext.request.contextPath}/deleteComment?comment_to_delete_id=${comment.id}" class="btn btn-danger btn-xs">Usuń</a>
                            </c:if>
                        </div>
                        </div>
                    </c:forEach>
                </c:if>


                <c:if test="${sessionScope.user.active}">
                <div class="col-md-8 col-md-ofset-2">
                    <form class="form-signin" action = "comment?discovery_id=${discoveryById.id}" method="post">
                        <textarea name = "content" rows="2" class="form-control" placeholder="Dodaj komentarz"></textarea>
                        <button class = "btn btn-lg btn-primary btn-block" type="submit">Dodaj</button>
                    </form>
                </div>
                </c:if>
            </div>
        </div>
</c:if>


<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>
