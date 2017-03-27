package marketsimulator.repos.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderStatus;
import marketsimulator.entities.OrderStatus.OrderStatusEnum;
import marketsimulator.repos.OrderStatusRepository;

/**
 * 
 *  * mock repository for persisting orderstatus data.
 * @author Epeltier
 *
 */
@Service
public class OrderStatusRepositoryMockImpl implements OrderStatusRepository {

	//for mocking data repo
	private Map<String, OrderStatusPersistence> mockOrderPersistance = new ConcurrentHashMap<String, OrderStatusPersistence>();
	
	@Override
	public OrderStatus getOrderStatus(String sessionId, String orderId) {

		return mockOrderPersistance.get(sessionId).getOrderStatus(orderId);
	}

	@Override
	public void saveOrderStatus(String sessionId, OrderStatus orderStatus) {
		mockOrderPersistance.get(sessionId).addOrderStatus(orderStatus);
		
	}

	@Override
	public List<OrderStatus> getPendingOrders(String sessionId) {
		return mockOrderPersistance.get(sessionId).getOrdersByStatus(OrderStatusEnum.PENDING);
	}
	
	@Override
	public List<OrderStatus> getOrdersByStatusAndTicker(String sessionId, String ticker,OrderStatusEnum status) {
		return mockOrderPersistance.get(sessionId).getOrdersByStatusAndTicker(status,ticker);
	}
	
	@Override
	public List<OrderStatus> getOrdersByStatus(String sessionId, String ticker,OrderStatusEnum status) {
		return mockOrderPersistance.get(sessionId).getOrdersByStatus(status);
	}

	@Override
	public List<OrderStatus> getPendingOrdersForTicker(String sessionId, String ticker) {
		return mockOrderPersistance.get(sessionId).getOrdersByStatusAndTicker(OrderStatusEnum.PENDING,ticker);
	}
	
	
	
	

	/**
	 * internal class mocking persistance of ordersStatus
	 *
	 */
	private class OrderStatusPersistence{
		
		public OrderStatusPersistence(String sessionId){
			this.setSessionId(sessionId);
		}
		
		private String sessionId;
		
		List<OrderStatus> orderStatuses = new ArrayList<OrderStatus>();
		
		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		
		public void addOrderStatus(OrderStatus orderStatus){
			
			this.orderStatuses.add(orderStatus);
		}
		
		public OrderStatus getOrderStatus(String orderId){
			
			for(OrderStatus orderStatus: orderStatuses){
				
				if(orderStatus.getOrder()!=null){
					if(orderStatus.getOrder().getId().equals(orderId)){
						return orderStatus;
					}
				}
			}
			return null;
			
		}
		
		public List<OrderStatus> getOrdersByStatus(OrderStatusEnum status){
			
			List<OrderStatus> statuses = new ArrayList<OrderStatus>();
			
				for(OrderStatus orderStatus: orderStatuses){
				
				if(orderStatus.getOrder()!=null){
					if(orderStatus.getOrderStatusEnum().equals(status)){
						
						statuses.add(orderStatus);
					}
				}
			}
			return statuses;
		}
		
		public List<OrderStatus> getOrdersByStatusAndTicker(OrderStatusEnum status, String ticker){
			
			List<OrderStatus> statuses = new ArrayList<OrderStatus>();
			
				for(OrderStatus orderStatus: orderStatuses){
				
				if(orderStatus.getOrder()!=null){
					if(orderStatus.getOrderStatusEnum().equals(status) && orderStatus.getOrder().getTicker().equals(ticker)){
						
						statuses.add(orderStatus);
					}
				}
			}
			return statuses;
		}

		
	}


	
}
