<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <title></title>
</head>
<body>
<br>

<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading"><b>Full list of projects</b></div>
    <table class="table table-hover">
      <tbody>
      <tr>
        <th>Project</th>
        <th>Category</th>
        <th></th>
      </tr>
      <tr>
        <c:forEach var="c" items="${projects}">
        <form>
          <td onclick="window.location.href='/servlet/project/<c:out
                  value="${c.id}"/>'">
            <a href="/servlet/project/<c:out value="${c.id}"/>">
              <c:out value="${c.name}"/>
            </a>
          </td>
          <td onclick="window.location.href='/servlet/project/<c:out
                  value="${c.id}"/>'">
            <a href="/servlet/project/<c:out value="${c.id}"/>">
              <c:out value="${c.category.name}"/>
            </a>
          </td>
          <td class="text-right text-nowrap">
            <button class="btn btn-xs btn-primary" formmethod="get"
                    formaction='/servlet/project/<c:out
                      value="${c.id}"/>/edit' type="submit">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
            <button class="btn btn-xs btn-danger" formmethod="post"
                    formaction='/servlet/project/<c:out
                      value="${c.id}"/>/delete' type="submit">
              <span class="glyphicon glyphicon-trash"></span>
            </button>
          </td>
        </form>
      </tr>
      </c:forEach>
      <tr>
        <td class="text-left text-nowrap">
          <button class="btn btn-xs btn-primary"
                  onclick="window.location.href='/servlet/project/add'">
            <span class="glyphicon glyphicon-plus-sign"></span>
            Add new project
          </button>
        </td>
        <td></td>
        <td></td>
      </tr>
      </tbody>
    </table>
  </div>
</div>

</body>
</html>
