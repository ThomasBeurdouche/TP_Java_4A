package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet("/rents/details")
public class ReservationDetailServlet extends HttpServlet {

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
            String idParameter = request.getParameter("id");
            if (idParameter != null && !idParameter.isEmpty()) {
                long reservationId = Long.parseLong(idParameter);
                request.setAttribute("reservation", reservationService.findById(reservationId));
            } else {
                System.out.println("Id absent des paramètres");
                response.sendRedirect(request.getContextPath() + "/rents"); //TODO
            }
        }catch (ServiceException e) {
            System.out.println("Servlet doGet Reservation detail : "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
