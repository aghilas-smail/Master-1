<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
    <h1 class="text-center mt-4">Enregistrement</h1>

    <c:if test="${not empty errorMessage}">
        <p class="text-danger text-center">${errorMessage}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/user/registration" method="post" class="mt-4">
        <div class="form-group">
            <label for="lastName">Nom :</label>
            <input type="text" id="lastName" name="lastName" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="name">Prenom :</label>
            <input type="text" id="name" name="name" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="email">Adresse email :</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="website">Site web :</label>
            <input type="url" id="website" name="website" class="form-control">
        </div>

        <div class="form-group">
            <label for="birthDay">Date de naissance :</label>
            <input type="date" id="birthDay" name="birthDay" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="groupe">Groupe :</label>
            <select name="groupe" class="form-control">
                <option value="">Selectionner un groupe</option>
                <c:forEach items="${groupes}" var="groupe">
                    <option value="${groupe.id}">${groupe.name}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Sing up</button>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
