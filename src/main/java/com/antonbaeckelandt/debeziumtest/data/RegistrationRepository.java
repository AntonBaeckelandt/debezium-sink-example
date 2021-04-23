package com.antonbaeckelandt.debeziumtest.data;

import com.antonbaeckelandt.debeziumtest.models.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    @Query("SELECT r FROM Registration r WHERE r.checkInTimestamp IS NOT NULL AND r.checkOutTimestamp IS NULL")
    Collection<Registration> findCurrentlyActiveRegistrations();

    @Query(value = "SELECT r.* FROM registration r WHERE r.check_in_timestamp >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    Collection<Registration> findRegistrationSince24h();
}
