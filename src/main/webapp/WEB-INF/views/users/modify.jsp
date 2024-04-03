<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Modification Utilisateur
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/users/modify">
                            <input type="hidden" id="ID" name="ID" value="${client.id}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-3 control-label">Nom: ${client.nom}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Nom" value="${client.nom}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-3 control-label">Prenom: ${client.prenom}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="first_name" name="first_name" placeholder="Prenom" value="${client.prenom}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-3 control-label">Email: ${client.email}</label>

                                    <div class="col-sm-9">
                                        <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${client.email}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birthday" class="col-sm-3 control-label">Date de Naissance: ${naissance}</label>

                                    <div class="col-sm-9">
                                        <input type="birthday" class="form-control" id="birthday" name="birthday" placeholder="dd/mm/yyyy" value="${naissance}" required
                                            data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
