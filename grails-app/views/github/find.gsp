<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Github Repo Lookup</title>
	</head>
	<body>
		<br />
		<g:form action="find" name="githubLookupForm">
			<g:message code="github.username" />: <g:textField name="githubId" value="${username}" placeholder="${g.message(code:'github.usernamePlaceholder')}" size="100" autofocus="autofocus" /><br />
			<g:submitButton name="submit" value="${g.message(code:'github.lookupRepositories')}" />
		</g:form>
		<br />
		<br />
		<g:if test="${repositories}">
			<h2><g:message code="github.repositoriesFor" args="[repositories.size(), username]" /></h2>
			<br />
			<g:render template="repository" collection="${repositories}" var="repository" />
		</g:if>
		<g:elseif test="${username}">
			<h2><g:message code="github.userNotFound" args="[username]" /></h2>
		</g:elseif>
	</body>
</html>
