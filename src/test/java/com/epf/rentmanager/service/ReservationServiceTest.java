package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    public void initialisation() {
        reservationDao = mock(ReservationDao.class);
        reservationService = new ReservationService(reservationDao);
    }

    @Test
    public void Create_should_return_long_when_reservation_is_valide() throws ServiceException, DaoException {
        Reservation reservation = new Reservation(1, new Client(), new Vehicle(), LocalDate.now(), LocalDate.now());
        when(reservationDao.create(any(Reservation.class))).thenReturn(1L);

        long reservationId = reservationService.create(reservation);

        assertEquals(1L, reservationId);
    }

    @Test
    public void FindAll_should_return_List_of_Reservation() throws ServiceException, DaoException {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, new Client(), new Vehicle(),LocalDate.now(),LocalDate.now()));
        reservations.add(new Reservation(2, new Client(), new Vehicle(),LocalDate.now(),LocalDate.now()));

        when(reservationDao.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.findAll();

        assertEquals(reservations.size(), result.size());
        assertTrue(result.containsAll(reservations));
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.reservationDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }
}
