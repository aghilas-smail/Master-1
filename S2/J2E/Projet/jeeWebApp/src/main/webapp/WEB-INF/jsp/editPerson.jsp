<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1 class="m-5 text-center" >Modifier mon Profil</h1>

<c:if test="${not empty errorMessage}">
    <p style="color: red">${errorMessage}</p>
</c:if>

<form action="${pageContext.request.contextPath}/user/profile" method="post">
    <input type="hidden" name="id" value="${person.id}" />
    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="lastName">Nom :</label> <input value="${person.lastName}" class="form-control" type="text" id="lastName"
                                                   name="lastName" required>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="name">Prenom :</label> <input value="${person.name}" class="form-control" type="text"
                                                       id="name" name="name" required>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="email">email :</label> <input value="${person.email}" class="form-control" type="email"
                                                                 id="email" name="email" required>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="website">Site web :</label> <input value="${person.website}" class="form-control" type="url" id="website"
                                                       name="website"><br>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="birthDay">Date de naissance :</label> <input value="${person.birthDay}" class="form-control"
                                                                    type="date" id="birthDay" name="birthDay" required>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="password">Nouveau mot de passe :</label> <input class="form-control" type="password"
                                                                    id="password" name="password">
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <label for="groupe">Groupe :</label> <select value="${person.groupe}" class="form-control" name="groupe">
        <option value="">Selectionner un groupe</option>
        <c:forEach items="${groupes}" var="groupe">
            <option value="${groupe.id}">${groupe.name}</option>
        </c:forEach>
    </select><br>
    </div>

    <div class="form-group mx-auto col-lg-8 col-md-10">
        <input class="btn btn-primary" type="submit" value="save">
    </div>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>