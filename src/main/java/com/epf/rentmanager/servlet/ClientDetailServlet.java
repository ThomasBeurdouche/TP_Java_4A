package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String idParameter = request.getParameter("id");
            if (idParameter != null && !idParameter.isEmpty()) {
                long userId = Long.parseLong(idParameter);
                List<Vehicle> vehicles = new ArrayList<>();
                request.setAttribute("client", clientService.findById(userId));
                request.setAttribute("reservations", reservationService.findByClientId(userId));
                request.setAttribute("nb_reservation", reservationService.findByClientId(userId).size());
                for (Reservation reservation : reservationService.findByClientId(userId)){
                        boolean presence =false;
                        for (Vehicle vehicle : vehicles){
                            if (vehicle.getId() == reservation.getVehicle().getId()) {
                                presence = true;
                                break;
                            }
                        }
                        if (!presence)vehicles.add(reservation.getVehicle());
                }
                request.setAttribute("vehicles", vehicles);
                request.setAttribute("nb_voiture", vehicles.size());
            } else {
                System.out.println("Id absent des paramètres");
                response.sendRedirect(request.getContextPath() + "/users"); //TODO
            }
        }catch (ServiceException e) {
            System.out.println("Servlet doGet Client detail : "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
