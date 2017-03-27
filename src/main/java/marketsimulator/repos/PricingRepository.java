package marketsimulator.repos;

import java.util.List;

/**
 * repository for equity price values
 * @author EPeltier
 *
 */
public interface PricingRepository {

	/**
	 * Get the latest price for the given ticker.
	 * @param ticker
	 * @return
	 */
	public Float getCurrentPriceForTicker(String ticker);

	/**
	 * persist the opening equity price for the given session and ticker
	 * @param sessionId
	 * @param ticker
	 * @param initialValue
	 */
	public void setStartEquityValue(String sessionId, String ticker, Float initialValue);
	
	public Float getLatestValueForTicker(String sessionId, String ticker);

	/**
	 * persist the updated price for the given session and ticker
	 * @param sessionId
	 * @param ticker
	 * @param updatedPrice
	 */
	public void saveUpdatedPrice(String sessionId, String ticker, Float updatedPrice);

	/**
	 * get the most recent price for the given ticker and sessionId
	 * @return
	 */
	public List<Float> getPricesForTicker(String sessionId, String ticker);

	
	/**
	 * purge repo of pricing data for a session
	 * @param sessionId
	 */
	public void removePricingData(String sessionId);
	

}
