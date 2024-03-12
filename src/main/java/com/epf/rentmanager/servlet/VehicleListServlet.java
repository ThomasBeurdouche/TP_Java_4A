package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/vehicles")
public class VehicleListServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("vehicles", VehicleService.getInstance().findAll());
        }catch (ServiceException e) {
            System.out.println("Servlet doGet Vehicle : "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
	}

    // TODO : A Tester
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nbrPlace = Integer.parseInt(request.getParameter("nb_places"));

            VehicleService.getInstance().create(new Vehicle(constructeur,modele,nbrPlace));

            response.sendRedirect(request.getContextPath() + "/vehicles");
        }catch (ServiceException e) {
            //e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while inserting the vehicle");
        }
    }
}
