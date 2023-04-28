package ibf2022.assessment.paf.batch3.services;

import ibf2022.assessment.paf.batch3.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BeerService {

	@Autowired
	private OrderRepository orderRepository;

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public String placeOrder(int breweryId, List<Map<String, Integer>> orders) {
		// TODO: Task 5 
		String orderId = UUID.randomUUID().toString().substring(0, 8);
		Map<String, Object> orderData = new HashMap<>();
		orderData.put("orderId", orderId);
		orderData.put("date", LocalDateTime.now());
		orderData.put("breweryId", breweryId);
		orderData.put("orders", orders);

		orderRepository.insertOrder(orderData);
		return orderId;
	}

}
