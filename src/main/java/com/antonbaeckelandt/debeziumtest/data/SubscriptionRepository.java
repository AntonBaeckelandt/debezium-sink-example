package com.antonbaeckelandt.debeziumtest.data;

import com.antonbaeckelandt.debeziumtest.models.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
}
