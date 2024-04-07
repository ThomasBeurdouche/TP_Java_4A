package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.model.Client;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @BeforeEach
    public void initialisation() {
        clientDao = mock(ClientDao.class);
        clientService = new ClientService(clientDao);
    }

    @Test
    public void Create_should_return_long_when_client_is_valide() throws ServiceException, DaoException {
        Client client = new Client(1, "nomTest", "prenomTest", "email@test", LocalDate.now());
        when(clientDao.create(any(Client.class))).thenReturn(1L);

        long clientId = clientService.create(client);

        assertEquals(1L, clientId);
    }

    @Test
    public void FindAll_should_return_List_of_Client() throws ServiceException, DaoException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "nomTest1", "prenomTest1", "email@test1", LocalDate.now()));
        clients.add(new Client(2, "nomTest2", "prenomTest2", "email@test2", LocalDate.now()));

        when(clientDao.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(clients.size(), result.size());
        assertTrue(result.containsAll(clients));
    }

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

}
