package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/modify")
public class ClientModifyServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

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
                request.setAttribute("client", clientService.findById(userId));
                request.setAttribute("naissance", clientService.findById(userId).getNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }else {
                System.out.println("Id absent des paramètres");
                response.sendRedirect(request.getContextPath() + "/users"); //TODO
            }
        }catch (ServiceException e) {
            System.out.println("Servlet doGet Client Modify: "+e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/modify.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String birthdayString = request.getParameter("birthday");
        LocalDate birthday = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String idParameter = request.getParameter("ID");
        long userId = Long.parseLong(idParameter);

        Client newClient = new Client(userId,lastName,firstName,email,birthday);
        try {
            clientService.modify(newClient);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Client Modify Servlet DoPost : " + e.getMessage());
        }
    }
}