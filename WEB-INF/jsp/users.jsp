<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#organizationsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Login Username</b></th>			
			<th align="center"><b>Full Name</b></th>
			<th align="center"><b>Role</b></th>
			<th align="center"><b>User Category</b></th>
			<th align="center"><b>Is Active?</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#users">
			<tr>
				<td><a href="<s:property value='#application.url' />user.action?id=<s:property value='id' />">Edit</a></td>
				<td><s:property value="userid" /></td>				
				<td><s:property value="fullname" /></td>
				<td><s:property value="roleName" /></td>
				<td><s:property value="type" /></td>
				<td><s:if test="active">Yes</s:if></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
