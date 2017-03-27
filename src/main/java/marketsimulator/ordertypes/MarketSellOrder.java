package marketsimulator.ordertypes;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;
import marketsimulator.utils.OrderEvaluator;

public class MarketSellOrder extends OrderEvaluator {

	public MarketSellOrder(){
		super.setOrderType(OrderTypeEnum.MARKET_SELL);
	}

	@Override
	public boolean evaluateOrderForExecution(Order order, Float price) {
		//market order executes at any price
		return true;
	}
	
	
}