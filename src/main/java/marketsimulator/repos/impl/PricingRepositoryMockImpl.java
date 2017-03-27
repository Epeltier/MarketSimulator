package marketsimulator.repos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import marketsimulator.repos.PricingRepository;

/**
 * mock repository for persisting price data
 * @author Epeltier
 *
 */
@Service
public class PricingRepositoryMockImpl implements PricingRepository {

	//for mocking data repo
	private Map<String, PricingValues> pricingValueMap = new ConcurrentHashMap<String, PricingValues>();
	
	@Override
	public Float getCurrentPriceForTicker(String ticker) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void removePricingData(String sessionId){
		getPricingValueMap().remove(sessionId);
	}
	
    private Map<String, PricingValues> getPricingValueMap() {
		return pricingValueMap;
	}


	@Override
	public void setStartEquityValue(String sessionId, String ticker, Float initialValue) {
		
		PricingValues pv = new PricingValues(sessionId);
		pv.setLatestValueForEquity(ticker,initialValue);
		pricingValueMap.put(sessionId, pv);
	}
	
	
	@Override
	public Float getLatestValueForTicker(String sessionId, String ticker) {
		
		PricingValues pv = getPricingValueMap().get(sessionId);
		List<Float> values = pv.getPricesForTicker(ticker);
		    	
    	if(values!=null){
    		return values.get(values.size()-1);
    	}  	
		return null;
	}
	
	@Override
	public void saveUpdatedPrice(String sessionId, String ticker, Float updatedPrice) {
		PricingValues pv = getPricingValueMap().get(sessionId);
		pv.setLatestValueForEquity(ticker,updatedPrice);
	}
	
	@Override
	public List<Float> getPricesForTicker(String sessionId, String ticker) {
		PricingValues pv = getPricingValueMap().get(sessionId);
		List<Float> values = pv.getPricesForTicker(ticker);
		return values;
		
	}


	private class PricingValues{
    	
		public PricingValues(String sessionId){
			setSessionId(sessionId);
		}
    	private String sessionId;
    	private Map<String,List<Float>> equities = new HashMap<String,List<Float>>(); 
    	
        public List<Float> setLatestValueForEquity(String ticker, Float latestValue){
        	
        	List<Float> values = equities.get(ticker);
        	if(values!=null){
        		values.add(latestValue);
        		equities.put(ticker, values);
        	}
        	else{
        		values = new ArrayList<Float>();
        		values.add(latestValue);
        		equities.put(ticker, values);
        	}
        	
        	return values;
        }
        
        public List<Float> getPricesForTicker(String ticker){
        	return this.equities.get(ticker);
        }

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
        
    	
    	
    }

}
