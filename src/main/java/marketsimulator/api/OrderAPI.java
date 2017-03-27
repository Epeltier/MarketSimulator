package marketsimulator.api;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import marketsimulator.data.OrderService;
import marketsimulator.entities.Order;
import marketsimulator.entities.OrderType;
import marketsimulator.entities.OrderType.OrderTypeEnum;

/**
 * API for placing Orders and checking statuses
 * @author Epeltier
 *
 */
@RestController
public class OrderAPI {

 
    @Autowired
    private OrderService orderService; 

    @RequestMapping("/orders/{sessionId}/open-orders")
    public List<String> getOpenOrders(@PathVariable("sessionId") String sessionId) {
    	return orderService.getOpenOrders(sessionId);
    }
    
    @RequestMapping("/orders/{sessionId}/place")
    public String placeOrder(@PathVariable("sessionId") String sessionId, @RequestParam(value="orderType") String orderType, @RequestParam(value="price") String price, @RequestParam(value="ticker") String ticker, @RequestParam(value="quantity") String quantity) {
    	
    	OrderTypeEnum ote = OrderType.OrderTypeEnum.valueOf(orderType);
    	Integer quantityD = Integer.valueOf(quantity);
    	Float priceD = Float.valueOf(price);
    	
    	Order order =  orderService.placeOrder(sessionId, priceD, ticker, quantityD, ote);
    	return order.getId();
    }
    
    
    @RequestMapping("/orders/{sessionId}/{orderId}/cancel")
    public String cancelOrder(@PathVariable("sessionId") String sessionId, @PathVariable("orderId") String orderId) {
    	return orderService.cancelOrder(sessionId, orderId).toString();
    	
    }
    
    @RequestMapping("/orders/{sessionId}/{orderId}/status")
    public String getOrderStatus(@PathVariable("sessionId") String sessionId, @PathVariable("orderId") String orderId) {
    	return orderService.getOrderStatus(sessionId, orderId).toString();
    }
    
    
    
    
    
    
}
