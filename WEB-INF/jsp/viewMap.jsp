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
<div id="map" class="mapPanel"></div>
<div id="marker"></div>
<dl class="fn1-output-field">
	<dt>Project</dt>
	<dd>
		<a href="<s:property value='#application.url' />project.action?id=<s:property value='project.id' />"> <s:property value="project.id" /> </a>
	</dd>
</dl>
<dl class="fn1-output-field">
	<dt>Name</dt>
	<dd><s:property value="project.name" /></dd>
</dl>
<dl>
	<dt></dt>
	<dd class="project"> <div id="<s:property value='project.id' />" class="<s:property value='project.firstTypeId' />" ><div class="geography" style="display:none"><s:property value="project.geometry" /></div></div></dd>
</dl>

<script type="text/javascript" src="<s:property value='#application.url' />js/ol.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/ol3-popup.js"></script>
<script type="text/javascript" src="<s:property value='#application.url' />js/showMap.js"></script>

<%@  include file="footer.jsp" %>
