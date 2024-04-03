package com.epf.rentmanager.service;

import com.epf.rentmanager.model.*;
import com.epf.rentmanager.exception.*;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	@Autowired
	private ReservationDao reservationDao;

	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	public long create(Reservation reservation) throws ServiceException {
		try{
			return reservationDao.create(new Reservation(reservation.getClient(),reservation.getVehicle(),reservation.getDebut(),reservation.getFin()));
		} catch (DaoException e){
			throw new ServiceException("Reservation Service create : "+e.getMessage());
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try{
			return reservationDao.delete(reservation);
		} catch (DaoException e){
			throw new ServiceException("Reservation Service delete : "+e.getMessage());
		}	
	}

	public boolean modify(Reservation reservation) throws ServiceException {
		try{
			return reservationDao.modify(reservation);
		} catch (DaoException e){
			throw new ServiceException("Reservation Service modify : "+e.getMessage());
		}
	}

	public Reservation findById(long id) throws ServiceException {
		try{
			return reservationDao.findResaId(id);
		} catch (DaoException e){
			throw new ServiceException("Reservation Service find by id : "+e.getMessage());
		}
	}

	public List<Reservation> findByClientId(long id) throws ServiceException {
		try{
			return reservationDao.findResaByClientId(id);
		} catch (DaoException e){
			throw new ServiceException("Reservation Service find by id client : "+e.getMessage());
		}	
	}

	public List<Reservation> findByVehicleId(long id) throws ServiceException {
		try{
			return reservationDao.findResaByVehicleId(id);
		} catch (DaoException e){
			throw new ServiceException("Reservation Service find by id vehicle : "+e.getMessage());
		}
	}

    public List<Reservation> findAll() throws ServiceException {
		try{
			return reservationDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Reservation Service find all : "+e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try{
			return reservationDao.count();
		} catch (DaoException e){
			throw new ServiceException("Reservation Service count : "+e.getMessage());
		}
	}
	
}
