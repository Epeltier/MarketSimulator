package marketsimulator.entities;

import java.util.UUID;

import marketsimulator.entities.OrderType.OrderTypeEnum;

public class Order {

	private Float price;
	private String ticker;
	private Integer quantity;
	private OrderTypeEnum orderType;
	
	private String id; 
	
	public Order(String ticker, Float price, OrderTypeEnum orderType, Integer quantity){
		this.setPrice(price);
		this.setTicker(ticker);
		this.setOrderType(orderType);
		this.setQuantity(quantity);
		this.setId(UUID.randomUUID().toString());
	}
	
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public OrderTypeEnum getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderTypeEnum orderType) {
		this.orderType = orderType;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	} 
	
}
