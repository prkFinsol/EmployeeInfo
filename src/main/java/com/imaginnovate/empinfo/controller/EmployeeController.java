package com.imaginnovate.empinfo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imaginnovate.empinfo.bean.EmployeeBean;
import com.imaginnovate.empinfo.bean.EmployeeInfoResponsePayload;
import com.imaginnovate.empinfo.exception.HandleResponse;
import com.imaginnovate.empinfo.exception.ResourceNotFoundException;
import com.imaginnovate.empinfo.model.Employee;
import com.imaginnovate.empinfo.repository.EmployeeRepository;
import com.imaginnovate.empinfo.service.EmployeeService;
import net.sf.json.JSONObject;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empinfo/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private HandleResponse handleResp;

	@PostMapping("/save")
	public ResponseEntity<Object> saveEmployeeData(@Valid @RequestBody EmployeeBean employeeBean,
			BindingResult bindingResult, HttpServletRequest request) throws IOException, ParseException {

		EmployeeInfoResponsePayload response = new EmployeeInfoResponsePayload();

		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			response = handleResp.invalidTokenResponse(request, 400, errors.get(0).getDefaultMessage());

		} else {
			response = employeeService.saveEmployee(employeeBean);

		}

		return new ResponseEntity<>(response,
				response.getHeader().getCode() == 400 ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	}

	@GetMapping("/getEmpTaxDetails/{empid}")
	public ResponseEntity<JSONObject> getEmpTaxDetails(@PathVariable int empid) {

		Optional<Employee> employee = employeeRepository.findById(empid);

		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("Employee ID " + empid + " Not Found!");
		}

		JSONObject jsonObject = employeeService.getEmployeeTaxDetails(empid);

		return new ResponseEntity<>(jsonObject, HttpStatus.OK);
	}

}
