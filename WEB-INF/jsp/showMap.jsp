<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<h3>CBU Projects</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<p>Note: You can click on any of the lines/points (if there is any) in the map to get the project name and link</p>
<div id="map" class="mapPanel"></div>
<div id="marker"></div>

<table class="fn1-table">
	<caption><s:property value="projectsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Location</b></th>
			<th align="center"><b>Type</b></th>			
			<th align="center"><b>Proj. Manager</b></th>
			<th align="center"><b>Proj. Tech</b></th>			
			<th align="center"><b>Inspector</b></th>
			<th align="center"><b>Created Date</b></th>			
			<th>Has Map?</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="%{projects}">
			<tr>
				<td><a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />"><s:property value="id" /> Details</a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="location" /></td>
				<td><s:property value="typeNames" /></td>				
				<td><s:property value="manager" /></td>
				<td><s:property value="tech" /></td>
				<td><s:property value="inspector" /></td>
				<td><s:property value="date" /></td>
				<s:if test="hasGeometry()">
					<td class="project">Yes
						<div id="<s:property value='id' />" class="<s:property value='firstTypeId' />" >
							<div class="geography" style="display:none"><s:property value="geometry" /></div>
							<div class="project_name" style="display:none"><s:property value="name" /></div>
							<div class="url" style="display:none"><s:property value="#application.url" /></div>							
						</div>
					</td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
				</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>

<script type="text/javascript" src="<s:property value='#application.url' />js/ol.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/ol3-popup.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/showMap.js"></script>

<%@  include file="footer.jsp" %>
