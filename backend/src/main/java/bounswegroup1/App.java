package bounswegroup1;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.assets.AssetsBundle;
import org.skife.jdbi.v2.DBI;
import bounswegroup1.resource.UserResource;
import bounswegroup1.db.UserDAO;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

/**
 * Hello world!
 *
 */
public class App extends Application<AppConfig>
{
    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName(){
        return "We are what we eat";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap){
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(AppConfig config, Environment env){
        configureCors(env);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDatabase(), "postgresql");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        final UserResource userResource = new UserResource(userDAO);
        env.jersey().register(userResource);
    }

    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
      }
      
}
