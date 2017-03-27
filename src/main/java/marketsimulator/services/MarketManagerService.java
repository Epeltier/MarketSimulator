package marketsimulator.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marketsimulator.data.OrderService;
import marketsimulator.data.PricingService;
import marketsimulator.entities.MarketStatus;
import marketsimulator.utils.MarketSimulatorEngine;

/**
 * 
 * Service to function as a thread-safe controller for opening/closing the market and service and placing trade orders. 
 * @author EPeltier
 *
 */
@Service
public class MarketManagerService {

	@Autowired
	private PricingService pricingService; 
	@Autowired 
	private OrderService orderService;  
	
	//thread safe synchronized markets
	Map<String, MarketSimulatorEngine> marketsMap = new ConcurrentHashMap<String, MarketSimulatorEngine>(new HashMap<String,MarketSimulatorEngine>());
	Map<String, Timer> marketStopTimers = new ConcurrentHashMap<String, Timer>(new HashMap<String,Timer>());
	
	
	//timeout market after 15 minutes (15*60*1000)
	private static final long MARKET_TIMEOUT_LENGTH = 900000;
	
	
	
	public String openMarket(){
		
		final String sessionId = generateSessionId();
		startNewMarketSimulator(sessionId);
		return sessionId;
		
	}
	
	private String startNewMarketSimulator(String sessionId){
		
		MarketSimulatorEngine mse = new MarketSimulatorEngine(sessionId, pricingService, orderService);
		mse.openMarket();
		//track the open market across threads
		marketsMap.put(sessionId, mse);
		//close the market after MARKET_TIMEOUT_LENGTH
		addMarketCloseTimer(sessionId);
		return sessionId;
		
	}
	
	public MarketStatus closeMarket(String sessionId){
		
		MarketSimulatorEngine mse = marketsMap.get(sessionId);
		//if it exists / isn't closed already
		if(mse!=null){		
			MarketStatus status = mse.closeMarket();
			//remove the timer
			marketStopTimers.get(sessionId).cancel();
			if(status==MarketStatus.CLOSED){
				marketsMap.remove(sessionId);
			}
			return status;
		}
		return MarketStatus.CLOSED;
	}
	
	public MarketStatus checkIfMarketOpen(String sessionId){
		
		MarketSimulatorEngine mse = marketsMap.get(sessionId);
		if(mse==null){
			return MarketStatus.CLOSED;
		}
		return mse.getMarketStatus();
		
	}
	

	public List<Float> getTickerHistoricPrices(String sessionId, String ticker){

		return pricingService.getPricesForTicker(sessionId, ticker);
		
	}
	
	
	private String generateSessionId(){
		
		return UUID.randomUUID().toString();
	}
	
	
	private void addMarketCloseTimer(String sessionId){
		
		Timer timer = new Timer();
        timer.schedule(new StopMarketTask(sessionId), MARKET_TIMEOUT_LENGTH);
        marketStopTimers.put(sessionId, timer);
        
	}
	
	/**
	 * TimerTask for closing/ending an existing market. This is to prevent orphaned open sessions. 
	 */
    class StopMarketTask extends TimerTask {
    	private String sessionId;
    	
    	public StopMarketTask(String sessionId){
    		this.sessionId = sessionId;
    	}
        public void run() {
        	closeMarket(sessionId);
            marketStopTimers.get(sessionId).cancel();
        }
    }
	
	
}
