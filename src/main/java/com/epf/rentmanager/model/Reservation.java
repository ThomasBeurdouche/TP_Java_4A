package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private Client client;
    private Vehicle vehicle;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(){}

    public Reservation(Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = 0;
        this.client = client;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(long id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient( Client client ) { this.client = client;}
    public Vehicle getVehicle() {return vehicle;}
    public void setVehicle(Vehicle vehicle) {this.vehicle = vehicle;}
    public LocalDate getDebut() {
        return debut;
    }
    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }
    public LocalDate getFin() {
        return fin;
    }
    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", client_id=" + client.getId() + ", vehicle_id=" + vehicle.getId() + ", debut=" + debut
                + ", fin=" + fin + "]";
    }



}
