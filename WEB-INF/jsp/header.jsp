<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <s:head />
  <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
  <link rel="SHORTCUT ICON" href="https://apps.bloomington.in.gov/favicon.ico" />
  <link rel="stylesheet" href="<s:property value='#application.url' />js/jquery-ui2.css" type="text/css" media="all" />
  <link rel="stylesheet" href="<s:property value='#application.url' />js/jquery.ui.theme.css" type="text/css" media="all" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/open-sans/open-sans.css" type="text/css" />
  <link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/0.2/css/default.css" type="text/css" />
  <link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/0.2/css/kirkwood.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/screen.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/ol.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/ol3-popup.css" type="text/css" />
  <link rel="stylesheet" href="<s:property value='#application.url' />css/print.css" type="text/css" media="print" />	
  <title>CBU Projects</title>
  <script type="text/javascript">
    var APPLICATION_URL = '<s:property value='#application.url' />';
  </script>
</head>
<body class="fn1-body">
  <header class="fn1-siteHeader">
    <div class="fn1-siteHeader-container">
      <div class="fn1-site-title">
        <h1 id="application_name"><a href="<s:property value='#application.url'/>">CBU Projects</a></h1>
        <div class="fn1-site-location" id="location_name"><a href="<s:property value='#application.url'/>">City of Bloomington, IN</a></div>
      </div>
      <s:if test="#session != null && #session.user != null">
        <div class="fn1-site-utilityBar">
          <nav id="user_menu" class="fn1-dropdown">
            <button class="fn1-dropdown-launcher" aria-haspopup="true" aria-expanded="false"><s:property value='#session.user.fullname' /></button>
            <div class="fn1-dropdown-links" aria-haspopup="true" aria-expanded="false">
              <a href="<s:property value='#application.url'/>logout.action" class="fn1-dropdown-link">Logout</a>
            </div>
          </nav>
          <nav id="admin_menu" class="fn1-dropdown">
            <button class="fn1-dropdown-launcher" aria-haspopup="true" aria-expanded="false">Admin</button>
            <div class="fn1-dropdown-links" >
              <s:if test="#session.user.isAdmin()">
								<a href="<s:property value='#application.url'/>type.action" class="fn1-dropdown-link">Field Set</a>
								<a href="<s:property value='#application.url'/>user.action" class="fn1-dropdown-link">Users</a>								
								<a href="<s:property value='#application.url'/>report.action" class="fn1-dropdown-link">Reports</a>
              </s:if>
            </div>
          </nav>
        </div>
	  </s:if>
	</div>

	<div class="fn1-nav1">
      <nav class="fn1-nav1-container">
				<s:if test="#session != null && #session.user != null && #session.user.canEdit()">
					<a href="<s:property value='#application.url'/>project.action">New Project</a>
					<a href="<s:property value='#application.url'/>phase.action">Project Phases</a>
				</s:if>
        <a href="<s:property value='#application.url'/>search.action">Search</a>
				<a href="<s:property value='#application.url'/>timeline.action">Timeline Report</a>				
				<a href="<s:property value='#application.url'/>report.action">Reports</a>
      </nav>
    </div>
  </header>
  <main>
    <div class="fn1-main-container">
