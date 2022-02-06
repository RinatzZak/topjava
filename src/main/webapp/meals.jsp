<%--
  Created by IntelliJ IDEA.
  User: Rinat Zakirov
  Date: 04.02.2022
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add</a>
<table border="1">
    <thead>
    <tr style="background: lavender">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.mealList}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="date"/>
            <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" /></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value=" ${meal.id} "/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value=" ${meal.id} "/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
