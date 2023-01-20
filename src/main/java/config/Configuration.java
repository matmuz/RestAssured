package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:admin.properties")
public interface Configuration extends Config {

    String username();

    String password();
}