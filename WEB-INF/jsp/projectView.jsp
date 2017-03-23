<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<h1><s:property value="%{project.name}" /></h1>
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

<p><s:property value="%{project.description}" /></p>
<s:if test="#session != null && #session.user != null && #session.user.canEdit()">
	<s:if test="project.canBeUpdated()">	
		<a href="<s:property value='#application.url' />project.action?id=<s:property value='project.id' />&action=Edit" class="fn1-btn">Edit Project</a>
	</s:if>
	<a href="<s:property value='#application.url'/>map.action?id=<s:property value='project.id' />" class="fn1-btn"> Add/Edit Map</a>
</s:if>
<s:elseif test="project.hasGeometry()">
	<a href="<s:property value='#application.url' />project.action?action=map&id=<s:property value='project.id' />" class="fn1-btn">Show Map</a>
</s:elseif>

<s:if test="phases.size() > 0">
	<s:set var="phases" value="phases" />
	<s:set var="showProject" value="false" />
	<s:set var="phasesTitle" value="phasesTitle" />
	<%@  include file="phases.jsp" %>
	<%@  include file="timelineOne.jsp" %>
</s:if>
<div class="tt-row-container">
	<div class="tt-split-container">
		<dl class="fn1-output-field">
			<dt>Project ID</dt>
			<dd><s:property value="%{project.id}" /></dd>
		</dl>
		<s:if test="project.num">		
			<dl class="fn1-output-field">
				<dt>Project #</dt>
				<dd><s:property value="%{project.num}" /></dd>
			</dl>
		</s:if>			
		<s:if test="project.name">
			<dl class="fn1-output-field">
				<dt>Project Name</dt>
				<dd><s:property value="%{project.name}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.description != ''">
			<dl class="fn1-output-field">
				<dt>Project Description</dt>
				<dd><s:property value="%{project.description}" /></dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Project Type(s)</dt>
			<dd><s:property value="%{project.typeNames}" /></dd>
		</dl>
		<s:if test="project.location">		
			<dl class="fn1-output-field">
				<dt>Location</dt>
				<dd><s:property value="%{project.location}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.contractor">		
			<dl class="fn1-output-field">
				<dt>Contractor</dt>
				<dd><s:property value="%{project.contractor}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.sub_contractor">				
			<dl class="fn1-output-field">
				<dt>Sub-contractor</dt>
				<dd><s:property value="%{project.sub_contractor}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.consultant">					
			<dl class="fn1-output-field">
				<dt>Consultant</dt>
				<dd><s:property value="%{project.consultant}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.sub_consultant">					
			<dl class="fn1-output-field">
				<dt>Sub-consultant</dt>
				<dd><s:property value="%{project.sub_consultant}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.manager">		
			<dl class="fn1-output-field">
				<dt>Proj. Manager</dt>
				<dd><s:property value="%{project.manager}" /></dd>
			</dl>
		</s:if>
		<s:if test="project.tech">					
			<dl class="fn1-output-field">
				<dt>Proj. Tech</dt>
				<dd><s:property value="%{project.tech}" /></dd>
			</dl>
		</s:if>			
	</div>
	<div class="tt-split-container">
		<s:if test="project.inspector">					
			<dl class="fn1-output-field">
				<dt>Proj. Inspector</dt>
				<dd><s:property value="%{project.inspector}" /></dd>
			</dl>
		</s:if>						
		<dl class="fn1-output-field">
			<dt>Funding Source</dt>
			<dd><s:property value="%{project.funding_source}" /></dd>
		</dl>
		<s:if test="project.file_path">							
			<dl class="fn1-output-field">
				<dt>File Folder Path </dt>
				<dd><s:property value="%{project.file_path}" /> </dd>
			</dl>
		</s:if>		
		<dl class="fn1-output-field">
			<dt>Created Date</dt>
			<dd><s:property value="%{project.date}" /> </dd>
		</dl>
		<s:if test="project.cert_sub_comp_date">
			<dl class="fn1-output-field">
				<dt>Certificate of Substantial Completion Date</dt>
				<dd><s:property value="%{project.cert_sub_comp_date}" /> </dd>
			</dl>
		</s:if>
		<s:if test="project.cert_final_comp_date">		
			<dl class="fn1-output-field">
				<dt>Certificate of Final Completion Date</dt>
				<dd><s:property value="%{project.cert_final_comp_date}" /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Est. Cost</dt>
			<dd>$<s:property value="%{project.est_cost}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Actual Cost</dt>
			<dd>$<s:property value="%{project.actual_cost}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Certificate of Warranty</dt>
			<dd><s:property value="%{project.cert_warranty_type}" /> 
				<s:property value="%{project.cert_warranty_other}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Status </dt>
			<dd><s:property value="%{project.status}" /> </dd>
		</dl>		
	</div>
</div>



<%@  include file="footer.jsp" %>
