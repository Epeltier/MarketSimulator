package marketsimulator.repos;

import java.util.List;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderStatus;
import marketsimulator.entities.OrderStatus.OrderStatusEnum;

/**
 * Repository for OrderStatus entity
 * @author EPeltier
 *
 */
public interface OrderStatusRepository {
	
	/**
	 * get the latest status of the given order for the sessionId
	 * @param sessionId
	 * @param orderId
	 * @return
	 */
	public OrderStatus getOrderStatus(String sessionId, String orderId);

	/**
	 * persist an orderStatus
	 * @param sessionId
	 * @param orderStatus
	 */
	public void saveOrderStatus(String sessionId, OrderStatus orderStatus);

	/**
	 * get open orders on the given session
	 * @param sessionId
	 * @return
	 */
	List<OrderStatus> getPendingOrders(String sessionId);

	/**
	 * get open/pending orders for the given session and ticker
	 * @param sessionId
	 * @param ticker
	 * @return
	 */
	public List<OrderStatus> getPendingOrdersForTicker(String sessionId, String ticker);

	/**
	 * get orders for the given session, and status
	 * @param sessionId
	 * @param status
	 * @return
	 */
	public List<OrderStatus> getOrdersByStatus(String sessionId, String ticker, OrderStatusEnum status);

	/**
	 * get orders for the given session, ticker, and status
	 * @param sessionId
	 * @param ticker
	 * @param status
	 * @return
	 */
	public List<OrderStatus> getOrdersByStatusAndTicker(String sessionId, String ticker, OrderStatusEnum status);


}
