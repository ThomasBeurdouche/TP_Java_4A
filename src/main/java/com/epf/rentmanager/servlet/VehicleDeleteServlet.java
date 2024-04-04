package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
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

@WebServlet("/vehicles/delete")
public class VehicleDeleteServlet extends HttpServlet {

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
                for(Reservation reservation : reservationService.findByVehicleId(vehicleId)){
                    reservationService.delete(reservation);
                }
                vehicleService.delete(vehicleService.findById(vehicleId));
            }
            response.sendRedirect(request.getContextPath() + "/vehicles");
        }catch (ServiceException e) {
            System.out.println("Servlet Delete doGet Vehicle : "+e.getMessage());
        }
    }
}