<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="phase" method="post" id="form_id" onsubmit="return projectValidate()">
	<s:hidden name="phase.prev_date" value="%{phase.prev_date}" />
	<s:hidden name="phase.id" value="%{phase.id}" />	
	<s:hidden name="phase.project_id" value="%{phase.project_id}" />
	<h4>Edit Phase '<s:property value="%{phase.name}" />'</h4>
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
  <p>*  Required field <br />
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">
			<dl class="fn1-output-field">		
				<dt>Project </dt>
				<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='phase.project_id' />"> <s:property value="phase.project" /> </a></dd>
			</dl>
			<dl class="fn1-output-field">		
				<dt>Name *</dt>
				<dd>
					<s:textfield name="phase.name" value="%{phase.name}" size="30" maxlength="50" required="true" /></dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Scheduled Start*</dt>
				<dd><s:textfield name="phase.schedule_start_date" value="%{phase.schedule_start_date}" size="10" maxlength="10" cssClass="date2" required="true" />(date)</dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Actual Start </dt>
				<dd><s:textfield name="phase.actual_start_date" value="%{phase.actual_start_date}" size="10" maxlength="10" cssClass="date2" />(date)</dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Actual Complete </dt>
				<dd><s:textfield name="phase.actual_complete_date" value="%{phase.actual_complete_date}" size="10" maxlength="10" cssClass="date2" />(date)</dd>
			</dl>			
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">				
				<dt>Update By</dt>
				<dd><s:property value="%{phase.user}"/></dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Phase Notes</dt>
				<dd><s:textarea name="phase.notes" value="%{phase.notes}" rows="5" cols="70" /></dd>
			</dl>
		</div>
	</div>
	<s:submit name="action" type="button" value="Save Changes" cssClass="fn1-btn"/>
	<a href="<s:property value='#application.url' />activity.action?phase_id=<s:property value='phase.id' />" class="fn1-btn"> Add Phase Activity </a>
	<s:submit name="action" type="button" value="Delete This Phase" cssClass="fn1-btn"/>		
</s:form>
<s:if test="phase.hasActivities()">
	<s:set var="activities" value="phase.activities" />
	<s:set var="activitiesTitle" value="activitiesTitle" />
	<%@  include file="activities.jsp" %>	
</s:if>

<s:if test="phases != null && phases.size() > 0">
	<s:set var="phases" value="phases" />
	<s:set var="showProject" value="false" />	
	<s:set var="phasesTitle" value="phasesTitle" />
	<%@  include file="phases.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>	
<script type="text/javascript">
$(".date2").datepicker({
  nextText: "Next",
  prevText:"Prev",
	minDate: "<s:property value='%{phase.prev_date}' />",
  buttonText: "Pick Date",
  showOn: "both",
  navigationAsDateFormat: true,
  buttonImage: "/cbuprojects/js/calendar.gif"
});
 $(function() {
  $('#form_id').areYouSure();
});

</script>





































