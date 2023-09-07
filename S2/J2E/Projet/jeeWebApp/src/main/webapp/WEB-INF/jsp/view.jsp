<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1 class="text-center">Membre  du groupe ${groupe.name}</h1>
<table class="table table-striped">
  <thead>
  <tr>
    <th scope="col">Nom</th>
    <th scope="col">Prénom</th>
    <th scope="col">Website</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="person" items="${persons}">
    <tr>
      <td><c:out value="${person.lastName}" /></td>
      <td><c:out value="${person.name}" /></td>
      <td><a href="${person.website}">${person.website}</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>