package marketsimulator.data.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marketsimulator.data.PricingService;
import marketsimulator.repos.PricingRepository;

/**
 * 
 * Mock implementation of a service to provide pricing data for market equities. This would mock a data repository or some data feed. 
 * @author EPeltier
 * 
 *
 */
@Service
public class PricingServiceMockImpl implements PricingService {

	@Autowired
	private PricingRepository pricingRepo;
	
	private static final Float OPEN_VALUE=10.00f;
	
	@Override
	public Float updatePriceForEquity(String sessionId, String ticker, Float previousValue) {
		
		//this is an arbitrary generation of a new equity value. Generate one with gaussian distribution 
		//(base 0, std deviation 1) from the current value
		
		if(previousValue==null){
			previousValue=getStartEquityValue(ticker);
		}
		Random r = new Random();
		double changeAmount = r.nextGaussian();
		//change the value by that percent
		double nextValue = previousValue*(changeAmount*.01) + previousValue;
		if(nextValue<0){
			nextValue=0;
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		Float updatedPrice =  Float.parseFloat(df.format(nextValue));
		pricingRepo.saveUpdatedPrice(sessionId, ticker, updatedPrice);
		
		return updatedPrice; 
	}
	
	public Float getLatestValueForEquity(String sessionId,String ticker){
		
	  return pricingRepo.getLatestValueForTicker(sessionId,ticker);
	}
	
	public Float getStartEquityValue(String ticker){
		
		return OPEN_VALUE;
	}
	
	public void removePricingData(String sessionId){
		pricingRepo.removePricingData(sessionId);
	}


	@Override
	public Float setStartEquityValue(String sessionId, String ticker) {
		
		Float initialValue = getStartEquityValue(ticker);
		pricingRepo.setStartEquityValue(sessionId,ticker, initialValue);
		return initialValue;
	}

	@Override
	public List<Float> getPricesForTicker(String sessionId, String ticker) {
		return pricingRepo.getPricesForTicker(sessionId, ticker);
	}
	
}
