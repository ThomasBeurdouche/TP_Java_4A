package com.epf.rentmanager.dao;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private VehicleDao vehicleDao;
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATION_QUERY = "SELECT COUNT(*) FROM Reservation;";


	public long create(Reservation reservation) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);){
			preparedStatement.setLong(1, reservation.getClient().getId());
            preparedStatement.setLong(2, reservation.getVehicle().getId());
            preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				reservation.setId(resultSet.getLong(1));
			}
			return reservation.getId();

		}catch(SQLException e){
			throw new DaoException("Reservation create : "+e.getMessage());
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY);){
			preparedStatement.setLong(1, reservation.getId());
			preparedStatement.executeUpdate();
			return reservation.getId();
		}catch(SQLException e){
			throw new DaoException("Reservation delete : "+e.getMessage());
		}
	}

	public Reservation findResaId(long resaId) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY);){
			preparedStatement.setLong(1, resaId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return new Reservation(resaId,clientDao.findById(resultSet.getInt(1)), vehicleDao.findById(resultSet.getInt(2)),resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate());
		}catch(SQLException e){
			throw new DaoException("Reservation FindByClientID : "+e.getMessage());
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);){
			preparedStatement.setLong(1, clientId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Reservation> newReservations = new ArrayList<>();
			while (resultSet.next()) {
				Reservation newReservation = new Reservation(resultSet.getInt(1),clientDao.findById(clientId), vehicleDao.findById(resultSet.getInt(2)),resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate());
				newReservations.add(newReservation);
			}
			return newReservations;
		}catch(SQLException e){
			throw new DaoException("Reservation FindByClientID : "+e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);){	
			preparedStatement.setLong(1, vehicleId);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Reservation> newReservations = new ArrayList<>();
			while (resultSet.next()) {
				Reservation newReservation = new Reservation(resultSet.getInt(1),clientDao.findById(resultSet.getInt(2)),vehicleDao.findById(vehicleId),resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate());
				newReservations.add(newReservation);
			}
			return newReservations;
		}catch(SQLException e){
			throw new DaoException("Reservation FindByVehicleID : "+e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Reservation> newReservations = new ArrayList<>();
			while (resultSet.next()) {
				Reservation newReservation = new Reservation(resultSet.getInt(1),clientDao.findById(resultSet.getInt(2)),vehicleDao.findById(resultSet.getInt(3)),resultSet.getDate(4).toLocalDate(),resultSet.getDate(5).toLocalDate());
				newReservations.add(newReservation);
			}
			return newReservations;
		}catch(SQLException e){
			throw new DaoException("Reservation FindAll : "+e.getMessage());
		}
	}

	public int count() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(COUNT_RESERVATION_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return resultSet.getInt(1);
		}catch(SQLException e){
			throw new DaoException("Reservation count : "+e.getMessage());
		}
	}
}
