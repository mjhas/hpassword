<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
    <h3>
        <spring:message code="password.dialog.header" />
    </h3>
</div>
<div class="modal-body">
    <c:set var="code">
        password.dialog.<%= request.getParameter("msg") %>
    </c:set>
    <spring:message code="${code}" /> 
</div>
<div class="modal-footer">
    <button class="btn btn-default" ng-click="cancel()">
        <spring:message code="dialog.ok" />
    </button>
</div>