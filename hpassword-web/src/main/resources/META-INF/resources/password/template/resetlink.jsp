<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<h1>
	<spring:message code="password.resetlink.title" />
</h1>
<form role="form" name="resetlink" class="" >
    <div class="form-group">
        <label class="sr-only" for="rlemail">
        <spring:message code="password.change.email" />
        </label>
        <input id="rlemail" 
            type="email" 
            required
            ng-model="prr.email" 
            class="form-control" 
            placeholder="<spring:message code="password.change.email" />"
         />
    </div>
	<div class="form-group">
       <img src="<spring:url value="/password/captcha.jpg" />" alt="captcha" />
    </div>
    <div class="form-group">
       <label class="sr-only" for="rlcaptcha">
        <spring:message code="password.change.captcha" />
       </label>
       <input id="rlcaptcha" 
            type="text" 
            ng-model="captcha" 
            required
            class="form-control" 
            placeholder="<spring:message code="password.change.captcha" />"
         />
    </div>
    <div class="form-group">
        <button ng-click="reset()" class="btn">
            <spring:message code="password.change.clear" />
        </button>
        <button ng-disabled="!resetlink.$valid" ng-click="submit(prr,captcha)" class="btn btn-default">
            <spring:message code="password.change.submit" />
        </button>
    </div>
</form>