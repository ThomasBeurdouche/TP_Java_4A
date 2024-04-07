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
                Utilisateurs
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form id=formulaireID class="form-horizontal" method="post" action="/rentmanager/users/create">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Nom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="first_name" name="first_name" placeholder="Prenom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>

                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birthday" class="col-sm-2 control-label">Date de Naissance</label>

                                    <div class="col-sm-10">
                                        <input type="birthday" class="form-control" id="birthday" name="birthday" required
                                            data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
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

    function preventFormSubmit(event) {
        var message = "";

        var birthdayString = document.getElementById("birthday").value;
        var birthday = new Date(birthdayString);
        var dateActuelle = new Date();
        var difference = dateActuelle.getTime() - birthday.getTime();
        var age = Math.floor(difference / (1000 * 60 * 60 * 24 * 365.25));

        if (age < 18) {
          message+="L'utilisateur doit avoir plus de 18 ans !\n";
        }

        var nom = document.getElementById("last_name").value;
        var prenom = document.getElementById("first_name").value;

        if (nom.length<3 || prenom.length<3){
          message+="Le nom et le prenom de l'utilisateur doivent faire au moins 3 characteres !\n";
        }

        var email = document.getElementById("email").value;
        var bddEmail = "${bddEmail}";
        if (bddEmail.includes(email)){
          message+="Cette email est deja utilise !\n";
        }

        if(message!=""){
            alert(message)
            event.preventDefault();
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        var form = document.getElementById("formulaireID");
        form.addEventListener("submit", preventFormSubmit);
    });

</script>
</body>
</html>
