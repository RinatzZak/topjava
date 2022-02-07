<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
