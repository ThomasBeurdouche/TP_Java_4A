package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
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

@WebServlet("/vehicles/modify")
public class VehicleModifyServlet extends HttpServlet {

    @Autowired
    private VehicleService vehicleService;

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
                request.setAttribute("vehicle", vehicleService.findById(userId));
            }else {
                System.out.println("Id absent des paramètres");
            }

        }catch (ServiceException e) {
            System.out.println("Servlet doGet Vehicle Modify: "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/modify.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        String nb_placesString = request.getParameter("seats");
        int nb_places = Integer.parseInt(nb_placesString);
        String idParameter = request.getParameter("ID");
        long vehicleId = Long.parseLong(idParameter);

        Vehicle newVehicle = new Vehicle(vehicleId,constructeur,modele,nb_places);
        try {
            vehicleService.modify(newVehicle);
            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Vehicle modify dopost servlet : " + e.getMessage());
        }
    }
}