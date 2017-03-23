<?xml version="1.0" encoding="UTF-8" ?>
<%@  include file="header.jsp" %>
<h3>CBU Projects</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<script language="javascript"> 

function toggle() {

	var ele = document.getElementById("toggle");

	var text = document.getElementById("display");

	if(ele.style.display == "block") {

    		ele.style.display = "none";

		text.innerHTML = "Details how to draw on the map";

  	}

	else {

		ele.style.display = "block";

		text.innerHTML = "Hide Details";

	}

} 

</script>
<a id="display" href="javascript:toggle();">Details how to draw on the map</a>
<div id="toggle" style="display: none">
	<ul>
		<li>You can add straight line or points to the map.</li>
		<li>To add straight lines, fist click on the "Line" button below.</li>
		<li>Zoom in/out to the location you want to draw by using your scroll mouse button</li>
		<li>Left click your mouse to the beginning of the line, then move to the next point, a blue line will be following your move.</li>
		<li>Left click on the second point, that is your first line.</li>
		<li>You can continue drawing more connected lines as move around and click.</li>
		<li>To end your drawing click second time on the last point, the line will turn red.</li>
		<li>If you make any mistake, click on the clear button below and start all over again.</li>
		<li>If you want to draw a point, click on "Point" option button below.</li>
		<li>Now click on the location you want, you can click multiple places. </li>
		<li>When you are done, click on "Save Map Data" button.</li>
	</ul>
</div>
<br />
<a href="<s:property value='#application.url' />project.action?id=<s:property value='mapItem.id' />&action=Edit"> Project <s:property value="mapItem.name" /></a>		
<s:form action="map" id="form_id" method="post">
	<s:hidden name="id" value="%{id}" />
	<s:hidden name="mapItem.geometry" id="geometry" value="%{mapItem.geometry}" />
	<div id="map" class="mapPanel"></div>
	<div id="mapTools" class="mapTools">
    <button type="button" id="LineString">Line</button>
    <button type="button" id="Point">Point</button>
    <button type="button" id="clearFeaturesButton">Clear</button>
	</div>
	<div id="marker"></div>
	<s:submit name="action" type="button" value="Save Map Data" class="fn1-btn"/>

</s:form>
<script type="text/javascript" src="<s:property value='#application.url' />js/ol.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/ol3-popup.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/showMap.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/mapEdit.js"></script>

<%@  include file="footer.jsp" %>
