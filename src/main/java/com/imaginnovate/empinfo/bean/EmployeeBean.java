package com.imaginnovate.empinfo.bean;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.imaginnovate.empinfo.validator.DateFormat;
import lombok.Data;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Data
public class EmployeeBean {
	
	@NotEmpty(message="employeeId should not be null or empty")
	@Pattern(regexp = "^[0-9]{6,6}", message="Invalid employeeId")
	private String employeeId;
	@NotEmpty(message = "firstname Should Not Be Empty")
	private String firstName;
	private String lastName;
	
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
    @NotEmpty(message = "Phone Number Should Not Be Empty")
    @NotNull(message = "\"Phone Number Should Not Be Null")
	private List<String>  phoneNumbers;	
    
	@DateFormat(format = "dd-MM-yyyy",message="Invalid Date Of Joining Format(Expected: dd-MM-yyyy")
	private String doj;
	
	private double salary;

}
