package marketsimulator.entities;

import java.util.HashMap;
import java.util.Map;

import marketsimulator.ordertypes.LimitBuyOrder;
import marketsimulator.ordertypes.LimitSellOrder;
import marketsimulator.ordertypes.MarketBuyOrder;
import marketsimulator.ordertypes.MarketSellOrder;
import marketsimulator.utils.OrderEvaluator;

public class OrderType{
	
	public enum OrderTypeEnum {
	MARKET_BUY,
	MARKET_SELL,
	LIMIT_BUY,
	LIMIT_SELL;
	}
	
	private Map<OrderTypeEnum, OrderEvaluator> orderEvaluators;
	
	public OrderType(){
		orderEvaluators = new HashMap<OrderTypeEnum, OrderEvaluator>(); 
		orderEvaluators.put(OrderTypeEnum.MARKET_BUY, new MarketBuyOrder());
		orderEvaluators.put(OrderTypeEnum.MARKET_SELL, new MarketSellOrder());
		orderEvaluators.put(OrderTypeEnum.LIMIT_BUY, new LimitBuyOrder());
		orderEvaluators.put(OrderTypeEnum.LIMIT_SELL, new LimitSellOrder());
	}
	
	
	public OrderEvaluator getOrderEvaluator(OrderTypeEnum ote){
		return orderEvaluators.get(ote);
	}


}
