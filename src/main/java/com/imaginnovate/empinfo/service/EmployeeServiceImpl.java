package com.imaginnovate.empinfo.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imaginnovate.empinfo.bean.EmployeeBean;
import com.imaginnovate.empinfo.bean.EmployeeInfoRespPayload;
import com.imaginnovate.empinfo.bean.EmployeeInfoResponsePayload;
import com.imaginnovate.empinfo.bean.ResponseHeader;
import com.imaginnovate.empinfo.model.Employee;
import com.imaginnovate.empinfo.repository.EmployeeRepository;
import net.sf.json.JSONObject;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeInfoResponsePayload saveEmployee(EmployeeBean employeeBean) throws ParseException {

		EmployeeInfoResponsePayload response = new EmployeeInfoResponsePayload();
		EmployeeInfoRespPayload payLoad=new EmployeeInfoRespPayload();
		ResponseHeader responseHeader=new ResponseHeader();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 

		try
		{
		Employee employee = new Employee();

		employee.setEmployeeid(Integer.parseInt(employeeBean.getEmployeeId()));
		employee.setFirstname(employeeBean.getFirstName());
		employee.setLastname(employeeBean.getLastName());
		employee.setSalary(employeeBean.getSalary());
		employee.setEmail(employeeBean.getEmail());
		String phoneNumbers=String.join(",", employeeBean.getPhoneNumbers());		
		employee.setPhonenumber(phoneNumbers);		
		
		java.util.Date date = sdf.parse(employeeBean.getDoj()); 
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		
		employee.setDoj(sqlStartDate);

		
		Employee emp = employeeRepository.save(employee);
		
		
		responseHeader.setCode(200);
		responseHeader.setStatus("SUCCESS");
		responseHeader.setDesc("Employee Information Saved Successfully");
		payLoad.setEmployee(emp);
		response.setResponsePayload(payLoad);
		response.setHeader(responseHeader);
		}
		catch(Exception e)
		{
			responseHeader.setCode(400);
			responseHeader.setStatus("FAILURE");
			responseHeader.setDesc(e.getMessage());
			response.setResponsePayload(null);
			response.setHeader(responseHeader);
			
			return response;
		}
		
		return response;
	}

	@Override
	public JSONObject getEmployeeTaxDetails(int empId) {

		DecimalFormat df = new DecimalFormat("0.00");
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 

		
		Employee employee = employeeRepository.getReferenceById(empId);

		jsonObject.put("firstName", employee.getFirstname());
		jsonObject.put("lastName", employee.getLastname());
		jsonObject.put("salary", df.format(employee.getSalary()));
		jsonObject.put("emplId", employee.getEmployeeid());
		 jsonObject.put("doj", sdf.format(employee.getDoj()).toString());
		 

		JSONObject jObject = calculateTax(employee.getSalary(), employee.getDoj());

		jsonObject.put("tax", jObject.get("tax"));
		jsonObject.put("cess", jObject.get("cess"));
		jsonObject.put("annualSalary", jObject.get("annualSalary"));

		return jsonObject;
	}

	public JSONObject calculateTax(double salary, java.sql.Date doj) {
		
		JSONObject jsonObject = new JSONObject();
		LocalDate finYearStart;
		LocalDate finYearStop;
		Double salaryPerDay = salary / 30;
		Double annualSalary = 0.00;
		Double tax = 0.00;
		Double cess = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		long monthsBetween =0;

		LocalDate joiningDate = doj.toLocalDate();

		LocalDate start = joiningDate.withDayOfMonth(1);
		LocalDate end = joiningDate.withDayOfMonth(joiningDate.getMonth().length(joiningDate.isLeapYear()));

		if (joiningDate.getMonthValue() <= 3) {
			finYearStart = LocalDate.of(joiningDate.getYear() - 1, 4, 1);
			finYearStop = LocalDate.of(joiningDate.getYear(), 3, 31);
		} else {
			finYearStart = LocalDate.of(joiningDate.getYear(), 4, 1);
			finYearStop = LocalDate.of(joiningDate.getYear() + 1, 3, 31);
		}

		long days = joiningDate.until(end, ChronoUnit.DAYS);

		if (days < 15) 
		{
			monthsBetween = ChronoUnit.MONTHS.between(LocalDate.parse(end.toString()).withDayOfMonth(1),
					LocalDate.parse(finYearStop.toString()).withDayOfMonth(1));

			annualSalary = (salary * monthsBetween) + (days * salaryPerDay);

		} else {
			monthsBetween = ChronoUnit.MONTHS.between(LocalDate.parse(start.toString()).withDayOfMonth(1),
					LocalDate.parse(finYearStop.toString()).withDayOfMonth(1));

			annualSalary = salary * (monthsBetween+1);

		}

		if (annualSalary <= 250000) 
		{
			tax = 0.00;
		} 
		else if (annualSalary > 250000 && annualSalary <= 500000) 
		{
			tax = (annualSalary-250000) * (0.05);
		}
		else if (annualSalary > 500000 && annualSalary <= 1000000) 
		{
			
			tax = (250000 * 0.05) + ((annualSalary-500000) * 0.10);
		} 
		else 
		{
			tax = (250000 * 0.05) + (500000 * 0.10) + ((annualSalary-1000000) * 0.20);
			
			if (annualSalary > 2500000) 
			{
				cess = ((annualSalary - 2500000)) * 0.02;
			}
			
		}


		jsonObject.put("tax", df.format(tax));
		jsonObject.put("cess", df.format(cess));
		jsonObject.put("annualSalary", df.format(annualSalary));

		return jsonObject;
	}

}
