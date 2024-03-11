package com.epf.rentmanager.dao;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);){
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
            preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				vehicle.setId(resultSet.getLong(1));
			}
			return vehicle.getId();

		}catch(SQLException e){
			throw new DaoException("Vehicle create : "+e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_VEHICLE_QUERY);){
			preparedStatement.setLong(1, vehicle.getId());
			preparedStatement.executeUpdate();
			return 1;
		}catch(SQLException e){
			throw new DaoException("Vehicle delete : "+e.getMessage());
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);){
			preparedStatement.setLong(1, id);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			Vehicle newVehicle = new Vehicle(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
			return newVehicle;
		}catch(SQLException e){
			throw new DaoException("Vehicle FindId : "+e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Vehicle> newVehicles = new ArrayList<>();
			while (resultSet.next()) {
				Vehicle newVehicle = new Vehicle(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
				newVehicles.add(newVehicle);
			}
			return newVehicles;
		}catch(SQLException e){
			throw new DaoException("Vehicle FindAll : "+e.getMessage());
		}
		
	}
	

}
