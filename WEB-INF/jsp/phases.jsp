<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table border="1" width="90%"><caption><s:property value="#phasesTitle" /></caption>
	<tr>
		<s:if test="#showProject == true">
			<td><b>Project</b></td>
		</s:if>
		<td><b>Phase Name</b></td>
		<td><b>Scheduled Start Date</b></td>
		<td><b>Actual Start Date</b></td>
		<td><b>Actual Complete Date</b></td>		
		<td><b>Updated By</b></td>		
		<td><b>Notes</b></td>
	</tr>
	<s:iterator var="one" value="#phases">
		<tr>
			<s:if test="#showProject">
				<td>
					<a href="<s:property value='#application.url' />project.action?id=<s:property value='project_id' />"> <s:property value="project.name" /></a>
				</td>
			</s:if>
			<td>
				<a href="<s:property value='#application.url' />phase.action?id=<s:property value='id' />"> <s:property value="name" /></a>
			</td>			
			<td>
				<s:property value="schedule_start_date" />
			</td>
			<td>
				<s:property value="actual_start_date" />
			</td>
			<td>
				<s:property value="actual_complete_date" />
			</td>			
			<td>
				<s:property value="user" />
			</td>
			<td>
				<s:property value="notes" />
			</td>
		</tr>
</s:iterator>
</table>
<s:if test="#phases.size() > 5">
	<p style="page-break-before: always"></p>
</s:if>
