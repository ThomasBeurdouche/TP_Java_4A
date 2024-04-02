package com.epf.rentmanager.service;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public long create(Client client) throws ServiceException {
		if(client.getNom().isEmpty()||client.getPrenom().isEmpty())throw new ServiceException("Le client n'as pas de nom ou de prénom");
		try{
			return clientDao.create(new Client(client.getId(),client.getNom().toUpperCase(),client.getPrenom(),client.getEmail(),client.getNaissance()));
		} catch (DaoException e){
			throw new ServiceException("Client create Service: "+e.getMessage());
		}
	}

	public boolean modify(Client client) throws ServiceException {
		try{
			return clientDao.modify(client);
		} catch (DaoException e){
			throw new ServiceException("Client modify Service: "+e.getMessage());
		}
	}

	public long delete(Client client) throws ServiceException {
		try{
			return clientDao.delete(client);
		} catch (DaoException e){
			throw new ServiceException("Client delete Service: "+e.getMessage());
		}	
	}

	public Client findById(long id) throws ServiceException {
		try{
			System.out.println(id);
			return clientDao.findById(id);
		} catch (DaoException e){
			throw new ServiceException("Client findById Service: "+e.getMessage());
		}	
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Client finAll Service: "+e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException e){
			throw new ServiceException("Client count Service: "+e.getMessage());
		}
	}
	
}
