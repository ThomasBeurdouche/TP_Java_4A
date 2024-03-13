package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setAttribute("nb_vehicle", AppConfiguration.context.getBean(VehicleService.class).count());
			request.setAttribute("nb_client", AppConfiguration.context.getBean(ClientService.class).count());
			request.setAttribute("nb_reservation", AppConfiguration.context.getBean(ReservationService.class).count());
		}catch (ServiceException e) {
			System.out.println("Servlet doGet Home : "+e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
