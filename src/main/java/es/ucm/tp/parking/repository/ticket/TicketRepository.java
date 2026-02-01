package es.ucm.tp.parking.repository.ticket;

import es.ucm.tp.parking.model.Ticket;

import java.util.*;

public interface TicketRepository {
  List<Ticket> getAllTickets();
  void save(Ticket ticket);
  Ticket retrieveById(UUID id);
  List<Ticket> retrieveByPlate(String plate);
  Ticket retrieveActiveTicketByPlate(String plate);
  void delete(UUID id);
}
