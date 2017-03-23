<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
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
<s:if test="phases != null && phases.size() > 0">
	<s:set var="phases" value="phases" />
	<s:set var="showProject" value="true" />
	<s:set var="phasessTitle" value="'Most Recent Projects Phases'" />
	<%@  include file="phases.jsp" %>
</s:if>
<s:elseif test="projects != null && projects.size() > 0">
  <p>Pick a project to add updates </p>	
	<s:set var="projects" value="projects" />
	<s:set var="projectsTitle" value="projectsTitle" />
	<%@  include file="projects.jsp" %>
</s:elseif>


<%@  include file="footer.jsp" %>	






































