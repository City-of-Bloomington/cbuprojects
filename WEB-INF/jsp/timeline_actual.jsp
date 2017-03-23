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

<h3 style="text-align:center"> Project(s) Phases Actual Dates Timeline (<s:property value="min_date" /> - <s:property value="max_date" />) </h3>
Note: hoover your mouse over the timeline bars to get more info.<br /><br />
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
      google.charts.load('current', {'packages':['timeline']});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var container = document.getElementById('timeline');
        var chart = new google.visualization.Timeline(container);
        var dataTable = new google.visualization.DataTable();

        dataTable.addColumn({ type: 'string', id: 'Project' });
        dataTable.addColumn({ type: 'string', id: 'Phase' });	
        dataTable.addColumn({ type: 'date', id: 'Start' });
        dataTable.addColumn({ type: 'date', id: 'End' });
        dataTable.addRows([
					<s:iterator var="one" value="projects" status="projStatus">
						<s:iterator var="one2" value="phases" status="status">
							[ 'Project <s:property value="project_id" />','<s:property value="name" /> (<s:property value="phase_actual_length_title" />)', new Date('<s:property value="actual_start_date" />'),new Date('<s:property value="actual_end_date" />') ]
							<s:if test="!#status.last">,</s:if>
						</s:iterator>
						<s:if test="!#projStatus.last">,</s:if>
					</s:iterator>
				]);
					var options = {
						timeline: {
							colorByRowLabel: true,
							groupByRowLabel: false,
							avoidOverlappingGridLines: false
						}
					};
					chart.draw(dataTable, options);
      }
    </script>
		<div id="timeline" style="height: <s:property value='pxsHeight' />px;"></div>
		<table width="90%" border="1">
			<caption>Projects Current Phases</caption>
			<tr>
				<td>Project</td>
				<td>Phase Rank</td>
				<td>Schedule Start Date</td>
				<td>Actual Start Date</td>
				<td>Actual Complete Date</td>				
				<td>Phase Notes</td>
			</tr>
			<s:iterator var="one" value="projects">
				<tr>
					<td>
						<a href="<s:property value='#application.url' />project.action?id=<s:property value='id' />"><s:property value="name" /></a>
					</td>
					<td><s:property value="lastPhase.name" /></td>					
					<td><s:property value="lastPhase.schedule_start_date" /></td>
					<td><s:property value="lastPhase.actual_start_date" /></td>
					<td><s:property value="lastPhase.actual_complete_date" /></td>
					<td><s:property value="lastPhase.notes" /></td>
				</tr>
			</s:iterator>
		</table>
  </body>
</html>



