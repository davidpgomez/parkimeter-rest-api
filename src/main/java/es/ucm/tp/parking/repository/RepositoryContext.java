package es.ucm.tp.parking.repository;

import es.ucm.tp.parking.repository.ticket.TicketRepository;

public class RepositoryContext {
  private final TicketRepository ticketRepository;

  public RepositoryContext(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public TicketRepository getTicketRepository() {
    return ticketRepository;
  }
}
