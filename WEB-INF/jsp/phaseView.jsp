<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<h4>View Project Phase</h4>
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
			<dt>Project </dt>
			<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='phase.project_id' />"> <s:property value="phase.project" /> </a></dd>
		</dl>
		<dl class="fn1-output-field">		
			<dt>Name</dt>
			<dd><s:property value="%{phase.name}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Scheduled Start Date</dt>
			<dd><s:property value="%{phase.schedule_start_date}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Actual Start Date</dt>
			<dd><s:property value="%{phase.actual_start_date}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Actual Complete Date</dt>
			<dd><s:property value="%{phase.actual_complete_date}" /></dd>
		</dl>		
		<dl class="fn1-output-field">		
			<dt>Notes</dt>
			<dd><s:property value="%{phase.notes}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Update By</dt>
			<dd><s:property value="%{phase.user}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt></dt>
			<dd><a href="<s:property value='#application.url' />phase.action?id=<s:property value='phase.id' />">Edit Phase </a></dd>
		</dl>
	</div>
</div>
<s:if test="phases != null && phases.size() > 0">
	<s:set var="phases" value="phases" />
	<s:set var="showProject" value="false" />		
	<s:set var="phasesTitle" value="phasesTitle" />
	<%@  include file="phases.jsp" %>
</s:if>


<%@  include file="footer.jsp" %>	






































