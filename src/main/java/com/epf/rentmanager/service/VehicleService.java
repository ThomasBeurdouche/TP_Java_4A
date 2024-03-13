package com.epf.rentmanager.service;

import java.util.List;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	public long create(Vehicle vehicle) throws ServiceException {
		if(vehicle.getConstructeur().isEmpty()||(vehicle.getNb_places()<=1))throw new ServiceException("Le vehicle n'as pas de constructeur ou de place");
		try{
			return vehicleDao.create(vehicle);
		} catch (DaoException e){
			throw new ServiceException("Vehicle Service create : "+e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try{
			return vehicleDao.delete(vehicle);
		} catch (DaoException e){
			throw new ServiceException("Vehicle Service delete : "+e.getMessage());
		}	
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			return vehicleDao.findById(id);
		} catch (DaoException e){
			throw new ServiceException("Vehicle Service findById : "+e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Vehicle Service findAll : "+e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try{
			return vehicleDao.count();
		} catch (DaoException e){
			throw new ServiceException("Vehicle Service count : "+e.getMessage());
		}
	}
	
}
