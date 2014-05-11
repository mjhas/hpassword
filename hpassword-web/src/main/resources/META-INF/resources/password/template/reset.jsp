<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<h1>
    <spring:message code="password.reset.title" />
</h1>
<form role="form" name="reset"  >
    <div class="form-group">
        <label class="sr-only" for="rtemail">
        <spring:message code="password.change.email" />
        </label>
        <input id="rtemail" 
            type="email" 
            required
            ng-model="pcr.email" 
            class="form-control" 
            placeholder="<spring:message code="password.change.email" />"
         />
    </div>

    <div class="form-group">
        <label class="sr-only" for="rttoken">
        <spring:message code="password.change.token" />
        </label>
        <input id="rttoken" 
            type="text"
            required 
            ng-model="pcr.token" 
            class="form-control" 
            placeholder="<spring:message code="password.change.token" />"
         />
    </div>

   <div class="form-group">
        <label class="sr-only" for="rtnewPassword">
        <spring:message code="password.change.new" />
        </label>
        <input id="rtnewPassword" 
            name="rtnewPassword"
            type="password"
            required 
            ng-model="pcr.newPassword" 
            ng-minlength="8" ng-maxlength="20"
            class="form-control" 
            placeholder="<spring:message code="password.change.new" />"
         />
        <div class="alert alert-danger" ng-show="reset.rtnewPassword.$error.minlength">
            <spring:message code="password.validation.minlength" />
        </div>
        <div class="alert alert-danger" ng-show="reset.rtnewPassword.$error.maxlength">
            <spring:message code="password.validation.maxlength" />
        </div>
   </div>

   <div class="form-group">
        <div class="alert alert-danger" ng-show="reset.rtnewPasswordRepeat.$error.passwordMatch">
            <spring:message code="password.change.mismatch" />
        </div> 
        <label class="sr-only" for="rtnewPasswordRepeat">
        <spring:message code="password.change.confirm.new" />
        </label>
        <input id="rtnewPasswordRepeat" 
            name="rtnewPasswordRepeat"
            type="password"
            required
            ng-model="newPasswordRepeat" 
            password-match="pcr.newPassword"
            class="form-control" 
            placeholder="<spring:message code="password.change.confirm.new" />"
         />
         
    </div>
    <div class="form-group">
       <img src="<spring:url value="/password/captcha.jpg" />" alt="captcha" />
    </div>
    <div class="form-group">
       <label class="sr-only" for="rtcaptcha">
        <spring:message code="password.change.captcha" />
       </label>
       <input id="rtcaptcha" 
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
        <button ng-disabled="!reset.$valid" ng-click="submit(pcr,newPasswordRepeat,captcha)" class="btn btn-default">
            <spring:message code="password.change.submit" />
        </button>
    </div>
</form>