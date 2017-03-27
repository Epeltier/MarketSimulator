package marketsimulator.data;

import java.util.List;

import org.springframework.stereotype.Service;


/**
 * Service for retrieving pricing data for equities
 * @author Epeltier
 *
 */
public interface PricingService {
	

	/**
	 * return the next value of the equity price 
	 * @param ticker
	 * @param previousValue
	 * @return
	 */
	public Float updatePriceForEquity(String sessionId, String ticker, Float previousValue);
	
	/**
	 * set the initial price value for the given ticker and session
	 * @param sessionId
	 * @param ticker
	 * @return
	 */
	public Float setStartEquityValue(String sessionId, String ticker);
	
	/**
	 * get the most recent price for the given ticker and sessionId
	 * @return
	 */
	public Float getLatestValueForEquity(String sessionId,String ticker); 
	
	
	/**
	 * get the list of historic prices of the given ticker and sessionId
	 * @param sessionId
	 * @param ticker
	 * @return
	 */
	public List<Float> getPricesForTicker(String sessionId, String ticker);
	
	/**
	 * purge repo of pricing data for a session
	 * @param sessionId
	 */
	public void removePricingData(String sessionId);

}
