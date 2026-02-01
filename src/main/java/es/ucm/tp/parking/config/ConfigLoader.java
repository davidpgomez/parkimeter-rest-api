package es.ucm.tp.parking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public class ConfigLoader {
  private Config cfg = new Config();

  private ConfigLoader(String configFile) {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      this.cfg = mapper.readValue(input, Config.class);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Config fromFile(String configFile) {
    ConfigLoader loader = new ConfigLoader(configFile);
    return loader.cfg;
  }
}
