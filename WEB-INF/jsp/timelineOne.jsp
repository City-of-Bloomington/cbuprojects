<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3 style="text-align:center"> Project Scheduled Phases Timeline (<s:property value="min_schedule_date" /> - <s:property value="max_schedule_date" />) </h3>
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
		<s:iterator var="one" value="phases" status="status">
		[ 'Project <s:property value="project_id" />','<s:property value="name" /> (<s:property value="phase_length_title" /> )', new Date('<s:property value="schedule_start_date" />'),new Date('<s:property value="schedule_end_date" />') ]
		<s:if test="!#status.last">,</s:if>
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




