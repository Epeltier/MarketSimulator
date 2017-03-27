package marketsimulator.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import marketsimulator.entities.MarketStatus;
import marketsimulator.services.MarketManagerService;

/**
 * API for opening/closing the market, and getting equity prices
 * @author EPeltier
 *
 */
@RestController
public class MarketManagerAPI {

    private final AtomicLong counter = new AtomicLong();
 
    @Autowired
    private MarketManagerService marketManagerService; 

    @RequestMapping("/manager/open")
    public String openMarket() {
    	
    	String sessionId = marketManagerService.openMarket();
    	return sessionId;
    }
    
    
    @RequestMapping("/manager/{sessionId}/status")
    public MarketStatus checkStatus(@PathVariable("sessionId") String sessionId) {
    	
    	MarketStatus marketStatus = marketManagerService.checkIfMarketOpen(sessionId);
    	return marketStatus; 

    }

    @RequestMapping("/manager/{sessionId}/close")
    public MarketStatus closeMarket(@PathVariable("sessionId") String sessionId) {
    	
    	MarketStatus marketStatus = marketManagerService.closeMarket(sessionId);
    	return marketStatus; 

    }

    @RequestMapping("/manager/{sessionId}/{ticker}/values")
    public List<Float> getMarketPrices(@PathVariable("sessionId") String sessionId, @PathVariable("ticker") String ticker) {
    	
    	List<Float> marketStatus = marketManagerService.getTickerHistoricPrices(sessionId, ticker);
    	return marketStatus; 

    }
    
    
}
