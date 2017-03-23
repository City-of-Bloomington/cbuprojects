<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="project" id="form_id" method="post" onsubmit="return projectValidate()">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Project</h1>
	</s:if>
	<s:else>
		<h1>Edit Project <s:property value="%{project.id}" /></h1>
		<s:hidden name="id" value="%{id}" />

		<s:hidden name="project.geometry" id="geometery" value="%{project.geometry}" />
		<s:hidden name="project.date" value="%{project.date}" />				
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
		** Project documents path, normally the I drive. Do not include file names unless there is only one file.<br />
		<s:if test="id != ''">
			*** You can change the status to 'On hold' or 'Closed' if the project is complete or for any reason the project can not move on and no further  actions can be made. <br />
			If you make any change, please hit the 'Save Changes' button<br />
			If you change the status to "On hold" or "Closed" no more changes
			can be made;
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.<br />
			After you save the project, you be able to add 'phase ranks' to your project.<br />			
		</s:else>
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Created Date</dt>
				<dd><s:property value="%{project.date}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Project # </dt>
				<dd><s:textfield name="project.project_num" value="%{project.project_num}" size="20" maxlength="20" requiredLabel="true" title="project number for the project"/> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Project Name *</dt>
				<dd><s:textfield name="project.name" value="%{project.name}" size="25" maxlength="70" requiredLabel="true" id="project_name" required="true" title="Short name for the project"/> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Project Description</dt>
				<dd><s:textarea name="project.description" value="%{project.description}" row="4" cols="80" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Type *</dt>
				<dd><s:select name="project.type_list" list="#{'1':'Water','2':'Wastewater','3':'Stormwater'}" multiple="true" required="true" title="you can pick more than one type" size="3" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Location/Facility</dt>
				<dd><s:select name="project.location_id" value="%{project.location_id}" list="locations" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Location" title="if the location is not in the list, pick other and describe in field below"/></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Other Location</dt>
				<dd><s:textfield name="project.other_location" value="%{project.other_location}" list="locations" size="30" maxlength="80" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Funding Source</dt>
				<dd><s:select name="project.funding_source_id" value="%{project.funding_source_id}" list="sources" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Funding" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Contractor</dt>
				<dd><s:select name="project.contractor_id" value="%{project.contractor_id}" list="contractors" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Contractor" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Sub-contractor</dt>
				<dd><s:select name="project.sub_contractor_id" value="%{project.sub_contractor_id}" list="contractors" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Sub-contractor" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Consultant</dt>
				<dd><s:select name="project.consultant_id" value="%{project.consultant_id}" list="consultants" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Consultant" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Sub-consultant</dt>
				<dd><s:select name="project.sub_consultant_id" value="%{project.sub_consultant_id}" list="consultants" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Sub-consultant" /></dd>
			</dl>
			<s:if test="project.id == ''">
		</div>
		<div class="tt-split-container">
			</s:if>
			<dl class="fn1-output-field">
				<dt>Proj. Manager</dt>
				<dd><s:select name="project.manager_id" value="%{project.manager_id}" list="managers" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Manager" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Proj. Tech</dt>
				<dd><s:select name="project.tech_id" value="%{project.tech_id}" list="techs" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Proj. Tech" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Proj. Inspector</dt>
				<dd><s:select name="project.inspector_id" value="%{project.inspector_id}" list="inspectors" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick Inspector" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Certificate of Warranty</dt>
				<dd><s:select name="project.cert_warranty_type" value="%{project.cert_warranty_type}" list="#{'N/A':'N/A','1 Year':'1 Year','5 Years':'5 Years','10 Years':'10 Years','Other':'Other'}" title="if warranty period not listed pick other and describe in field below" /> if Other specify
					<s:textfield name="project.cert_warranty_other" value="%{project.cert_warranty_other}" size="20" maxlength="80" /> 
				</dd>
			</dl>		
			<dl class="fn1-output-field">
				<dt>Est. Cost</dt>
				<dd>$<s:textfield name="project.est_cost" value="%{project.est_cost}" size="12" maxlength="12" title="Estimated cost" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Actual Cost</dt>
				<dd>$<s:textfield name="project.actual_cost" value="%{project.actual_cost}" size="12" maxlength="12" /> </dd>
			</dl>
		</div>
	</div>
	<dl class="fn1-output-field">
		Certificate of Substantial Completion Date
		<s:textfield name="project.cert_sub_comp_date" value="%{project.cert_sub_comp_date}" size="10" maxlength="10" cssClass="date" /> 
	</dl>
	<dl class="fn1-output-field">
		Certificate of Final Completion Date
		<s:textfield name="project.cert_final_comp_date" value="%{project.cert_final_comp_date}" size="10" maxlength="10" cssClass="date" />
	</dl>		
	<dl class="fn1-output-field">
		<dt>Files Path **</dt>
		<dd><s:textfield name="project.file_path" value="%{project.file_path}" size="70" maxlength="150" title="Path where project files are saved, normally I drive" /> </dd>
	</dl>
	<s:if test="project.id != ''">
		<dl class="fn1-output-field">
			<dt title="change status to 'On hold' or 'Closed' if the project is done or cannot move to next phase">Status ***</dt>
			<dd><s:radio name="project.status" value="%{project.status}" list="#{'Active':'Active','On hold':'On hold','Closed':'Closed'}" /></dd>
		</dl>
	</s:if>
	<s:if test="project.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url'/>genphase.action?project_id=<s:property value='project.id' />" class="fn1-btn"> Add/Edit Phases</a>		
		<s:if test="project.canHaveMorePhases()">		
			<a href="<s:property value='#application.url'/>phase.action?project_id=<s:property value='project.id' />" class="fn1-btn">Next Project Phase</a>
		</s:if>
		<s:if test="#session.user != null && #session.user.canDelete() && project.canDelete()">
			<s:submit type="button" value="Delete" class="fn1-btn" onclick="return confirmDelete()"/>
		</s:if>
		<a href="<s:property value='#application.url'/>map.action?id=<s:property value='project.id' />" class="fn1-btn"> Add/Edit Map</a>
	</s:else>
</s:form>
<s:if test="project.id != '' && phases.size() > 0">
	<s:set var="phases" value="phases" />
	<s:set var="showProject" value="false" />
	<s:set var="phasesTitle" value="phasesTitle" />
	<%@ include file="phases.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>

<script type="text/javascript">
 $(function() {
  $('#form_id').areYouSure();
});

</script>
