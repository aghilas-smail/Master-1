<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liste des personnes</title>
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
        p {
            text-align: center;
            font-size: 1.2rem;
            margin-top: 20px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<main class="container">
    <h1>Liste des personnes</h1>
    <table>
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Groupe</th>
            <th>email</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="person" items="${persons}">
            <tr>
                <td><c:out value="${person.lastName}" /></td>
                <td><c:out value="${person.name}" /></td>
                <td><c:out value="${person.groupe.name}" /></td>
                <td><c:out value="${person.email}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>


