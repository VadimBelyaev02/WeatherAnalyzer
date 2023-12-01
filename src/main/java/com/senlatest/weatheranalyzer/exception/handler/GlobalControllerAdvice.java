package com.senlatest.weatheranalyzer.exception.handler;


import com.senlatest.weatheranalyzer.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    //    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ApiResponseDto<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
//        final ApiResponseDto<?> apiResponseDto = ApiResponseDto.badApiResponse(
//                exception.getMessage()
//        );
//        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
//    }
//
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


//
//    @ExceptionHandler({DuplicateRecordException.class})
//    public ResponseEntity<ApiResponseDto<?>> handleDuplicateRecordException(DuplicateRecordException exception) {
//        final ApiResponseDto<?> apiResponseDto = ApiResponseDto.badApiResponse(
//                exception.getMessage()
//        );
//        return new ResponseEntity<>(apiResponseDto, HttpStatus.CONFLICT);
//    }
}
