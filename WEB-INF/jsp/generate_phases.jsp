<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="genphase" id="form_id" method="post">
	<s:hidden name="project_id" value="%{project_id}" />
	<h1>Add/Edit Project Phases</h1>
	To add additional phases please consider the following
	<ul>
		<li> Enter a meaning phase name, such as Studay, Desing, etc </li>
		<li> Enter a 'Scheduled start date' </li>
		<li> Phase name and scheduled start date are required for any new phase. </li>
		<li> The 'Schedule start date' is used to present phases in logical order </li>
		<li> Make sure the 'Schedule start date' are different for different phases. Avoid using the same date for two or more phases.</li>
		<li> If you change the 'Schedule start date' this may cause its order to change</li>
		<li> You can pick a list of predefind phases, the ones without 'Schedule start date', just make sure to enter a 'Scheduled start date' value to be added to your list. These are optional, you can come up with a list of phase names.</li>
		<li> A 'Complete' phase name is required and must have 'Schedule start date' greater than every other phases' scheduled start date.</li>
	</ul>
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
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Project </dt>
			<dd>
				<a href="<s:property value='#application.url' />project.action?id=<s:property value='project_id' />">Project: <s:property value="project.name" /></a>
			</dd>
		</dl>
		<s:if test="phases">
			<dl class="fn1-output-field">
				<dt> </dt>
				<dd> <table border="1">
					<caption>Current Project Phases</caption>
					<tr><td></td><td></td><td>Phase Name </td><td>Schedule Start Date</td></tr>
					<s:iterator var="one" value="phases" status="rankStatus">
						<tr>
							<td><s:property value="#rankStatus.count" /> - </td>
							<td><input type="hidden" name="phases[<s:property value='%{#rankStatus.index}' />].id" value="<s:property value='id' />" />
								<input type="hidden" name="phases[<s:property value='%{#rankStatus.index}' />].rank_id" value="<s:property value='rank_id' />" />
							</td>
							<td><input name="phases[<s:property value='%{#rankStatus.index}' />].name" value="<s:property value='name' />" size="30" /></td>
							<td><input name="phases[<s:property value='%{#rankStatus.index}' />].schedule_start_date" value="<s:property value='schedule_start_date' />" size="10" class="date" /></td>
						</tr>
					</s:iterator>
				</table>
				</dd>
			</dl>
		</s:if>
	</div>
	<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
</s:form>
<%@  include file="footer.jsp" %>

<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});
</script>
