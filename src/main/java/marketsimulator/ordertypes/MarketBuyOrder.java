package marketsimulator.ordertypes;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;
import marketsimulator.utils.OrderEvaluator;

public class MarketBuyOrder extends OrderEvaluator {

	public MarketBuyOrder(){
		super.setOrderType(OrderTypeEnum.MARKET_BUY);
	}

	@Override
	public boolean evaluateOrderForExecution(Order order, Float price) {
		//market order executes at any price
		return true;
	}
	
	
}
