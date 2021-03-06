<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<c:set var="dateplaceholder">
	<spring:message code="placeholder.computer.date" />
</c:set>
<c:set var="resourcesUrl"
	value="${pageContext.request.contextPath}/resources" />
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${resourcesUrl}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${resourcesUrl}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${resourcesUrl}/css/main.css" rel="stylesheet"
	media="screen">
<script src="${resourcesUrl}/js/jquery.min.js"></script>
<script src="${resourcesUrl}/js/jquery.validate.min.js"></script>
<script src="${resourcesUrl}/js/form.validator.js"></script>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<h4 align="center" style="color:${service.color};">
			<c:if test="${!empty result}">
				<spring:message code="${result}" />
			</c:if>
		</h4>
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1>
						<spring:message code="computer.edit" />
					</h1>
					<fmt:formatDate value="${dateDiscontinued}" pattern="${pattern}" />
					<springForm:form id="form" action="edit"
						modelAttribute="computerDTO" method="POST">
						<springForm:input type="hidden" path="id" name="id" id="id"
							value="${computer.id}" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="computer.name" /></label>
								<springForm:input path="name" name="name" type="text"
									class="form-control" id="name" value="${computer.name}" />
								<springForm:errors path="name" cssClass="help-block form-error" />
							</div>
							<!-- Converting the date from yyyy-mm-dd to locale  -->
							<c:set var="locale">${pageContext.response.locale}</c:set>
							<c:set var="pattern"
								value="${ ('en' eq locale) ? 'MM-dd-yyyy' : 'dd-MM-yyyy'}" />
							<fmt:parseDate pattern="yyyy-MM-dd"
								value="${computer.introduced}" var="dateIntroduced" />
							<fmt:parseDate pattern="yyyy-MM-dd"
								value="${computer.discontinued}" var="dateDiscontinued" />
							<fmt:formatDate value='${dateIntroduced}' pattern='${pattern}'
								var="introDate" />
							<fmt:formatDate value="${dateDiscontinued}" pattern="${pattern}"
								var="discoDate" />
							<div class="form-group">
								<label for="introduced"><spring:message
										code="computer.introduced" /></label>
								<springForm:input type="text" class="form-control"
									placeholder="${ dateplaceholder}" path="introduced"
									id="introduced" name="introduced" value="${introDate}" />
								<springForm:errors path="introduced"
									cssClass="help-block form-error" />
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.add" /></label>
								<springForm:input path="discontinued" type="text"
									placeholder="${ dateplaceholder}" class="form-control"
									id="discontinued" name="discontinued" value="${discoDate}" />
								<span class="help-block form-error">${error['discontinued']}</span>
								<springForm:errors path="discontinued"
									cssClass="help-block form-error" />
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="computer.company" /></label>
								<springForm:select path="companyId" class="form-control"
									id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${map}" var="entry">
										<option value="${entry.key}">
											<f:selected stringTest="${entry.value}"
												stringRef="${computer.companyName}" /> ${entry.value}
										</option>
									</c:forEach>
								</springForm:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="button.edit"/>"
								class="btn btn-primary"> or <a href="dashboard"
								class="btn btn-default"><spring:message code="button.cancel" /></a>
						</div>
						 <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" id="_csrf"/>
					</springForm:form>
					<script type="text/javascript">
						var translated = new Array();
						translated['required'] = "<spring:message code='required' javaScriptEscape='true' />";
						translated['notblank'] = "<spring:message code='notblank' javaScriptEscape='true' />";
						translated['isdate'] = "<spring:message code='isdate' javaScriptEscape='true' />";
						translated['before70'] = "<spring:message code='before70' javaScriptEscape='true' />";
						translated['greaterthan'] = "<spring:message code='greaterthan' javaScriptEscape='true' />";
					</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>