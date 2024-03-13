package com.epf.rentmanager.dao;

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
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {


	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENT_QUERY = "SELECT COUNT(*) FROM Client;";

	public long create(Client client) throws DaoException {
		
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);){
			preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				client.setId(resultSet.getLong(1));
			}
			return client.getId();

		}catch(SQLException e){
			throw new DaoException("Client Create : "+e.getMessage());
		}
		
	}
	
	public long delete(Client client) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY);){
			
			preparedStatement.setLong(1, client.getId());
			preparedStatement.executeUpdate();
			return client.getId();
		}catch(SQLException e){
			throw new DaoException("Client Delete : "+e.getMessage());
		}

	}

	public Client findById(long id) throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY);){
			preparedStatement.setLong(1, id);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return 	new Client(id,resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate());
		}catch(SQLException e){
			throw new DaoException("Client FindId : "+e.getMessage());
		}

	}

	public List<Client> findAll() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENTS_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Client> newClients = new ArrayList<>();
			while (resultSet.next()) {
				Client newClient = new Client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5).toLocalDate());
				newClients.add(newClient);
			}
			return newClients;
		}catch(SQLException e){
			throw new DaoException("Client FindAll : "+e.getMessage());
		}
	}

	public int count() throws DaoException {
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(COUNT_CLIENT_QUERY);){
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();
			resultSet.next();
			return resultSet.getInt(1);
		}catch(SQLException e){
			throw new DaoException("Client count : "+e.getMessage());
		}
	}

}
