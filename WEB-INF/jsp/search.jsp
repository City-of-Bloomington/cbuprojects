<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="search" id="form_id" method="post">
	<h1>Search Projects</h1>
	All fields are optional <br />
	* Any user involved in projects such as managers, technicians and inspectors. <br />
	** You can find all the projects within that date range using from and to dates<br />
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
	<div class="tt-row-container">
		<div class="tt-split-container">	
			<dl class="fn1-output-field">
				<dt>Project ID</dt>
				<dd><s:textfield name="projectList.id" size="10" value="%{projectList.id}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Project #</dt>
				<dd><s:textfield name="projectList.project_num" size="20" value="%{projectList.project_num}" /></dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Name </dt>
				<dd><s:textfield name="projectList.name" value="%{projectList.name}" size="30" maxlength="70" title="you can use partial name"/> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Location</dt>
				<dd><s:select name="projectList.location_id" value="%{projectList.location_id}" list="locations" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Type</dt>
				<dd><s:select name="projectList.type_id" value="%{projectList.type_id}" list="types" listKey="id" headerKey="-1" headerValue="All" listValue="name" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Funding Source</dt>
				<dd><s:select name="projectList.funding_source_id" value="%{projectList.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>User* </dt>
				<dd><s:select name="projectList.proj_user_id" value="%{projectList.proj_user_id}" list="project_users" listKey="id" listValue="fullname" headerKey="-1" headerValue="All" title="Manager, Tech or Inspector" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Constractor</dt>
				<dd><s:select name="projectList.contractor_id" value="%{projectList.contractor_id}" list="contractors" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Consultant</dt>
				<dd><s:select name="projectList.consultant_id" value="%{projectList.consultant_id}" list="consultants" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
			</dl>			
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt><label title="Enter the date range">Date **</label></dt>
		<dd>From: <s:textfield name="projectList.date_from" value="%{projectList.date_from}" size="10" cssClass="date" /> To:<s:textfield name="projectList.date_to" value="%{projectList.date_to}" size="10" cssClass="date" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Est. Cost</dt>
		<dd>From:<s:textfield name="projectList.est_cost_from" value="%{projectList.est_cost_from}" size="12" maxlength="12" /> To:<s:textfield name="projectList.est_cost_to" value="%{projectList.est_cost_to}" size="12" maxlength="12" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Actual Cost</dt>
		<dd>From:<s:textfield name="projectList.actual_cost_from" value="%{projectList.actual_cost_from}" size="12" maxlength="12" /> To:<s:textfield name="projectList.actual_cost_to" value="%{projectList.actual_cost_to}" size="12" maxlength="12" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Status</dt>
		<dd><s:radio name="projectList.status" value="%{projectList.status}" list="#{'-1':'All','Active':'Active','On hold':'On hold','Closed':'Closed'}" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Current Phase </dt>
		<dd><s:select name="projectList.rank_id" value="%{projectList.rank_id}" list="ranks" listKey="id" listValue="name" headerKey="-1" headerValue="All" title="The last rank phase of the project" /></dd>
	</dl>
	<dl class="fn1-input-field">
		<dt></dt>
		<dd><s:submit name="action" type="button" value="Submit" cssClass="fn1-btn" /></dd>
	</dl>
</s:form>
<s:if test="projects != null && projects.size() > 0">
	<s:set var="projects" value="projects" />
	<s:set var="projectsTitle" value="projectsTitle" />
	<%@  include file="projects.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>
