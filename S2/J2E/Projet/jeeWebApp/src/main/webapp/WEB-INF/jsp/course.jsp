<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="list" value="/course/list" />
<c:url var="newCourse" value="/course/new" />
<c:url var="findCourses" value="/course/find" />


<div class="container">
	<h1>Courses</h1>
	<form action="${findCourses}" method="post">
		<p>
			<a class="btn btn-info" href="${newCourse}">New course</a> <span
				style="margin-left: 30px;"></span> <input name="name" size="10" />
			<input class="btn btn-info" type="submit" value="Find" />
		</p>
	</form>
	<c:forEach items="${courses}" var="course">
		<p>${course.id}:
			<c:out value="${course.name}" />
		</p>
	</c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
