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
                Modification Reservation
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="/rentmanager/rents/modify">
                            <input type="hidden" id="ID" name="ID" value="${reservation.id}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-3 control-label">Vehicle: ${reservation.vehicle.constructeur} ${reservation.vehicle.modele}</label>

                                    <div class="col-sm-9">
                                        <select class="form-control" id="car" name="car">
                                            <c:forEach items="${vehicles}" var="vehicle">
                                                <option value=${vehicle.id} >${vehicle.constructeur} ${vehicle.modele}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-3 control-label">Client: ${reservation.client.prenom} ${reservation.client.nom}</label>

                                    <div class="col-sm-9">
                                        <select class="form-control" id="client" name="client">
                                            <c:forEach items="${clients}" var="client">
                                                <option value=${client.id} >${client.nom} ${client.prenom}</option>
                                            </c:forEach>
                                        </select>
                                     </div>
                                </div>
                                <div class="form-group">
                                    <label for="debut" class="col-sm-3 control-label">Debut: ${debut}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="debut" name="debut" placeholder="dd/mm/yyyy" value="${debut}" required
                                            data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="fin" class="col-sm-3 control-label">Fin: ${fin}</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="fin" name="fin" placeholder="dd/mm/yyyy" value="${fin}" required
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
