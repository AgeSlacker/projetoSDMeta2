<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06/12/2019
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results page</title>
</head>
<body>
    <p>Results page</p>
    <s:iterator value="searchBean.searchResults" >
        <a href="<s:property value="url"></s:property>"><p><s:property value="name"></s:property> </p></a>
        <p><s:property value="description"></s:property> </p>
    </s:iterator>
</body>
</html>
