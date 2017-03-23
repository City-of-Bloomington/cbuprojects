<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="activity" method="post" id="form_id">
	<s:hidden name="activity.phase_id" value="%{activity.phase_id}" />
	<s:if test="activity.id == ''">
		<h4>New Phase Activity</h4>
	</s:if>
	<s:else>
		<s:hidden name="activity.id" value="%{activity.id}" />
		<h4>Edit Phase Activity</h4>
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
  <p>*  Required field <br />
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<dl class="fn1-output-field">		
				<dt>Project </dt>
				<dd><a href="<s:property value='#application.url' />project.action?id=<s:property value='%{activity.phase.project_id}' />"> <s:property value="%{activity.phase.project}" /> </a></dd>
			</dl>
			<dl class="fn1-output-field">		
				<dt>Phase</dt>
				<dd><a href="<s:property value='#application.url' />phase.action?id=<s:property value='%{activity.phase_id}' />"> <s:property value="%{activity.phase}" /> </a></dd>
		</dl>
		</div>
		<div class="tt-split-container">				
			<dl class="fn1-output-field">				
				<dt>Date *</dt>
				<dd><s:textfield name="activity.date" value="%{activity.date}" size="10" maxlength="10" cssClass="date" required="true" />*</dd>
			</dl>
			<dl class="fn1-output-field">				
				<dt>Update By</dt>
				<dd>
				<s:if test="activity.id != ''">			
					<s:property value="%{phase.user}"/>
				</s:if>
				</dd>
			</dl>
		</div>
		<dl class="fn1-output-field">				
			<dt>Activity *</dt>
			<dd><s:textarea name="activity.name" value="%{activity.name}" rows="5" cols="70" required="true" /></dd>
		</dl>
	</div>
	<s:if test="activity.id == ''">
		<s:submit name="action" type="button" value="Save" cssClass="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" cssClass="fn1-btn"/>
		<a href="<s:property value='#application.url' />activity.action?phase_id=<s:property value='phase.id' />" class="fn1-btn"> Add Phase Activity </a>
	</s:else>
	
</s:form>
<s:if test="phase.hasActivities()">
	<s:set var="activities" value="phase.activities" />
	<s:set var="activitiesTitle" value="activitiesTitle" />
	<%@  include file="activities.jsp" %>	
</s:if>
<%@  include file="footer.jsp" %>	
<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});
</script>





































