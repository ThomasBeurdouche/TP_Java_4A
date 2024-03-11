package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		if(vehicle.getConstructeur().isEmpty()||(vehicle.getNb_places()<=1))throw new ServiceException("Le vehicle n'as pas de constructeur ou de place");
		try{
			return vehicleDao.create(vehicle);
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try{
			return vehicleDao.delete(vehicle);
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}	
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			return vehicleDao.findById(id);
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Erreur Dao : "+e.getMessage());
		}
	}
	
}
