package marketsimulator.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marketsimulator.data.OrderService;
import marketsimulator.data.PricingService;
import marketsimulator.entities.MarketStatus;


/**
 * 
 * Encapsulation of a market simulation engine for retrieving and tracking equity prices and maintaining cycles
 * @author EPeltier
 */
@Service
public class MarketSimulatorEngine {
	
	private PricingService pricingService; 
	private OrderService orderService; 
	private String sessionId;
	
	private MarketStatus marketStatus; 
	private Timer marketIteration;


	private static final long MARKET_ITERATION_INCREMENT=1000;
	
	public MarketSimulatorEngine(String sessionId, PricingService pricingService, OrderService orderService){

		this.pricingService= pricingService; 
		this.orderService= orderService; 
		this.sessionId = sessionId;
		
	}
	
	public void openMarket(){
		System.out.println("opening");
		setMarketStatus(MarketStatus.OPEN);
		marketIteration = new Timer();
		marketIteration.schedule(new MarketIteration(), MARKET_ITERATION_INCREMENT,MARKET_ITERATION_INCREMENT);
		setInitialValues();
		
	}
	
	public MarketSimulatorEngine(){
		
	}
	
	public MarketStatus closeMarket(){
		
		setMarketStatus(MarketStatus.CLOSED);
		marketIteration.cancel();
		pricingService.removePricingData(this.sessionId);
		orderService.removeOrderData(this.sessionId);
		return MarketStatus.CLOSED;
		
	}
	

	public MarketStatus getMarketStatus() {
		return marketStatus;
	}

	private void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}
	
	private void setInitialValues(){
		 Set<String> trackingEquites = getEquitiesList();
		
		for(String ticker:trackingEquites){
			Float initialValue= pricingService.setStartEquityValue(this.sessionId, ticker);
		}
	}
	
	
	/**
	 * TimerTask for closing/ending an existing market. This is to prevent orphaned open sessions. 
	 */
    class MarketIteration extends TimerTask {
    	 	
    	private void iteratePrices(){
    		for(String ticker:getEquitiesList()){
    			
    			Float existingPrice = pricingService.getLatestValueForEquity(sessionId,ticker); 
    			Float latestPrice = pricingService.updatePriceForEquity(sessionId, ticker, existingPrice);
    			
    			processOrderChecks();
    		}
    		
    	}
        public void run() {
        	iteratePrices();
        }
    }

    private void processOrderChecks(){
    	
    	//call order engine for each ticker?
    	
    }
    
    /**
     * TODO - these should come from a properties files or data service. for mock purposes here - they're static.
     * @return
     */
    public Set<String> getEquitiesList(){
    	
    	Set<String> equitiesList = new HashSet<String>();
    	equitiesList.add("ABC");
    	
    	return equitiesList;
    	
    	
    }

}
