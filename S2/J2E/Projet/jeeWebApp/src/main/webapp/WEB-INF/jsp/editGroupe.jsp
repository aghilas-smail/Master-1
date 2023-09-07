<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Ajouter un groupe</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <style>
    h1 {
      margin-top: 50px;
      margin-bottom: 30px;
      font-size: 2rem;
      font-weight: bold;
      text-align: center;
    }
    form {
      margin: 0 auto;
      width: 60%;
      padding: 20px;
      border: 1px solid #ddd;
      text-align: center;
      font-size: 1.2rem;
      border-radius: 8px;
    }
    label {
      font-weight: bold;
      display: block;
      margin-bottom: 10px;
      text-align: left;
    }
    input[type="text"] {
      width: 100%;
      padding: 8px;
      border-radius: 4px;
      border: 1px solid #ddd;
    }
    input[type="submit"] {
      margin-top: 20px;
      padding: 8px 16px;
      border: none;
      border-radius: 4px;
      background-color: #007bff;
      color: #fff;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #0069d9;
    }
    .return-link {
      display: block;
      margin-top: 20px;
      text-align: center;
      font-size: 1.2rem;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1>Ajouter un groupe</h1>

<c:url var="actionUrl" value="/groupes/addGroupe" />

<form method="POST" action="${actionUrl}">
  <input type="hidden" name="id" value="${groupe.id}" />
  <label>Nom du groupe:</label> <input value="${groupe.name}" type="text" name="name"
                                       required /> <br />
  <c:choose>
    <c:when test="${groupe.id > 0}">
      <input type="submit" value="Modifier" />
    </c:when>
    <c:otherwise>
      <input type="submit" value="Ajouter" />
    </c:otherwise>
  </c:choose>

</form>

<br/>
<a href="${pageContext.request.contextPath}/groupes">Retour a la
  liste des groupes</a>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>