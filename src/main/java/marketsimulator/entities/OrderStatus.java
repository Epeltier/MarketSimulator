package marketsimulator.entities;

public class OrderStatus {

	public enum OrderStatusEnum{
		PENDING,
		CANCELED,
		EXECUTED,
		INVALID;
	}
	
	private Order order;
	private OrderStatusEnum orderStatusEnum;
	
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public OrderStatusEnum getOrderStatusEnum() {
		return orderStatusEnum;
	}
	public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
		this.orderStatusEnum = orderStatusEnum;
	}
	

}
