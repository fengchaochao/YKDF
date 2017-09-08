<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代付产品示例</title>
  <link rel="stylesheet" href="static/jquery-ui.min.css">
  <script src="static/jquery-1.11.2.min.js"></script>
  <script src="static/jquery-ui.min.js"></script>
  <script src="static/demo.js"></script>
  <script>
  	$(function() {
	    setApiDemoTabs("#tabs-df");
	    setApiDemoTabs("#tabs-dfBatch");
	  });
  </script>
  <link rel="stylesheet" href="static/demo.css">
</head>

<body style="background-color:#e5eecc;">
<div id="wrapper">
<div id="header">
<h2>代付产品示例</h2>

</div>

<div id="tabs-api">
  <ul>
    <li><a href="#tabs-api-1">前言</a></li>
    <li><a href="#tabs-api-2">代付样例</a></li>
    <li><a href="#tabs-api-3">批量代付样例</a></li>
    <li><a href="#tabs-api-4">常见开发问题</a></li>
  </ul>
  
  <div id="tabs-api-1">
    <jsp:include  page="/pages/introduction.jsp"/>
  </div>

  <div id="tabs-api-2">
	<div id="tabs-df">
	  <ul>
	    <li><a href="#tabs-df-1">说明</a></li>
	    <li><a href="pages/df.jsp">代付</a></li>
	    <!--
	    <li><a href="pages/realAuth_back.jsp">实名认证（后台）</a></li>
	     <li><a href="pages/realAuth_front.jsp">实名认证（前台）</a></li>
	     -->
		<li><a href="pages/query.jsp">交易状态查询</a></li>
		<li><a href="pages/file_transfer.jsp">对账文件下载</a></li>
	  </ul>
	  <div id="tabs-df-1">
	     <jsp:include  page="/pages/df_intro.jsp"/>
	  </div>
	</div>
  </div>
  <div id="tabs-api-3">
		<div id="tabs-dfBatch">
		  <ul>
		    <li><a href="#tabs-dfBatch-1">说明</a></li>
		    <li><a href="pages/batch_df.jsp">批量代付</a></li>
	    	<li><a href="pages/batch_df_query.jsp">批量付收查询</a></li>
	    	<li><a href="pages/file_transfer.jsp">对账文件下载</a></li>
		  </ul>
		  <div id="tabs-dfBatch-1">
	        <jsp:include  page="/pages/batch_df_intro.jsp"/>
	      </div>
		</div>
  </div>
    
  <div id="tabs-api-4">
    <jsp:include  page="/pages/devlopHelp.jsp"/>
  </div>
	  
  </div> <!-- end of tabs-api-->
</div><!-- end of wrapper-->
 
 
</body>
</html>