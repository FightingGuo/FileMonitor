<%@ page import="java.util.Map" %>
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
    <%
        pageContext.setAttribute("project_path", request.getContextPath());
    %>

    <!--
        web路径：
    不以/开始的相对路径，找资源，以当前资源路径为基准  容易出问题
    以/开始的相对路径，找资源，以服务器的路径(从webapp下开始)为标准(http://localhost:3306) 需要加上项目名才能找到
    -->
    <!--先引入js(注意jquery版本要再1.9.1以上) 再引入bootstrap的css-->
    <script type="text/javascript" src="${project_path}/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${project_path}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${project_path}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
</head>
<body>

<!--保存按钮弹出的模态框 Modal -->
<div class="modal fade" id="modalAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabelAdd">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_input" placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input"  class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_input" placeholder="email@ghc.163">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input" class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <!--checked=checked设置默认选中状态-->
                                <input type="radio" name="gender" id="gender_add1" value="M" >男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender_add2" value="F" checked="checked">女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input" class="col-sm-2 control-label">depart_name</label>
                        <div class="col-sm-4">
                            <!--部门提交部门id即可-->
                            <select  class="form-control" name="dId" id="departName_add"></select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save_emp">保存</button>
            </div>
        </div>
    </div>
</div>

<!--编辑按钮弹出的模态框-->
<!-- Modal -->
<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabelEdit">更新员工信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input"  class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_edit" placeholder="email@ghc.163">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input" class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <!--checked=checked设置默认选中状态-->
                                <input type="radio" name="gender" id="gender_edit1" value="M" >男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender_edit2" value="F" checked="checked">女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input" class="col-sm-2 control-label">depart_name</label>
                        <div class="col-sm-4">
                            <select  class="form-control" name="dId" id="departName_edit"></select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update_emp">更新</button>
            </div>
        </div>
    </div>
</div>



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
        <button type="button" class="btn btn-success" id="emp_add">新增</button>
        <button type="button" class="btn btn-danger" id="emp_deleteAll">删除</button>
    </div>
</div>


<!--显示表格数据-->
<div class="row">
    <div class="col-md-12">
        <table class="table table-hover" id="emps_table">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" id="checkAll">
                </th>
                <th>#</th>
                <th>empName</th>
                <th>gender</th>
                <th>email</th>
                <th>department_name</th>
                <th>操作</th>
            </tr>
            </thead>

            <!--用jquery函数和ajax异步请求获取 员工数据-->
            <tbody>

            </tbody>

        </table>
        </div>
    </div>
    <!--显示分页信息-->
    <div class="row">
        <!--分页文字信息-->
        <div class="col-md-6" id="page_information_area"></div>
        <!--分页条信息-->
        <div class="col-md- col-md-offset-7" id="page_nave_area">

        </div>
    </div>
</div>


    <script type="text/javascript">
        //totalPages总记录数  currentNum当前页码
        var totalPages,currentNum,pageSize;


        //重置表单函数(表单数据和样式)
        function resetForm(ele) {
            //清空表单内容
            $(ele)[0].reset();
            //清空表单样式  find(*) 找到表单下的所有元素 清空样式
            $(ele).find("*").removeClass("has-success has-error");
            //清空信息提示框的文本
            $(ele).find(".help-block").text("");
        }
        
        
        <!--点击新增绑定事件-->
            $("#emp_add").click(function () {
                //在每次点击新增之前完整重置表单数据(包括数据和样式)  调用dom对象 的rest方法清空表单数据
              resetForm("#modalAdd form");

                //发送ajax请求，查出部门信息，显示再下拉列表中
                getDepartName("#departName_add");

                //弹出模态框
                $("#modalAdd").modal({
                    backdrop:"static"
                });
            });

        //下拉列表函数
        function getDepartName(ele) {
            //查询部门之前先清空原来查询到的
            $(ele).empty();
            //模态框中填写的表单数据传给服务器保存
            $.ajax({
                url:"${project_path}/depts",
                type:"GET",
                success:function (result) { //服务器响应给浏览器的请求
                    $.each(result.map.deptList,function (index,item) {
                       var depart_name=$("<option></option>").append(item.deptName).attr("value",item.deptId);
                       depart_name.appendTo(ele);
                    });
                }
            });
        }


        //1.加载完成以后，直接出去发送一个ajax请求，要到第一页的分页数据  因为首页不需要点击就的显示数据  所以要在函数加载完成后调用
       $(function () {
           to_page(1);
       });


       //点击分页条后跳转函数
        function to_page(pageNum) {
            $.ajax({
                url:"${project_path}/emps",
                data:"pageNum="+pageNum,
                type:"GET",
                success:function (result) { //服务器响应给浏览器的请求
                    //1.解析并显示员工数据
                    build_emps_table(result);

                    //2.解析显示分信息
                    build_page_nav_information(result);

                    //3.解析并显示条
                    build_page_nav(result);

                }
            });
        }


        //解析员工数据
        function build_emps_table(result) {
            //应为每一次ajax请求都是向表格添加数据 所以每次添加前都应先清空表格数据
            $("#emps_table tbody").empty();

            var emps=result.map.pageInfo.list;
            $.each(emps,function (index,item) {
                // alert(items.empName)
                var checkBoxTd=$("<td><input type='checkbox' class='check_item'></td>");
                var empIdTd=$("<td></td>").append(item.empId);
                var empNameTd=$("<td></td>").append(item.empName);
                var genderTd=$("<td></td>").append(item.gender=="M"?"男":"女");
                var emailTd=$("<td></td>").append(item.email);
                    var deptNameTd=$("<td></td>").append(item.dept.deptName);

                var editBtn=$("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑")
                    //为编辑按钮添加一个自定义属性  用于根据id查找用户
                    .attr("edit_id",item.empId);
                var removeBtn=$("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除")
                    //为删除按钮添加一个自定义属性  用于根据id删除用户
                    .attr("delete_id",item.empId);
                var btnTd=$("<td></td>").append(editBtn).append(removeBtn)
                // append方法执行完以后还是返回原来的元素
                $("<tr></tr>")
                    .append(checkBoxTd)
                    .append(empIdTd)
                    .append(empNameTd)
                    .append(genderTd)
                    .append(emailTd)
                    .append(deptNameTd)
                    .append(btnTd)
                    .appendTo("#emps_table tbody");
            });
        }

        //解析分页信息
        function build_page_nav_information(result){
            $("#page_information_area").empty();
            var emps=result.map.pageInfo;
            $("#page_information_area").append("当前第"+emps.pageNum+"页,共有"+emps.pages+"页,总计"+emps.total+"条记录数");
            totalPages=emps.total;
            currentNum=emps.pageNum;
            pageSize=emps.pageSizes;
        }

        //解析分页条
        function build_page_nav(result) {
            $("#page_nave_area").empty();

            //分页信息
            var emps=result.map.pageInfo;
            //分页信息里 具体的分页导航数据
            var pages = result.map.pageInfo.navigatepageNums;
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
            var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
            var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
            var lastPageLi= $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));

            var ul=$("<ul></ul>").addClass("pagination");

           //添加首页和前一页
            ul.append(firstPageLi).append(prePageLi);

            //如果没有上一页 就设置首页和上一页不可点击
            if (emps.hasPreviousPage == false){
                firstPageLi.addClass("disabled");
                prePageLi.addClass("disabled");
            }else{
                //首页绑定单机事件
                firstPageLi.click(function (extend) {
                    to_page(1);
                });


                //上一页绑定单机事件
                prePageLi.click(function () {
                    //页码不能小于0   不用手动写啦 pageHelper有个很吊的参数叫reasonable  设置为true
                    // if(emps.pageNum-1>0) {
                    to_page(emps.pageNum - 1);
                    // }
                });
            }

            //如果没有最后一页 就设置 末页和下一页不可点击
            if (emps.hasNextPage == false){
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            }else {
                //如果没禁用再绑定单机事件
                //下一页绑定单机事件
                nextPageLi.click(function () {
                    to_page(emps.pageNum+1);
                });

                //末页绑定单机事件
                lastPageLi.click(function (extend) {
                    to_page(emps.pages);
                });
            }

           //遍历页码号 {1,2,3,4,5}
           $.each(pages,function (index,item) {
               var numLi= $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
               if (emps.pageNum==item) {
                   //如果遍历的页码是当前页 就加上高亮
                   numLi.addClass("active");
               }
               numLi.click(function () {
                   to_page(item);
               })
               ul.append(numLi);
           });

            //添加后一页和末页 把数据加入到分页条里
                ul.append(nextPageLi).append(lastPageLi);

            //把ul标签加入nav标签  其实就是一层一层 appendTo
           var nav=$("<nav></nav>").append(ul).appendTo("#page_nave_area");

        }
        
        //检验表单数据函数
        function show_validate_msg(ele,status,msg) {
            //清除当前元素的校验状态
            $(ele).parent().removeClass("has-success has-error");
            $(ele).next("span").text("");

            if ("success"== status){
                $(ele).parent().addClass("has-success");
                $(ele).next("span").text(msg);
            }else if("error"==status){
                $(ele).parent().addClass("has-error");
                $(ele).next("span").text(msg);
            }

        }

        //校验表单数据
        function validate_add_emp() {
            //拿到要校验的数据,使用正则表达式
            //1.校验用户名
            var empName=$("#empName_input").val();
            var regName=/(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/; //用户名可包含 小写a-z 大写A-Z 0-9 下划线(_)  -
            if(!regName.test(empName)){
                show_validate_msg("#empName_input","error","用户名可以是2-5为中文 或6-16位英文");
                return false;
            }else{
               show_validate_msg("#empName_input","success","");
            }

            //2.校验邮箱
            var empEmail=$("#email_input").val();
            var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if(!regEmail.test(empEmail)){
                show_validate_msg("#email_input","error","邮箱的格式位×××@×××.com");
                return false;
            }else{
                show_validate_msg("#email_input","success","")
            }
            return  true;
        }

        //给员工姓名绑定事件 做数据库校验
        $("#empName_input").change(function () {
            var empName=this.value;
            //发送ajax请求区服务器校验 用户名是否可用
           $.ajax({
            url:"${project_path}/emp",
               data:"empName="+empName,
               type:"GET",
            success:function (result) {
               if (result.code==100) {
                   show_validate_msg("#empName_input","success","用户名可用");
                   //如果可用 给保存按钮添加一个属性 在保存按钮发送ajax去服务器保存之前做校验
                   $("#save_emp").attr("ajax-va","success");
               }else{
                   show_validate_msg("#empName_input","error",result.map.vaMsg);
                   $("#save_emp").attr("ajax-va","fail");
               }
            }   
           });

        });

        
        //给保存添加单机事件
        $("#save_emp").click(function () {
            //保存前先进行 前端校验 看用户名是否符合标准
            if(!validate_add_emp()){
                return false;
            }

            //判断是否检验成功 可保存ajax-va=success  不可保存ajax-va=fail
            var info=$(this).attr("ajax-va")
            if("fail"==info){
                show_validate_msg("#empName_input","error","用户名已重复,请重新输入！");
                return false;
            }

            <!--传入HiddenHttpMethodFilter 的参数_method 删除=delete  添加=post  修改=put-->
            // _method="post",
                <!--date:传给服务器的数据类型  $("#modalAdd form").serialize() 把表格数据序列化为 key=value形式-->
                $.ajax({
                    url:"${project_path}/emp",
                    type:"POST",
                    data:$("#modalAdd form").serialize(),
                    success:function (result) { //服务器响应给浏览器的请求
                        //判断服务器传来的消息 是否可保存 100:成功  200:失败
                        if(result.code==200){
                            //有哪个字段的错误信息就显示哪个字段的
                            if (result.map.errorField){
                                if (undefined!=result.map.errorField.empName){
                                    show_validate_msg("#empName_input","error",result.map.errorField.empName);
                                }
                                if (undefined!=result.map.errorField.email){
                                    show_validate_msg("#email_input","error",result.map.errorField.email)
                                }
                            }
                            //显示失败信息
                            // console.log(result);
                        }else if(result.code==100){
                            //员工保存成功后
                            //1.关闭模态框
                            $("#modalAdd").modal('hide');
                            //2.来到最后一页
                            //发送ajax请求显示,跳转到最后一页+1  有pageHelper插件 敢敢找大于页码数 只会找到最后一页
                            to_page(totalPages);
                        }
                    }
                });
        });

        //根据id去查这个员工的信息并回显
        function getEmp(id) {
            $.ajax({
                url:"${project_path}/emp/"+id,
                type:"GET",
                success:function (result) {
                    // console.log(result);
                    var emp=result.map.emp;
                    $("#empName_update_static").text(emp.empName);
                    $("#email_edit").val(emp.email);
                    $("#modalEdit input[name=gender]").val([emp.gender]);
                    $("#modalEdit select[name=dId]").val([emp.dId]);
                }

            });
        }
        

        //给编辑按钮添加单机事件
        /**
         * js代码在页面加载完成后就执行 而表格内的数据是在页面加载完成之后发送ajax请求得到
         * 所以是在按钮创建之前就绑定了事件 所以点击事件绑定不成功
         * 1.可以在创建按钮的时候绑定事件
         * 2.绑定点击.live() jquery新版没有live，使用on进行替代
         */
        $(document).on("click",".edit_btn",function () {
            //弹出模态框之前也要先查出部门信息
            getDepartName("#departName_edit");

            //回显员工信息
            var id=$(this).attr("edit_id");
            getEmp(id);

            //把员工的id传递给更新按钮  在由更新按钮传递给服务器
            $("#update_emp").attr("id",id);

            //弹出模态框
            $("#modalEdit").modal({
                backdrop:"static"
            });

        });


        //点击更新按钮后去数据库更新信息
        $("#update_emp").click(function () {

            //验证输入的邮箱是否合法
            var empEmail=$("#email_edit").val();
            var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if(!regEmail.test(empEmail)){
                show_validate_msg("#email_edit","error","邮箱的格式位×××@×××.com");
                return false;
            }else{
                show_validate_msg("#email_edit","success","")
            }

            //通过编辑按钮拿到当前更新的id 再把id传到服务器
            var empId=$(this).attr("id");

            //发送ajax请求保存更新
            $.ajax({
                url:"${project_path}/emp/"+empId,
                type:"POST",
                data:$("#modalEdit form").serialize()+"&_method=put",
                success:function (result) {
                    // alert(result.msg);

                    //关闭模态框并跳转到更新页
                $("#modalEdit").modal('hide');

                to_page(currentNum);
                }
            });
        });

        //根据id删除 给删除按钮绑上单机事件
        $(document).on("click",".delete_btn",function () {
            //弹出是否确认删除对话框 eq(1)找到tr的第二个td 是empName
            var empName=$(this).parents("tr").find("td:eq(2)").text();
            if(confirm("确认删除【"+empName+"】")){
                //点击确认 发送ajax请求删除
                //拿到当前删除的这个员工的id
                var id=$(this).attr("delete_id");
                $.ajax({
                    url:"${project_path}/emp/"+id,
                    type:"POST",
                    data:"_method=delete",
                    success:function (result) {
                        alert(result.msg);
                        //删除成功后，返回当前页
                        to_page(currentNum);
                    }
                });
            }
        });

        //给全选/全不选按钮绑定单机事件
        $("#checkAll").click(function () {
           //attr获取checked是undefined;
            //dom原生属性用prop获取属性  attr用于获取自定义的属性
            // alert($(this).prop("checked"));
            //设置所有的checked 的状态和全选框的状态一致
            $(".check_item").prop("checked",$(this).prop("checked"));

        });

        //为每个复选框设置单机事件  当所有复选框被选中时，全选框也要选中
        $(document).on("click",".check_item",function () {
            //判断当前选择中的元素是否有等于当前页面的复选框个数个
            var flag=$(".check_item:checked").length==$(".check_item").length;
                $("#checkAll").prop("checked",flag);
        });


        //点击删除，执行批量删除
        $("#emp_deleteAll").click(function () {

            //循环取出复选框选中的员工的name 拼接到empNames
            var empNames="";
            var empId="";
            $.each($(".check_item:checked"),function () {
                //组装员工姓名的字符串
                empNames+=$(this).parents("tr").find("td:eq(2)").text()+",";
                //组装员工id的字符串
                empId+=$(this).parents("tr").find("td:eq(1)").text()+"-";
            });

            //取出empNames中多余的, 和empId中多余的-
            empNames=empNames.substring(0,empNames.length-1);
            empId=empId.substring(0,empId.length-1);
            if (confirm("确认删除【"+empNames+"】吗?")){
                //确认以后发送 ajax请求 到数据库删除
                $.ajax({
                    url:"${project_path}/emp/"+empId,
                    type:"POST",
                    data:"_method=delete",
                    success:function (result) {
                        alert(result.msg);
                        //回到当前页面
                        to_page(totalPages);
                    }
                });
            }


        });



    </script>



</body>
</html>
