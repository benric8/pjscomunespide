package pe.gob.pj.pide.api.app.exceptions;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	Logger log = LogManager.getLogger(CustomGlobalExceptionHandler.class);

	// @Validate For Validating Path Variables and Request Parameters
	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	// error handle for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = ((FieldError) error).getDefaultMessage();
			String valor = (((FieldError) error).getRejectedValue() == null) ? "null"
					: ((FieldError) error).getRejectedValue().toString();
			errors.put(fieldName + " (" + valor + ")", errorMessage);
		});
		body.put("errors", errors);
		log.warn(errors);
		return new ResponseEntity<>(body, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" no soportado por el request. Soporta el método ");
		ex.getSupportedHttpMethods().forEach((error) -> {
			builder.append(error + " ");
		});
		body.put("errors", builder.toString());
		log.error(builder.toString());
		return new ResponseEntity<>(body, headers, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "No se ha encontrado ningun controlador para  " + ex.getHttpMethod() + " " + ex.getRequestURL();
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.NOT_FOUND);

		Map<String, String> errors = new HashMap<>();
		errors.put("motivo", error);
		errors.put("mensaje", ex.getLocalizedMessage());
		body.put("error", errors);
		return new ResponseEntity<Object>(body, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getLocalizedMessage());
		builder.append(" currió un error ");
		body.put("errors", builder.toString());
		log.error(builder.toString());
		return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}