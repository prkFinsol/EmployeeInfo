package com.imaginnovate.empinfo.bean;

import lombok.Data;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */


@Data
public class EmployeeInfoResponsePayload {

	private ResponseHeader header;
	
	private EmployeeInfoRespPayload responsePayload;
}