package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
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

@WebServlet("/rents/modify")
public class ReservationModifyServlet extends HttpServlet {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
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
                request.setAttribute("reservation", reservationService.findById(userId));
                request.setAttribute("debut", reservationService.findById(userId).getDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                request.setAttribute("fin", reservationService.findById(userId).getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }else {
                System.out.println("Id absent des paramètres");
            }
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());

        }catch (ServiceException e) {
            System.out.println("Servlet doGet Reservation Modify: "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/modify.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idClientString = request.getParameter("client");
        long idClient = Long.parseLong(idClientString);
        String idVehicleString = request.getParameter("car");
        long idVehicle = Long.parseLong(idVehicleString);
        String debutString  = request.getParameter("debut");
        LocalDate debut = LocalDate.parse(debutString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String finString  = request.getParameter("debut");
        LocalDate fin = LocalDate.parse(finString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String idParameter = request.getParameter("ID");
        long reservationId = Long.parseLong(idParameter);

        try {
            Reservation newReservation = new Reservation(reservationId,clientService.findById(idClient),vehicleService.findById(idVehicle),debut,fin);
            reservationService.modify(newReservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Reservation modify dopost servlet : " + e.getMessage());
        }
    }
}