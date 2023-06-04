package com.imaginnovate.empinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imaginnovate.empinfo.model.Employee;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
