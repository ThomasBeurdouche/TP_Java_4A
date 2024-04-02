package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vehicles/details")
public class VehicleDetailServlet extends HttpServlet {

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
            String idParameter = request.getParameter("id");
            if (idParameter != null && !idParameter.isEmpty()) {
                long vehicleId = Long.parseLong(idParameter);
                List<Client> clients = new ArrayList<>();
                request.setAttribute("vehicle", vehicleService.findById(vehicleId));
                request.setAttribute("reservations", reservationService.findByVehicleId(vehicleId));
                request.setAttribute("nb_reservation", reservationService.findByVehicleId(vehicleId).size());
                for (Reservation reservation : reservationService.findByVehicleId(vehicleId)){
                    boolean presence =false;
                    for (Client client : clients){
                        if (client.getId() == reservation.getClient().getId()) {
                            presence = true;
                            break;
                        }
                    }
                    if (!presence)clients.add(reservation.getClient());
                }
                request.setAttribute("clients", clients);
                request.setAttribute("nb_client", clients.size());
            } else {
                System.out.println("Id absent des paramètres");
                response.sendRedirect(request.getContextPath() + "/vehicles"); //TODO
            }
        }catch (ServiceException e) {
            System.out.println("Servlet doGet Vehicle detail : "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
