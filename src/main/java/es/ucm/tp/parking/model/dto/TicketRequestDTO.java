package es.ucm.tp.parking.model.dto;

/**
 * POJO representing the body when a new ticket is required
 */
public class TicketRequestDTO {
  private String plate;
  private int minutes;

  // Getters y Setters
  public String getPlate() { return plate; }
  public int getMinutes() { return minutes; }
  public void setPlate(String plate) { this.plate = plate; }
  public void setMinutes(int minutes) { this.minutes = minutes; }
}