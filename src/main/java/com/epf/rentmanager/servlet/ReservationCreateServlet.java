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
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());

        }catch (ServiceException e) {
            System.out.println("Servlet doGet Vehicle create : "+e.getMessage());
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



        try {
            Reservation newReservation = new Reservation(clientService.findById(idClient),vehicleService.findById(idVehicle),debut,fin);
            reservationService.create(newReservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la création de la réservation : " + e.getMessage());
        }
    }
}
