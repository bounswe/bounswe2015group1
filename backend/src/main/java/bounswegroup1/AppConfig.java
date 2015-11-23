package bounswegroup1;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("httpClient")
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @NotEmpty
    private String bearerRealm;

    private String nutritionixAppId;
    private String nutritionixAppKey;

    public String getNutritionixAppKey() {
        return nutritionixAppKey;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public JerseyClientConfiguration getHttpClient() {
        return httpClient;
    }

    public String getBearerRealm() {
        return bearerRealm;
    }

    public String getNutritionixAppId() {
        return nutritionixAppId;
    }
}
