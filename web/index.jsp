<%--
  Created by IntelliJ IDEA.
  User: tomorrow
  Date: 2019/4/18
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
</head>
<body><center>
  <h1>文件上传</h1>
  <form action="/FileServlet" method="get" enctype="multipart/form-data" >
    文件<input type = "file" name = "file"/><br>
    文件名<input type = "text" name = "name"/>
    <input type = "submit" value = "提交">



  </form>



</center>

</body>
</html>
