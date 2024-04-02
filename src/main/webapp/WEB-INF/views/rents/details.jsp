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
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">Reservation id: ${reservation.id}<br>Debut: ${reservation.debut}<br>Fin: ${reservation.fin}</h3>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#client" data-toggle="tab">Client</a></li>
                        </ul>
                        <div class="tab-content">
                           <div class="active tab-pane" id="client">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th>email</th>
                                            <th>Date de Naissance</th>
                                        </tr>
                                        <tr>
                                            <td>${reservation.client.id}.</td>
                                            <td>${reservation.client.nom}</td>
                                            <td>${reservation.client.prenom}</td>
                                            <td>${reservation.client.email}</td>
                                            <td>${reservation.client.naissance}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#vehicle" data-toggle="tab">Vehicle</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="vehicle">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Marque</th>
                                            <th>Modele</th>
                                            <th>Nombre de places</th>
                                        </tr>
                                        <tr>
                                            <td>${reservation.vehicle.id}.</td>
                                            <td>${reservation.vehicle.constructeur}</td>
                                            <td>${reservation.vehicle.modele}</td>
                                            <td>${reservation.vehicle.nb_places}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
