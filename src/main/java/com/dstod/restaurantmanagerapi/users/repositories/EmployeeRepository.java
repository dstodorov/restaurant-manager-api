package com.dstod.restaurantmanagerapi.users.repositories;

import com.dstod.restaurantmanagerapi.users.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Long, Employee> {
}
