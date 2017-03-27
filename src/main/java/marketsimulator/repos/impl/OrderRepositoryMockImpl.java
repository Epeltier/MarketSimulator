package marketsimulator.repos.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import marketsimulator.entities.Order;
import marketsimulator.repos.OrderRepository;

/**
 * mock repository for persisting order data.
 * @author EPeltier
 *
 */
@Service
public class OrderRepositoryMockImpl implements OrderRepository {

	//for mocking data repo
	private Map<String, OrderPersistence> mockOrderPersistance = new ConcurrentHashMap<String, OrderPersistence>();
	
	
	@Override
	public Order saveOrder(String sessionId, Order order) {
		
		getMockOrderPersistance().get(sessionId).addOrder(order);
		return order;
	}

	
	private Map<String, OrderPersistence> getMockOrderPersistance() {
		return mockOrderPersistance;
	}
	

	@Override
	public void removeOrderData(String sessionId){
		
		getMockOrderPersistance().remove(sessionId);
	}

	/**
	 * internal class mocking persistance of orders
	 *
	 */
	private class OrderPersistence{
		
		public OrderPersistence(String sessionId){
			this.setSessionId(sessionId);
		}
		
		
		
		private String sessionId;
		
		List<Order> orders = new ArrayList<Order>();
		
		public void addOrder(Order order){
			orders.add(order);
		}

		
		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		
	}

}
