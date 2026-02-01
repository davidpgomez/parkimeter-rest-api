package es.ucm.tp.parking.route;


import es.ucm.tp.parking.model.Ticket;
import es.ucm.tp.parking.model.dto.TicketRequestDTO;
import es.ucm.tp.parking.model.dto.TicketResponseDTO;
import es.ucm.tp.parking.service.TicketService;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import java.util.List;
import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;


public class TicketRoutes implements EndpointGroup {
  private final TicketService service;

  public TicketRoutes(TicketService service) {
    this.service = service;
  }

  private void addTicket(Context ctx) {
    // Parse ticket information provided in body
    TicketRequestDTO dto = ctx.bodyAsClass(TicketRequestDTO.class);
    Ticket issued = service.create(dto.getPlate(), dto.getMinutes());
    // Send new issued ticket with status code 201
    ctx.json(TicketResponseDTO.fromDomain(issued)).status(201);
  }

  private void getSingleTicket(Context ctx) {
    Ticket ticket = service.getTicketById(ctx.pathParam("id"));
    if (ticket != null) ctx.json(TicketResponseDTO.fromDomain(ticket));
    else ctx.status(404).result("No ticket found with ID: " + ctx.pathParam("id"));
  }

  private void getTicketsByPlate(Context ctx) {
    // if we have a query param to filter, get it and apply filter
    String plate = ctx.queryParam("plate");
    if (plate != null) {
      List<Ticket> t = service.retrieveTicketByPlate(plate);
      if (t != null) ctx.json(t);
      else ctx.status(404).result("No ticket found for plate " + plate);
    } else {
      // otherwise return all tickets
      ctx.json(service.getAllTicket().stream().map(TicketResponseDTO::fromDomain).toList());
    }
  }

  private void deleteTicket(Context ctx) {
    if (service.cancel(UUID.fromString(ctx.pathParam("id")))) ctx.status(204);
    else ctx.status(404);
  }

  private void extendTicket(Context ctx) {
    int min = Integer.parseInt(ctx.queryParam("minutes"));
    if (service.extend(UUID.fromString(ctx.pathParam("id")), min)) ctx.status(200);
    else ctx.status(404);
  }

  @Override
  public void addEndpoints() {
    // Serve here 'tickets' resource
    path("tickets", () -> {
      // GET /tickets returns all parking tickets, /tickets?plate=1111HHH returns tickets for that plate
      get(this::getTicketsByPlate);
      // POST /tickets creates a new ticket
      post(this::addTicket);

      // Operations on a given tickets by its ID
      path("{id}", () -> {
        // GET /tickets/6ba7b812-9dad-11d1-80b4-00c04fd430c8 returns info about that ticket
        get(this::getSingleTicket);
        // DELETE /tickets/6ba7b812-9dad-11d1-80b4-00c04fd430c8 deletes that ticket
        delete(this::deleteTicket);
        // PATCH /ticket/6ba7b812-9dad-11d1-80b4-00c04fd430c8/extend?minutes=10 renews duration for a ticket
        patch("extend", this::extendTicket);
      });
    });
  }
}

