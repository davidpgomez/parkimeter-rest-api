package es.ucm.tp.parking.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
  private final UUID id;
  private final String carPlate;
  private LocalDateTime parkingStart;
  private LocalDateTime parkingEnd; // Timestamp en milisegundos

  public Ticket(String plate, LocalDateTime start, LocalDateTime end) {
    this.id = UUID.randomUUID();
    this.carPlate = plate;
    this.parkingStart = start;
    this.parkingEnd = end;
  }

  // Getters y Setters
  public UUID getId() { return id; }
  public String getCarPlate() { return carPlate; }
  public LocalDateTime getParkingEnd() { return parkingEnd; }
  public LocalDateTime getParkingStart() { return parkingStart; }
  public boolean isActive() { return LocalDateTime.now().isBefore(parkingEnd) &&  LocalDateTime.now().isAfter(parkingStart); }
  public void extend(int minutes) { this.parkingEnd = LocalDateTime.now().plusMinutes(minutes); }
}