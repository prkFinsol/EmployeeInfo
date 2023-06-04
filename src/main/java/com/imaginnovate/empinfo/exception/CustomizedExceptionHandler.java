package com.imaginnovate.empinfo.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginnovate.empinfo.bean.EmployeeInfoResponsePayload;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private HandleResponse handleResp;


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleInvalidInputException(ResourceNotFoundException ex,
			HttpServletRequest request) {

		EmployeeInfoResponsePayload response = new EmployeeInfoResponsePayload();

		try {
			response = handleResp.invalidTokenResponse(request, 400, ex.getMessage());
		} catch (Exception e) {
		}
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}