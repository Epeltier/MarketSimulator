package marketsimulator.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * This RESTful java application will simulate a stock market execution by allowing for the opening/closing of the market. When the market is open,
 * a sessionId will be generated. This sessionId represents a thread-safe ID for accessing the specific market. An 'open' market will generate regular stock price updates.
 * Different order types can be submitted and will be evaluated if their criteria fulfills the order type requirements.
 * 
 * TODO - complete the order execution transaction engine to include an account creation upon market open and crediting/debiting the account based on order execution results.
 * 
 * @author EPeltier
 * 3/25/2017
 *
 */
@SpringBootApplication(scanBasePackages = { "marketsimulator" })
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    // Used for tomcat deployment
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}

