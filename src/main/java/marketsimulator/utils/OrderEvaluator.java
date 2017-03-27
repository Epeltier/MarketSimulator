package marketsimulator.utils;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType.OrderTypeEnum;

/**
 * abstract class for wrapping order types and controller their criteria for being executed
 * @author EPeltier
 *
 */
public abstract class OrderEvaluator {

	
	private OrderTypeEnum orderType;
	
	/**
	 * Method to evaluate if the given order should execute with the current price
	 * @param order
	 * @param price
	 * @return
	 */
	public abstract boolean evaluateOrderForExecution(Order order, Float price);
	
	
	public OrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderTypeEnum orderType) {
		this.orderType = orderType;
	} 
}
