package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.graalvm.compiler.asm.sparc.SPARCAssembler;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<String> bddEmail = new ArrayList<>();
            for (Client client : clientService.findAll()){
                bddEmail.add(client.getEmail());
            }
            request.setAttribute("bddEmail",bddEmail);
        }catch (ServiceException e) {
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la création du client : " + e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String birthdayString = request.getParameter("birthday");
        LocalDate birthday = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Client newClient = new Client(lastName,firstName,email,birthday);

        try {
            clientService.create(newClient);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Servlet Client DoPost : " + e.getMessage());
        }
    }
}
