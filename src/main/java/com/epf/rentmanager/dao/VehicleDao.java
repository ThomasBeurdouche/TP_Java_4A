package com.epf.rentmanager.dao;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	private static final String MODIFY_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";

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
			return vehicle.getId();
		}catch(SQLException e){
			throw new DaoException("Vehicle Dao delete : "+e.getMessage());
		}
	}

	public boolean modify(Vehicle vehicle) throws DaoException {

		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(MODIFY_VEHICLE_QUERY);){
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.setLong(4, vehicle.getId());
			preparedStatement.executeUpdate();
			return true;

		}catch(SQLException e){
			throw new DaoException("Vehicle Modify DAO: "+e.getMessage());
		}

	}

	public Vehicle findById(long id) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);){
			preparedStatement.setLong(1, id);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return new Vehicle(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
		}catch(SQLException e){
			throw new DaoException("Vehicle Dao FindId : "+e.getMessage());
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
			throw new DaoException("Vehicle Dao FindAll : "+e.getMessage());
		}
		
	}

	public int count() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(COUNT_VEHICLES_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return resultSet.getInt(1);
		}catch(SQLException e){
			throw new DaoException("Vehicle Dao count : "+e.getMessage());
		}
	}

}
