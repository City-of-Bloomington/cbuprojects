<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="user" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New User</h1>
	</s:if>
	<s:else>
		<h1>Edit User <s:property value="%{user.id}" /></h1>
		<s:hidden name="user.id" value="%{user.id}" />
	</s:else>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>
  <p>* Required field <br />
		<s:if test="id != ''">
			If you make any change, please hit the 'Save Changes' button
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.
		</s:else>
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Full Name </dt>
			<dd><s:textfield name="user.fullname" value="%{user.fullname}" size="30" maxlength="70" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Username </dt>
			<dd><s:textfield name="user.userid" value="%{user.userid}" size="8" maxlength="8" required="true" />* (username used to login) </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Role </dt>
			<dd><s:select name="user.role" value="%{user.role}" list="#{'View':'View only','Edit':'Edit Only','Edit:Delete':'Edit & Delete','Edit:Delete:Admin':'Admin'}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>User Category </dt>
			<dd><s:select name="user.type" value="%{user.type}" list="#{'-1':'Regular User','Manager':'Project Manager','Tech':'Project Tech','Inspector':'Project Inspector'}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Is Active? </dt>
			<dd><s:checkbox name="user.active" value="%{user.active}" /> Yes </dd>
		</dl>		
		<s:if test="user.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="users != null">
	<s:set var="users" value="users" />
	<s:set var="usersTitle" value="usersTitle" />
	<%@  include file="users.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


