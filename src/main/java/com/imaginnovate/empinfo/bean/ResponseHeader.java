package com.imaginnovate.empinfo.bean;

import lombok.Data;

/**
 * @author Rama Krishna
 *
 */

@Data
public class ResponseHeader {
	
	private String status;
	private int code;
	private String desc;
}