package marketsimulator.ordertypes;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;
import marketsimulator.utils.OrderEvaluator;

public class LimitBuyOrder extends OrderEvaluator {

	public LimitBuyOrder(){
		super.setOrderType(OrderTypeEnum.LIMIT_BUY);
	}

	@Override
	public boolean evaluateOrderForExecution(Order order, Float price) {
		//execute when the market price is less than or equal to limit price
		return price <= order.getPrice();
	}
	
	
}
