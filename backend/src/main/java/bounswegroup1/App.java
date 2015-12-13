package bounswegroup1;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.OptionalContainerFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.client.JerseyClientBuilder;

import org.skife.jdbi.v2.DBI;

import bounswegroup1.resource.IngredientResource;
import bounswegroup1.resource.RecipeResource;
import bounswegroup1.resource.SessionResource;
import bounswegroup1.resource.UserResource;
import bounswegroup1.resource.MenuResource;
import bounswegroup1.resource.CommentResource;
import bounswegroup1.auth.OAuthAuthenticator;
import bounswegroup1.db.AccessTokenDAO;
import bounswegroup1.db.RecipeDAO;
import bounswegroup1.db.UserDAO;
import bounswegroup1.db.MenuDAO;
import bounswegroup1.db.CommentDAO;

import bounswegroup1.model.AccessToken;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.ws.rs.client.Client;

/**
 * Hello world!
 *
 */
public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "We are what we eat";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));

        bootstrap.addBundle(new MigrationsBundle<AppConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfig config) {
                return config.getDatabase();
            }
        });
    }

    @Override
    public void run(AppConfig config, Environment env) {
        configureCors(env);

        // Connect to db
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDatabase(), "postgresql");

        jdbi.registerContainerFactory(new OptionalContainerFactory());

        // create dao's and clients
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        final AccessTokenDAO accessTokenDAO = jdbi.onDemand(AccessTokenDAO.class);
        final RecipeDAO recipeDAO = jdbi.onDemand(RecipeDAO.class);
        final MenuDAO menuDAO = jdbi.onDemand(MenuDAO.class);
        final CommentDAO commentDAO = jdbi.onDemand(CommentDAO.class);

        final Client httpClient = new JerseyClientBuilder(env).using(config.getHttpClient())
                .build("httpClient");

        // create resources
        final UserResource userResource = new UserResource(userDAO);
        final SessionResource sessionResource = new SessionResource(accessTokenDAO, userDAO);
        final RecipeResource recipeResource = new RecipeResource(recipeDAO);
        final MenuResource menuResource = new MenuResource(menuDAO);
        final IngredientResource ingredientResource = new IngredientResource(httpClient,
                config.getNutritionixAppId(), config.getNutritionixAppKey());
        final CommentResource commentResource = new CommentResource(commentDAO, userDAO);

        final AvatarUploader avatarUploader = new AvatarUploader(config.getAvatarFactorOne(), config.getAvatarFactorTwo());
        
        // register resources
        env.jersey()
                .register(AuthFactory.binder(
                        new OAuthFactory<AccessToken>(new OAuthAuthenticator(accessTokenDAO),
                                config.getBearerRealm(), AccessToken.class)));
        
        env.jersey().register(MultiPartFeature.class);

        env.jersey().register(userResource);
        env.jersey().register(sessionResource);
        env.jersey().register(recipeResource);
        env.jersey().register(ingredientResource);
        env.jersey().register(menuResource);
        env.jersey().register(commentResource);

    }

    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

}
