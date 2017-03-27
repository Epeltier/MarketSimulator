package marketsimulator.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import marketsimulator.entities.Order;
import marketsimulator.entities.OrderStatus;
import marketsimulator.entities.OrderStatus.OrderStatusEnum;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;
import marketsimulator.repos.OrderRepository;
import marketsimulator.repos.OrderStatusRepository;
import marketsimulator.repos.PricingRepository;
import marketsimulator.utils.OrderEvaluator;

/**
 * Service for managing all market orders - placing orders, checking orders, executing orders.
 * @author EPeltier
 *
 */
@Service
public class OrderService {
	
	@Autowired
	private PricingRepository pricingRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	private OrderType orderTypes = new OrderType();
	//@Autowired 
	//private OrderTransactionEngine orderTransactionEngine;
	
	public OrderStatus executeOrder(String sessionId, Order order){
		
		//TODO - complete order transaction engine - validate order details of quantity/price against account values, 
		//then update account values and order status.
		
		//return orderTransactionEngine.executeOrder(sessionId, order);
		return null;
	}
	
	/**
	 * For the given ticker, check each open order for execution eligibility 
	 * @param ticker
	 */
	public void checkOrders(String sessionId, String ticker){
		
		Float currentPrice = pricingRepository.getLatestValueForTicker(sessionId, ticker);
		List<OrderStatus> openOrders = orderStatusRepository.getPendingOrdersForTicker(sessionId, ticker);
		
		//check each order for execution validity. 
		for(OrderStatus orderStatus:openOrders){
			OrderEvaluator evaluator = orderTypes.getOrderEvaluator(orderStatus.getOrder().getOrderType());
			
			//evaluate, and if eligible, execute order. 
			boolean evaluationDetermination = evaluator.evaluateOrderForExecution(orderStatus.getOrder(), currentPrice);
			if(evaluationDetermination){
				executeOrder(sessionId, orderStatus.getOrder());
			}
		}
	}
	
	
	/**
	 * get open orders on the given session
	 * @param sessionId
	 * @return
	 */
    public List<String> getOpenOrders(String sessionId) {
    	List<OrderStatus> openOrders= orderStatusRepository.getPendingOrders(sessionId);
    	
    	List<String> orderIds = new ArrayList<String>();
    	for(OrderStatus order:openOrders){
    		orderIds.add(order.getOrder().getId());
    	}
    	return orderIds;
    }
    

    /**
     * place a new order
     * @param sessionId
     * @param price
     * @param ticker
     * @param quantity
     * @param type
     * @return
     */
    public Order placeOrder(String sessionId, Float price, String ticker, Integer quantity, OrderTypeEnum type) {
    	
    	Order order = new Order(ticker, price, type, quantity);
    	return orderRepository.saveOrder(sessionId, order);
    }
    
    
    /**
     * cancel an order
     * @return
     */
    public OrderStatusEnum cancelOrder(String sessionId, String orderId) {

    	OrderStatus orderStatus = orderStatusRepository.getOrderStatus(sessionId, orderId);
    	if(orderStatus==null){
    		//if it doesnt exist, return invalid
    		return OrderStatus.OrderStatusEnum.INVALID;
    	}
    	//if not pending, return status
    	else if(!orderStatus.getOrderStatusEnum().equals(OrderStatusEnum.PENDING)){
    		return orderStatus.getOrderStatusEnum();
    	}
    	
    	orderStatus.setOrderStatusEnum(OrderStatusEnum.CANCELED);
    	orderStatusRepository.saveOrderStatus(sessionId, orderStatus);
    	
    	return orderStatus.getOrderStatusEnum();

    	
    }

    public OrderStatusEnum getOrderStatus(String sessionId, String orderId) {
    	OrderStatus orderStatus = orderStatusRepository.getOrderStatus(sessionId, orderId);
    	if(orderStatus==null){
    		return OrderStatus.OrderStatusEnum.INVALID;
    	}
    	
    	return orderStatus.getOrderStatusEnum();
    }
    
	/**
	 * for memory/thread cleanup
	 * @param sessionId
	 */
	public void removeOrderData(String sessionId){
		
		orderRepository.removeOrderData(sessionId);
	}


}
