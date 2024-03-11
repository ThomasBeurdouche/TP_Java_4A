package com.epf.rentmanager.ui.cli;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

public class Main {

    private static ClientService clientService = ClientService.getInstance();
    private static VehicleService vehicleService = VehicleService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        boolean client = false;
        boolean vehicle = false;
        boolean reservation = false;

        while (continuer) {
            System.out.println("Choisissez l'objet que vous voulez utilisé :");
            System.out.println("1. Client");
            System.out.println("2. Vehicule");
            System.out.println("3. Réservation");
            System.out.println("4. Quitter");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    client=true;
                    while (client) {
                        System.out.println("Choisissez une fonction à exécuter :");
                        System.out.println("1. Pour créer un client");
                        //System.out.println("2. Pour supprimer un client");
                        System.out.println("3. Pour afficher tous les clients");
                        System.out.println("4. Retour choix objet");
            
                        int choixClient = scanner.nextInt();
            
                        switch (choixClient) {
                            case 1:
                                ClientCreate();
                                break;
                            //case 2:
                                //long clientId = IOUtils.readLong("Id du Client : ");
                                //ClientDelete(clientService.findById(clientId));
                                //break;
                            case 3:
                                ClientFindAll();
                                break;
                            case 4:
                                vehicle = false;
                                break;
                            default:
                                System.out.println("Choix invalide");
                        }
                    }
                    break;
                case 2:
                    vehicle=true;
                    while (vehicle) {
                        System.out.println("Choisissez une fonction à exécuter :");
                        System.out.println("1. Pour créer un véhicule");
                        //System.out.println("2. Pour supprimer un véhicule");
                        System.out.println("3. Pour afficher tous les véhicules");
                        System.out.println("4. Retour choix objet");
            
                        int choixVehicule = scanner.nextInt();
            
                        switch (choixVehicule) {
                            case 1:
                                VehicleCreate();
                                break;
                            //case 2:
                                //long vehicleId = IOUtils.readLong("Id du Vehicle : ");
                                //VehicleDelete(vehicleService.findById(vehicleId));
                                //break;
                            case 3:
                                VehicleFindAll();
                                break;
                            case 4:
                                vehicle = false;
                                break;
                            default:
                                System.out.println("Choix invalide");
                        }
                    }
                    break;
                case 3:
                reservation=true;
                while (reservation) {
                    System.out.println("Choisissez une fonction à exécuter :");
                    System.out.println("1. Pour créer une réservation");
                    //System.out.println("2. Pour supprimer une réservation");
                    System.out.println("3. Pour afficher toutes les réservations");
                    System.out.println("4. Retour choix objet");
        
                    int choixReservation = scanner.nextInt();
        
                    switch (choixReservation) {
                        case 1:
                            ReservationCreate();
                            break;
                        //case 2:
                            //long reservationId = IOUtils.readLong("Id du client : ");
                            //ReservationDelete(reservationService.findById(reservationId));
                            //break;
                        case 3:
                            ReservationFindAll();
                            break;
                        case 4:
                            reservation = false;
                            break;
                        default:
                            System.out.println("Choix invalide");
                    }
                }
                    break;
                case 4:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Client
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void ClientCreate(){
        
        String nom = IOUtils.readString("Entrez votre nom : ",true);
        String prenom = IOUtils.readString("Entrez votre prenom : ",true);
        String mail = IOUtils.readEmail("Entrez votre mail : ");
        LocalDate dateNaissance = IOUtils.readDate("Entrez votre date de naissance au format dd/MM/yyyy : ", true);
        try {
            System.out.println("ID = "+clientService.create(new Client(nom,prenom,mail,dateNaissance)));
        }catch (ServiceException e) {
            System.out.println("Create Client : "+e.getMessage());    
        }
    }

    public static void ClientDelete(Client client){
        try {
            System.out.println(client);
            System.out.println(clientService.delete(client));
        }catch (ServiceException e) {
            System.out.println("Delete Client : "+e.getMessage());    
        }
    }

    public static void ClientFindById(long Id){
        try {
            System.out.println(clientService.findById(Id));
        }catch (ServiceException e) {
            System.out.println("FindById Client : "+e.getMessage());    
        }
    }

    public static void ClientFindAll(){
        try {
            System.out.println(clientService.findAll());
        }catch (ServiceException e) {
            System.out.println("FindAll Client : "+e.getMessage());    
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Vehicle
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void VehicleCreate(){

        String constructeur = IOUtils.readString("Entrez le constructeur : ", false);
        String modele = IOUtils.readString("Entrez le modele : ", false);
        int nbrPlace = IOUtils.readInt("Entrez le nombre de place: ");
        try {
            System.out.println("ID = "+vehicleService.create(new Vehicle(constructeur,modele,nbrPlace)));
        }catch (ServiceException e) {
            System.out.println("Create Vehicle"+e.getMessage());    
        }
    }

    public static void VehicleDelete(Vehicle vehicle){
        try {
            System.out.println(vehicleService.delete(vehicle));
        }catch (ServiceException e) {
            System.out.println("Delete Vehicle : "+e.getMessage());    
        }
    }

    public static void VehicleFindById(long Id){
        try {
            System.out.println(Id);
            System.out.println(vehicleService.findById(Id));
        }catch (ServiceException e) {
            System.out.println("FindById Vehicle : "+e.getMessage());    
        }
    }

    public static void VehicleFindAll(){
        try {
            System.out.println(vehicleService.findAll());
        }catch (ServiceException e) {
            System.out.println("FindAll Vehicle : "+e.getMessage());    
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Reservation
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    public static void ReservationCreate(){
        long idClient = IOUtils.readLong("Entrez l'id du client : ");
        long idVehicle = IOUtils.readLong("Entrez l'id du vehicle : ");
        LocalDate debut = IOUtils.readDate("Entrez la date de début de réservation au format dd/MM/yyyy : ", false);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin de réservation au format dd/MM/yyyy : ", false);
        try {
            System.out.println("ID = "+reservationService.create(new Reservation(idClient, idVehicle, debut, fin)));
        }catch (ServiceException e) {
            System.out.println("Create Reservation : "+e.getMessage());    
        }
    }

    public static void ReservationDelete(Reservation reservation){
        try {
            System.out.println(reservationService.delete(reservation));
        }catch (ServiceException e) {
            System.out.println("Delete Reservation : "+e.getMessage());    
        }
    }

    public static void ReservationFindByIdClient(long IdClient){
        try {
            System.out.println(IdClient);
            System.out.println(reservationService.findByClientId(IdClient));
        }catch (ServiceException e) {
            System.out.println("Test FindById Vehicle : "+e.getMessage());    
        }
    }

    public static void ReservationFindByIdVehicle(long IdVehicle){
        try {
            System.out.println(IdVehicle);
            System.out.println(reservationService.findByClientId(IdVehicle));
        }catch (ServiceException e) {
            System.out.println("Test FindById Vehicle : "+e.getMessage());    
        }
    }

    public static void ReservationFindAll(){
        try {
            System.out.println(reservationService.findAll());
        }catch (ServiceException e) {
            System.out.println("Test FindAll Vehicle : "+e.getMessage());    
        }
    }

}
