package es.ucm.tp.parking;

import es.ucm.tp.parking.config.Config;
import es.ucm.tp.parking.config.ConfigLoader;
import es.ucm.tp.parking.model.Ticket;
import es.ucm.tp.parking.repository.RepositoryContext;
import es.ucm.tp.parking.repository.ticket.MemoryTicketRepository;
import es.ucm.tp.parking.repository.ticket.TicketRepository;
import es.ucm.tp.parking.route.TicketRoutes;
import es.ucm.tp.parking.service.TicketService;
import io.javalin.Javalin;

public class Main {

  /**
   * Instantiate TicketService from Config objects
   * @param appConfig Application configuration
   * @return An initialized TicketService
   */
  private static TicketService serviceFromConfig(Config appConfig) {
    String repoType = appConfig.getRepositoryConfig().getType();
    TicketRepository ticketRepository = switch (repoType) {
      case "memory" -> new MemoryTicketRepository();
      default -> throw new IllegalArgumentException("Invalid repository type '"+repoType+"'");
    };
    RepositoryContext repositories = new RepositoryContext(ticketRepository);
    return new TicketService(repositories.getTicketRepository());
  }

  public static void main(String[] args) {
    Config applicationConfig = ConfigLoader.fromFile("application.conf");
    TicketService service = serviceFromConfig(applicationConfig);

    // Create Javalin APP registering 'tickets' resource
    Javalin.create( javalinConfig -> {
        javalinConfig.router.apiBuilder(() -> new TicketRoutes(service).addEndpoints());
      }
    ).start(applicationConfig.getApplication().getPort());
  }
}
