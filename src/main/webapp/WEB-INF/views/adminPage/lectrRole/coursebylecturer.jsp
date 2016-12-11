<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div class="col-xs-12" style="margin-top:10px">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">My Courses</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body table-responsive no-padding"
				id="list-students-details">
				<c:if test="${fn:length(clist) gt 0 }">
					<table class="table table-hover">
						<tbody>
							<tr>
								<th>#</th>
								<th>Course Name</th>
								<th>Course Description</th>
								<th>Max Class Size</th>
								<th>Faculty Offering</th>
								<th>Department Offering</th>
								<th>Grade Course</th>
								<th>View Enrolements</th>
							</tr>
							<c:forEach var="course" items="${clist}">
								<tr>
									<td>0</td>
									<td>${course.courseName}</td>
									<td>${course.courseDescription}</td>
									<td>${course.maxClassSize}</td>
									<td>${course.courseFacultyID.facultyName}</td>	
									<td>${course.courseDepartmentID.departmentName}</td>
									<td>
										<small>
											<a class="label label-success" href="/caps/Lecturer/gradebycourse/${course.courseID }">Grade Course</a>
										</small>
									</td>
									<td>
										<small>
											<a class="label label-success" href="/caps/Lecturer/enrolbycourse/${course.courseID }">View Enrolment</a>
										</small>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>

</body>
</html>