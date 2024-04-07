package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String message = request.getParameter("message");
            if (message != null && !message.isEmpty()) {
                request.setAttribute("message", message);
            }
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());

        }catch (ServiceException e) {
            System.out.println("Servlet doGet Reservation create : "+e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idClientString = request.getParameter("client");
        long idClient = Long.parseLong(idClientString);
        String idVehicleString = request.getParameter("car");
        long idVehicle = Long.parseLong(idVehicleString);
        String debutString = request.getParameter("begin");
        LocalDate debut = LocalDate.parse(debutString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String finString = request.getParameter("end");
        LocalDate fin = LocalDate.parse(finString,DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        boolean respectContrainte=true;

        try {
            for(Reservation reservation : reservationService.findByVehicleId(idVehicle)){
                if (!reservation.getDebut().isAfter(fin) && !reservation.getFin().isBefore(debut)) {
                    respectContrainte = false;
                    break;
                }
            }


            boolean jourVide=false;
            int jourConsecutif = 0;
            for (int i=1;i<=30;i++){
                jourVide=true;
                for(Reservation reservation : reservationService.findByVehicleId(idVehicle)){
                    if (fin.plusDays(i).isEqual(reservation.getDebut()) || fin.plusDays(i).isEqual(reservation.getFin()) || (fin.plusDays(i).isBefore(reservation.getFin()) && fin.plusDays(i).isAfter(reservation.getDebut()))) {
                        jourVide = false;
                        break;
                    }
                }
                if(jourVide){
                    jourConsecutif+=i-1;
                    break;
                }
            }
            if (!jourVide)respectContrainte=false;


            for (int i=1;i<=30;i++){
                jourVide=true;
                for(Reservation reservation : reservationService.findByVehicleId(idVehicle)){
                    if (debut.minusDays(i).isEqual(reservation.getDebut()) || debut.minusDays(i).isEqual(reservation.getFin()) || (debut.minusDays(i).isBefore(reservation.getFin()) && debut.minusDays(i).isAfter(reservation.getDebut()))) {
                        jourVide = false;
                        break;
                    }
                }
                if(jourVide){
                    jourConsecutif+=i-1;
                    break;
                }
            }
            if (!jourVide)respectContrainte=false;

            jourConsecutif+= (int)ChronoUnit.DAYS.between(debut,fin);
            if (jourConsecutif>=30)respectContrainte=false;



            jourVide=false;
            jourConsecutif = 0;

            List<Reservation> reservations = new ArrayList<>();

            for(Reservation reservationVehicle : reservationService.findByVehicleId(idVehicle)){
                for(Reservation reservationClient : reservationService.findByVehicleId(idClient)){
                    if (reservationClient.getId()==reservationVehicle.getId()) reservations.add(reservationClient);
                }
            }

            for (int i=1;i<=7;i++){
                jourVide=true;
                for(Reservation reservation : reservations){
                    if (fin.plusDays(i).isEqual(reservation.getDebut()) || fin.plusDays(i).isEqual(reservation.getFin()) || (fin.plusDays(i).isBefore(reservation.getFin()) && fin.plusDays(i).isAfter(reservation.getDebut()))) {
                        jourVide = false;
                        break;
                    }
                }
                if(jourVide){
                    jourConsecutif+=i-1;
                    break;
                }
            }
            if (!jourVide)respectContrainte=false;

            for (int i=1;i<=7;i++){
                jourVide=true;
                for(Reservation reservation : reservations){
                    if (debut.minusDays(i).isEqual(reservation.getDebut()) || debut.minusDays(i).isEqual(reservation.getFin()) || (debut.minusDays(i).isBefore(reservation.getFin()) && debut.minusDays(i).isAfter(reservation.getDebut()))) {
                        jourVide = false;
                        break;
                    }
                }
                if(jourVide){
                    jourConsecutif+=i-1;
                    break;
                }
            }
            if (!jourVide)respectContrainte=false;

            jourConsecutif+= (int)ChronoUnit.DAYS.between(debut,fin);
            if (jourConsecutif>=7)respectContrainte=false;

            if (respectContrainte){
            Reservation newReservation = new Reservation(clientService.findById(idClient),vehicleService.findById(idVehicle),debut,fin);
            reservationService.create(newReservation);
            response.sendRedirect(request.getContextPath() + "/rents");
            }else {
                response.sendRedirect(request.getContextPath() + "/rents/create");
            }
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Servlet Create Reservation DoPost : " + e.getMessage());
        }
    }
}
