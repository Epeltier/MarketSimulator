package marketsimulator.ordertypes;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;
import marketsimulator.utils.OrderEvaluator;

public class LimitSellOrder extends OrderEvaluator {

	public LimitSellOrder(){
		super.setOrderType(OrderTypeEnum.LIMIT_SELL);
	}

	@Override
	public boolean evaluateOrderForExecution(Order order, Float price) {
		//execute when the market price exceeds or is equal to the limit price.
		return price >= order.getPrice();
	}
	
	
}
