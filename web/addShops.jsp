<%--
  Created by IntelliJ IDEA.
  User: tomorrow
  Date: 2019/4/19
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>添加商品</title>
</head>
<body><div style="text-align: center;">
    <h1>添加商品网页</h1><br>
    <form action="${pageContext.request.contextPath}/addShops" method="post" enctype="multipart/form-data"
          id="form1">
    请输入商品名<label>
        <input type="text" name="nameofshop"/>
    </label><br>
    请上传商品图片<input type="file" name="fileofshop"/><br>
        <input type="submit" value="上传">

</form>
</div>

</body>
</html>
