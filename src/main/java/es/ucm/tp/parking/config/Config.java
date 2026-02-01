package es.ucm.tp.parking.config;

/**
 * This class maps to the application config
 */
public class Config {
  private Application application;

  public static class RepositoryConfig {
    private String type;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
  }

  public static class Application {
    private int port;
    private RepositoryConfig repository;

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public RepositoryConfig getRepository() { return repository; }
    public void setRepository(RepositoryConfig repository) { this.repository = repository; }
  }

  public Application getApplication() { return application; }
  public void setApplication(Application application) { this.application = application; }

  public RepositoryConfig getRepositoryConfig() { return application.getRepository();}

}