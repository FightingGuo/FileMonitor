<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/4/29
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工列表</title>
    ${
          pageContext.setAttribute("project_path", request.getContextPath())
    }

    <!--
        web路径：
    不以/开始的相对路径，找资源，以当前资源路径为基准  容易出问题
    以/开始的相对路径，找资源，以服务器的路径(从webapp下开始)为标准(http://localhost:3306) 需要加上项目名才能找到

    -->

    <link rel="stylesheet" href="${project_path}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
    <script src="${project_path}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${project_path}/static/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<!--搭建显示页面-->
<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-lg-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-4"></div>
        <button type="button" class="btn btn-primary">修改</button>
        <button type="button" class="btn btn-danger">删除</button>
    </div>
</div>


<!--没用ajax json的 只使用与 浏览器端与 服务器端交互   -->
<!--显示表格数据-->
<div class="row">
    <div class="col-md-12">
        <table class="table table-hover">
            <tr>
                <th>#</th>
                <th>empName</th>
                <th>gender</th>
                <th>email</th>
                <th>department_name</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${pageInfo.list}" var="emp">
                <tr>
                    <td>${emp.empId}</td>
                    <td>${emp.empName}</td>
                    <td>${emp.gender="M"?"男":"女"}</td>
                    <td>${emp.email}</td>
                    <td>${emp.dept.deptName}</td>
                    <td>
                        <button class="btn btn-primary btn-sm">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            编辑
                        </button>
                        <button class="btn btn-danger btn-sm">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div/>
    </div>
    <!--显示分页信息-->
    <div class="row">
        <!--分页文字信息-->
        <div class="col-md-6">
            当前第${pageInfo.pageNum}页,共有${pageInfo.pages},总计${pageInfo.total}条记录数
        </div>
        <!--分页条信息-->
        <div class="col-md- col-md-offset-7">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li type="hidden"><a href="${project_path}/emps?pageNum=1">首页</a></li>

                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${project_path}/emps?pageNum=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                        <!--判断当前遍历到的pageNum是否为当前页面的pageNum 如果是 则设置样式高亮显示-->
                        <c:if test="${pageNum==pageInfo.pageNum}">
                            <li class="active"><a href="#" class="disabled">${pageNum}</a></li>
                        </c:if>
                        <c:if test="${pageNum!=pageInfo.pageNum}">
                            <li><a href="${project_path}/emps?pageNum=${pageNum}">${pageNum}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pageInfo.hasNextPage}">
                    <li>
                        <a href="${project_path}/emps?pageNum=${pageInfo.pageNum+1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    </c:if>
                        <li><a href="${project_path}/emps?pageNum=${pageInfo.pages}">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>

</div>

</body>
</html>
