<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>List Page</h1>

    <ul>
        <c:forEach var="dto" items="${list}">
            <li>${dto}</li>
        </c:forEach>
    </ul>
</body>
</html>
