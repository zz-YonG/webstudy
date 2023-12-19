<%--
  Created by IntelliJ IDEA.
  User: 郭智勇
  Date: 2023/12/18
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <title>银行账户转账</title>
  </head>
  <body>
  </body>
  <form action="transfer" method="post">
    转出账户：<input type="text" name="fromActno"><br>
    转入账户：<input type="text" name="toActno"><br>
    转账金额：<input type="text" name="money"><br>
    <input type="submit" value="转账">
  </form>
</html>
