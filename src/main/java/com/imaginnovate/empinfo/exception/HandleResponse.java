package com.imaginnovate.empinfo.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginnovate.empinfo.bean.EmployeeInfoResponsePayload;
import com.imaginnovate.empinfo.bean.ResponseHeader;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Component
public class HandleResponse {

	@Autowired
	private ObjectMapper mapper;

	public EmployeeInfoResponsePayload invalidTokenResponse(HttpServletRequest request, int code, String desc) {
		JSONObject response = new JSONObject();
		ResponseHeader responseHeader = new ResponseHeader();
		EmployeeInfoResponsePayload reponsePayLoad = new EmployeeInfoResponsePayload();
		try {

			responseHeader.setCode(code);
			responseHeader.setStatus("FAILED");
			responseHeader.setDesc(desc);
			// payload.setHeader(respheader);
			// responsedtls.setPayload(payload);
			reponsePayLoad.setHeader(responseHeader);
			reponsePayLoad.setResponsePayload(null);
			return reponsePayLoad;
			
		} catch (Exception e) {
			return reponsePayLoad;
		}

	}

}