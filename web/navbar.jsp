<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-light navbar-expand-md navigation-clean">
    <div class="container"><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                    <c:choose>
                        <c:when test="${session.logged == true}">
                            <li class="nav-item" role="presentation">
                                Welcome <c:out value="${loginBean.name}"/>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item" role="presentation"><a class="nav-link" href="login.jsp">Login</a></li>
                            <li class="nav-item" role="presentation"><a class="nav-link" href="register.jsp">Registrar</a></li>
                        </c:otherwise>
                    </c:choose>

            </ul>
        </div>
    </div>
</nav>