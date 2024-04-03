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
                Voitures
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/vehicles/modify">
                            <input type="hidden" id="ID" name="ID" value="${vehicle.id}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="manufacturer" class="col-sm-3 control-label">Marque: ${vehicle.constructeur}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="manufacturer" name="manufacturer" placeholder="Marque" value="${vehicle.constructeur}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modele" class="col-sm-3 control-label">Modele: ${vehicle.modele}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="modele" name="modele" placeholder="Modele" value="${vehicle.modele}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="seats" class="col-sm-3 control-label">Nombre de places: ${vehicle.nb_places}</label>

                                    <div class="col-sm-9">
                                        <input type="number" class="form-control" id="seats" pattern="/d+" name="seats" placeholder="Nombre de places" value="${vehicle.nb_places}" required>
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
</body>
</html>
