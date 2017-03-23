<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#projectsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Proj. #</b></th>			
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Type</b></th>			
			<th align="center"><b>Location</b></th>			
			<th align="center"><b>Proj. Manager</b></th>
			<th align="center"><b>Proj. Tech</b></th>
			<th align="center"><b>Proj. Inspector</b></th>			
			<th align="center"><b>Contractor</b></th>
			<th align="center"><b>Consultant</b></th>
			<th align="center"><b>Created Date</b></th>			
			<th align="center"><b>Last Phase Rank</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#projects">
			<tr>
				<td><a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />"><s:property value="id" /> </a></td>
				<td><s:property value="project_num" /></td>
				<td><s:property value="name" /></td>				
				<td><s:property value="typeNames" /></td>				
				<td><s:property value="location" /></td>				
				<td><s:property value="manager" /></td>
				<td><s:property value="tech" /></td>
				<td><s:property value="inspector" /></td>
				<td><s:property value="contractor" /></td>
				<td><s:property value="consultant" /></td>
				<td><s:property value="date" /></td>
				<s:if test="hasPhases()">
					<td><s:property value="lastPhase.rank" /></td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
				</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>
