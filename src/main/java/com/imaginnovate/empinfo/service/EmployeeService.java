package com.imaginnovate.empinfo.service;

import java.text.ParseException;
import org.springframework.stereotype.Service;
import com.imaginnovate.empinfo.bean.EmployeeBean;
import com.imaginnovate.empinfo.bean.EmployeeInfoResponsePayload;
import net.sf.json.JSONObject;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Service
public interface EmployeeService {
	
	public EmployeeInfoResponsePayload saveEmployee(EmployeeBean employeeBean) throws ParseException;
	
	public JSONObject getEmployeeTaxDetails(int empId);

}
