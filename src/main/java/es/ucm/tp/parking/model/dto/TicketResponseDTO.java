package es.ucm.tp.parking.model.dto;

import es.ucm.tp.parking.model.Ticket;

public class TicketResponseDTO {
  public String id;
  public String plateNumber;
  public String startTime;
  public String endTime;
  public boolean active;

  // Mapper from domain Ticket to DTO response ticket
  public static TicketResponseDTO fromDomain(Ticket ticket) {
    TicketResponseDTO dto = new TicketResponseDTO();
    dto.id = ticket.getId().toString();
    dto.plateNumber = ticket.getCarPlate();
    dto.startTime = ticket.getParkingStart().toString(); // ISO8601 Zulu
    dto.endTime = ticket.getParkingEnd().toString();
    dto.active = ticket.isActive();
    return dto;
  }
}