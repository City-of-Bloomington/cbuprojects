<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="report" method="post">    
  <h3> CBU Project Reports</h3>
  <s:if test="hasActionErrors()">
	<div class="errors">
      <s:actionerror/>
	</div>
  </s:if>
  <s:if test="hasActionMessages()">
	<div class="welcome">
      <s:actionmessage/>
	</div>
  </s:if>
	Select one or more of report types.<br />
	* You need to select a year or date range from the given fields below.<br />
	<dl class="fn1-output-field">
		<dt>Report type: </dt>
		<dd><s:checkbox name="report.byLocation" value="report.byLocation"  />Projects classified by Location</dd>
		<dd><s:checkbox name="report.byFund" value="report.byFund"  />Projects classified by Funding Resource</dd>
		<dd><s:checkbox name="report.byManager" value="report.byManager"  />Projects classified by Manager</dd>
		<dd><s:checkbox name="report.byStatus" value="report.byStatus"  />Projects classified by Status</dd>				
	</dl>
	<dl class="fn1-output-field">			
		<dt>Project Type </dt>
		<dd><s:select name="report.type_id" list="types" value="%{report.type_id}" listKey="id" listValue="name" headerKey="-1" headerValue="All" />(optional) </dd>
	</dl>			
	<dl class="fn1-output-field">			
		<dt>Year:</dt>
		<dd><s:select name="report.year" list="years" value="%{report.year}" />* or</dd>
	</dl>
	<dl class="fn1-output-field">			
		<dt>Date, from: </dt>
		<dd><s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" />* To <s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" />*</dd>
	</dl>
			
	<dl class="fn1-output-field">			
		<dt></dt>			
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>  
<%@  include file="footer.jsp" %>	






































