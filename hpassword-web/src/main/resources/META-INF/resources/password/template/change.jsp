<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<h1>
    <spring:message code="password.change.title" />
</h1>
<form name="change" role="form"  >
    <div class="form-group">
        <label class="sr-only" for="chemail">
        <spring:message code="password.change.email" />
        </label>
        <input id="chemail" 
            type="email" 
            required
            ng-model="pcr.email" 
            class="form-control" 
            placeholder="<spring:message code="password.change.email" />"
         />
    </div>

    <div class="form-group">
        <label class="sr-only" for="choldPassword">
        <spring:message code="password.change.old" />
        </label>
        <input id="choldPassword" 
            type="password"
            required 
            ng-model="pcr.oldPassword" 
            class="form-control" 
            placeholder="<spring:message code="password.change.old" />"
         />
    </div>

   <div class="form-group">
        <div class="alert alert-danger" ng-show="change.chnewPassword.$error.minlength">
            <spring:message code="password.validation.minlength" />
        </div>
        <div class="alert alert-danger" ng-show="change.chnewPassword.$error.maxlength">
            <spring:message code="password.validation.maxlength" />
        </div>
        <label class="sr-only" for="chnewPassword">
        <spring:message code="password.change.new" />
        </label>
        <input id="chnewPassword"
            name="chnewPassword" 
            type="password"
            required 
            ng-model="pcr.newPassword" 
            ng-minlength="8" ng-maxlength="20"
            class="form-control" 
            placeholder="<spring:message code="password.change.new" />"
         />
    </div>

   <div class="form-group">
         <div class="alert alert-danger" ng-show="change.chnewPasswordRepeat.$error.passwordMatch">
            <spring:message code="password.change.mismatch" />
         </div> 
        <label class="sr-only" for="newPasswordRepeat">
        <spring:message code="password.change.confirm.new" />
        </label>
        <input id="chnewPasswordRepeat" 
            type="password"
            name="chnewPasswordRepeat"
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
       <label class="sr-only" for="chcaptcha">
        <spring:message code="password.change.captcha" />
       </label>
       <input id="chcaptcha" 
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
        <button ng-disabled="!change.$valid" ng-click="submit(pcr,newPasswordRepeat,captcha)" class="btn btn-default">
            <spring:message code="password.change.submit" />
        </button>
    </div>
</form>