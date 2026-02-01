package es.ucm.tp.parking.repository.ticket;

import es.ucm.tp.parking.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemoryTicketRepository implements TicketRepository {
  private Map<String, Ticket> db = new HashMap<>();

  @Override
  public List<Ticket> getAllTickets() {
    return new ArrayList<>(db.values());
  }

  @Override
  public void save(Ticket ticket) {
    db.put(ticket.getCarPlate(), ticket);
  }

  @Override
  public Ticket retrieveById(UUID id) {
    return db.get(id.toString());
  }

  public List<Ticket> retrieveByPlate(String plate) {
    List<Ticket> ticketForPlate = new ArrayList<>();
    for (Ticket ticket : db.values()) {
      if (ticket.getCarPlate().equals(plate)) {
        ticketForPlate.add(ticket);
      }
    }
    return ticketForPlate;
  }

  @Override
  public Ticket retrieveActiveTicketByPlate(String plate) {
    List<Ticket> ticketForPlate = retrieveByPlate(plate);
    if (!ticketForPlate.isEmpty()) {
      for (Ticket ticket : ticketForPlate) {
        if (ticket.isActive()) {
          return ticket;
        }
      }
    } else {
      return null;
    }
    return null;
  }

  @Override
  public void delete(UUID id) {
    db.remove(id.toString());
  }
}
