package com.epf.rentmanager.service;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;

public class ReservationService {

	private ReservationDao reservationDao;
	public static ReservationService instance;
	
	private ReservationService() {
		this.reservationDao = ReservationDao.getInstance();
	}
	
	public static ReservationService getInstance() {
		if (instance == null) {
			instance = new ReservationService();
		}
		
		return instance;
	}
	
	
	public long create(Reservation reservation) throws ServiceException {
		try{
			return reservationDao.create(new Reservation(reservation.getClient_id(),reservation.getVehicle_id(),reservation.getDebut(),reservation.getFin()));
		} catch (DaoException e){
			throw new ServiceException("Reservation Erreur Dao create : "+e.getMessage());
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try{
			return reservationDao.delete(reservation);
		} catch (DaoException e){
			throw new ServiceException("Reservation Erreur Dao delete : "+e.getMessage());
		}	
	}

	public List<Reservation> findByClientId(long id) throws ServiceException {
		try{
			return reservationDao.findResaByClientId(id);
		} catch (DaoException e){
			throw new ServiceException("Reservation Erreur Dao find by id client : "+e.getMessage());
		}	
	}

	public List<Reservation> findByVehicleId(long id) throws ServiceException {
		try{
			return reservationDao.findResaByVehicleId(id);
		} catch (DaoException e){
			throw new ServiceException("Reservation Erreur Dao find by id vehicle : "+e.getMessage());
		}
	}

    public List<Reservation> findAll() throws ServiceException {
		try{
			return reservationDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Reservation Erreur Dao find all : "+e.getMessage());
		}
	}
	
}
