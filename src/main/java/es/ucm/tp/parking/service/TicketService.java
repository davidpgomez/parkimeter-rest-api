package es.ucm.tp.parking.service;

import es.ucm.tp.parking.model.Ticket;
import es.ucm.tp.parking.repository.ticket.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TicketService {
  private TicketRepository repo;

  public TicketService(TicketRepository repository) {
    this.repo = repository;
  }

  public List<Ticket> getAllTicket() {
    return repo.getAllTickets();
  }

  public Ticket create(String plate, int minutes) {
    Ticket nuevo = new Ticket(plate, LocalDateTime.now(), LocalDateTime.now().plusMinutes(minutes));
    repo.save(nuevo);
    return nuevo;
  }

  public boolean hasTicketActiveByPlate(String matricula) {
    List<Ticket> t = repo.retrieveByPlate(matricula);
    return !t.isEmpty();
  }

  public Ticket getTicketById(String id) {
    return repo.retrieveById(UUID.fromString(id));
  }

  public List<Ticket> retrieveTicketByPlate(String plate) {
    return repo.retrieveByPlate(plate);
  }

  public boolean extend(UUID ticketId, int minutes) {
    Ticket t = repo.retrieveById(ticketId);
    if (t != null) {
      // extend duration for active ticket the provided minutes
      t.extend(minutes);
      repo.save(t);
      return true;
    }
    return false;
  }

  public boolean cancel(UUID ticketId) {
    if (repo.retrieveById(ticketId) != null) {
      repo.delete(ticketId);
      return true;
    }
    return false;
  }
}