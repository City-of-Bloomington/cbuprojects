<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table border="1" width="90%"><caption><s:property value="#activitiesTitle" /></caption>
	<tr>
		<td><b>Activity</b></td>						
		<td><b>Date</b></td>		
		<td><b>Updated By</b></td>				
	</tr>
	<s:iterator var="one" value="#activities">
		<tr>
			<td>
				<a href="<s:property value='#application.url' />activity.action?id=<s:property value='id' />"> <s:property value="date" /></a>
			</td>
			<td>
				<s:property value="name" />
			</td>
			<td>
				<s:property value="user" />
			</td>
		</tr>
</s:iterator>
</table>
