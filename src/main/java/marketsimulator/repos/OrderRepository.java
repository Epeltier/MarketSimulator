package marketsimulator.repos;


import marketsimulator.entities.Order;
/**
 * Repository for Order entities
 * @author EPeltier
 *
 */
public interface OrderRepository {

	

	/**
	 * place a new order
	 * @param sessionId 
	 * @param order
	 */
	public Order saveOrder(String sessionId, Order order);

	/**
	 * for memory/thread cleanup
	 * @param sessionId
	 */
	public void removeOrderData(String sessionId);




}
