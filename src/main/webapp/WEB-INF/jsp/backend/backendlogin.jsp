<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Gentellela Alela! | </title>

  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="${pageContext.request.contextPath }/login/BackendUserLogin" method="post">
              <h1>后台管理系统</h1>
              <div>${exception.message }</div>
              <div>
                <input type="text" id="userCode" name="userCode" class="form-control" placeholder="请输入用户名" required="" />
              </div>
              <div>
                <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="请输入密码" required="" />
              </div>
              <div>
				<button type="submit" class="btn btn-success">登     录</button>
              	<input id="chongtian" class="btn btn-default" type="button" value="重 填" />
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">
                  ©2016 All Rights Reserved
                </p>

              </div>
            </form>
          </section>
        </div>
        
      </div>
    </div>
  </body>
  
  <%@include file="../../../common/CssJs.jsp" %>
  <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/backendUserlogin.js"></script>
  
</html>