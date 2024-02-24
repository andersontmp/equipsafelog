package com.equipsafelog.core.to;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Response for API REST
 */
public class ResponseObjectVO {

	/**
	 * HttpStatus
	 */
	private HttpStatus status;
	private String message;
	private Object object;
	private String errorCode;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	@JsonIgnore
	public void setOk(String message) {
		this.status = HttpStatus.OK;
		this.message = (message == null || message.equals("")) ? HttpStatus.OK.name() : message;
	}
	@JsonIgnore
	public void setErrorInternalError(String message) {
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.message = (message == null || message.equals("")) ? HttpStatus.INTERNAL_SERVER_ERROR.name() : message;
	}
	@JsonIgnore
	public void setErrorNotFound(String message) {
		this.status = HttpStatus.NOT_FOUND;
		this.message = (message == null || message.equals("")) ? HttpStatus.NOT_FOUND.name() : message;
		
	}
	@JsonIgnore
	public void setErrorNoContent(String message) {
		this.status = HttpStatus.NO_CONTENT;
		this.message = (message == null || message.equals("")) ? HttpStatus.NO_CONTENT.name() : message;
	}
	@JsonIgnore
	public void setErrorNotAcceptable(String message) {
		this.status = HttpStatus.NOT_ACCEPTABLE;
		this.message = (message == null || message.equals("")) ? HttpStatus.NOT_ACCEPTABLE.name() : message;
	}

	public enum ErrorCode {
		NO_AUTOREFERENCE_PERMITTED("NO_AUTOREFERENCE_PERMITTED"),
		DELETE_NO_ERROR("DELETE_NO_ERROR"),
		UPDATE_NO_ERROR("UPDATE_NO_ERROR"),
		CREATE_NO_ERROR("CREATE_NO_ERROR"),
		NOT_EXISTS("NOT_EXISTS"),
		ALREADY_PRESENT("ALREADY_PRESENT"),
		FK_VIOLATION("FK_VIOLATION"),
		ID_NULL("ID_NULL"),
		VALIDATION_ERROR("VALIDATION_ERROR"),
		OTHER("OTHER"),
		NO_ERROR("NO_ERROR"), 
		CHECK_WARNINGS("CHECK_WARNINGS"), 
		IMPORT_ERROR("IMPORT_ERROR");
		
		static Map<String, ErrorCode> nameVsType;
		private String name;

		private ErrorCode(String name) {
			this.name = name;
		}

		public static ErrorCode valueFor(String typeName) {
			return nameVsType.get(typeName.toLowerCase());
		}

		public String getName() {
			return this.name;
		}

		static {
			nameVsType = new HashMap<String, ErrorCode>();
			for (ErrorCode type : ErrorCode.values()) {
				nameVsType.put(type.getName(), type);
			}
		}
	}
}
