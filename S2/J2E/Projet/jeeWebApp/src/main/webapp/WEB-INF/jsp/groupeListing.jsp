<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liste des groupes</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <style>
        h1 {
            margin-top: 50px;
            margin-bottom: 30px;
            font-size: 2rem;
            font-weight: bold;
            text-align: center;
        }
        table {
            margin: 0 auto;
            width: 80%;
            border-collapse: collapse;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 1.2rem;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-size: 1.3rem;
        }
        a {
            margin-right: 10px;
            text-decoration: none;
            color: #fff;
            background-color: #00d982;
            padding: 8px 8px;
            border-radius: 4px;
        }
        a:hover {
            background-color: #00d982;
        }
        .add-btn {
            margin-top: 20px;
            display: block;
            text-align: center;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<main class="container">
    <h1>Liste des groupes</h1>
    <form class="float-right" action="${pageContext.request.contextPath}/groupes/rechercher" method="GET">
        <div class="form-group float-left">
            <input placeholder="Nom du groupe" type="text" class="form-control" id="name" name="name">
        </div>
        <button type="submit" class="float-right btn btn-primary">Rechercher</button>
    </form>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Operations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="groupe" items="${groupes}">
            <tr>
                <td>${groupe.id}</td>
                <td>${groupe.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/groupes/editGroupe?id=${groupe.id}" class="edit-btn">Modifier</a>
                    <a href="${pageContext.request.contextPath}/groupes/removeGroupe?id=${groupe.id}" class="delete-btn">Supprimer</a>
                    <a href="${pageContext.request.contextPath}/groupes/show?id=${groupe.id}" class="delete-btn">Show</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/groupes/addGroupe" class="add-btn">Ajouter un groupe</a>
</main>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>