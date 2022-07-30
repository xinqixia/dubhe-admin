package xin.qixia.dubhe.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result handler(Exception e) {
        e.printStackTrace();
        return Result.fail(401, e.getMessage(), null);
    }
}
