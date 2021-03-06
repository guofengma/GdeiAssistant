<%--
  Created by IntelliJ IDEA.
  User: linguancheng
  Date: 2017/10/29
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>400 Bad Request</title>
    <link rel="stylesheet" href="/css/common/weui-1.1.1.min.css">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!-- 如果使用双核浏览器，强制使用webkit来进行页面渲染 -->
    <meta name="renderer" content="webkit"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <script>
        function backToIndex() {
            window.location.href = "/index";
        }
    </script>
</head>
<body>

<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">400 Bad Request</h2>
        <p class="weui-msg__desc">请求参数不合法</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a onclick="backToIndex()" href="javascript:" class="weui-btn weui-btn_primary">返回首页</a>
        </p>
    </div>
</div>

</body>
</html>