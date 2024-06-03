package com.lpdecastro.jobmanager.exception;

import com.lpdecastro.jobmanager.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.ExpressionAuthorizationDecision;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class AppExceptionHandler {

    private static final Pattern AUTHORITY_PATTERN = Pattern.compile("SCOPE_([A-Z]+)");

    private final MessageSource messageSource;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDto> handleAppException(AppException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String message = messageSource.getMessage(ex.getErrorMessage().getCode(), null, Locale.getDefault());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, message);
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus);

        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> fields = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError ->
                    fields.put(fieldError.getField(), fieldError.getDefaultMessage()));
            errorResponseDto.setFields(fields);
            errorResponseDto.setMessage("Some fields are invalid");
        } else if (bindingResult.getGlobalError() != null) {
            errorResponseDto.setMessage(bindingResult.getGlobalError().getDefaultMessage());
        }

        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class,
            HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        String authExpressionString = ((ExpressionAuthorizationDecision) ex.getAuthorizationResult()).getExpression()
                .getExpressionString();
        List<String> authorities = extractAuthoritiesFromExpressionString(authExpressionString);
        String message = "Access Denied. Must have role(s): " + String.join(", ", authorities);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, message);
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAppException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus, ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    public static List<String> extractAuthoritiesFromExpressionString(String authExpressionString) {
        Matcher matcher = AUTHORITY_PATTERN.matcher(authExpressionString);
        return matcher.results()
                .map(match -> match.group(1))
                .collect(Collectors.toList());
    }
}
