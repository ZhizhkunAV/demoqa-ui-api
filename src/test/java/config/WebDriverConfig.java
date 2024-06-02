package config;

import org.aeonbits.owner.Config;

import java.net.URL;

public interface WebDriverConfig extends Config {

    @Key("baseUrl")
    @DefaultValue("https://demoqa.com")
    String getBaseUrl();

    @Key("remoteUrl")
    @DefaultValue("http://localhost:4444")
    URL getRemoteURL();

    @Key("browser")
    @DefaultValue("CHROME")
    Browser getBrowser();

    @Key("browser_version")
    @DefaultValue("122.0")
    String getBrowserVersion();

    @Key("browser_size")
    @DefaultValue("1920x1080")
    String getBrowserSize();

}