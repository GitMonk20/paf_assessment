package ibf2022.assessment.paf.batch3.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OrderRepository {

	// TODO: Task 5
    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertOrder(Map<String, Object> order) {
        mongoTemplate.insert(order, "orders");
    }
}
