<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'CssJs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/maps/jquery-jvectormap-2.0.3.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/bootstrap-progressbar-3.3.4.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/custom.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/dropzone.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/green.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/jquery.mCustomScrollbar.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/jqvmap.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/nprogress.css" type="text/css"></link>
 
  </head>
  
  <body>
  </body>
  
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/datepicker/daterangepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/moment/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/custom.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/dropzone.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/fastclick.js"></script>
 	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/nprogress.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/validator.js"></script>
	
	
</html>
