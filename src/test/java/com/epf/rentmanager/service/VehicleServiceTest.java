package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
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
public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @BeforeEach
    public void initialisation() {
        vehicleDao = mock(VehicleDao.class);
        vehicleService = new VehicleService(vehicleDao);
    }

    @Test
    public void Create_should_return_long_when_vehicle_is_valide() throws ServiceException, DaoException {
        Vehicle vehicle = new Vehicle(1, "constructeurTest", "modeleTest", 3);
        when(vehicleDao.create(any(Vehicle.class))).thenReturn(1L);

        long vehicleId = vehicleService.create(vehicle);

        assertEquals(1L, vehicleId);
    }

    @Test
    public void FindAll_should_return_List_of_Vehicle() throws ServiceException, DaoException {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(1, "constructeurTest1", "modeleTest1",3));
        vehicles.add(new Vehicle(2, "constructeurTest2", "modeleTest2",3));

        when(vehicleDao.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.findAll();

        assertEquals(vehicles.size(), result.size());
        assertTrue(result.containsAll(vehicles));
    }


    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findAll());
    }
}
