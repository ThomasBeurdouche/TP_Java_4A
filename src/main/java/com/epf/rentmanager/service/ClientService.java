package com.epf.rentmanager.service;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		if(client.getNom().isEmpty()||client.getPrenom().isEmpty())throw new ServiceException("Le client n'as pas de nom ou de prénom");
		try{
			return clientDao.create(new Client(client.getId(),client.getNom().toUpperCase(),client.getPrenom(),client.getEmail(),client.getNaissance()));
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}
	}

	public long delete(Client client) throws ServiceException {
		try{
			return clientDao.delete(client);
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}	
	}

	public Client findById(long id) throws ServiceException {
		try{
			System.out.println(id);
			return clientDao.findById(id);
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}	
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException e){
			throw new ServiceException("Erreur Client Service count : "+e.getMessage());
		}
	}
	
}
