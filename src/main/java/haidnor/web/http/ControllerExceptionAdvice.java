package haidnor.web.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HandlerInterceptor.class);

    /**
     * 获取异常将要的堆栈信息
     */
    private static String getStackInfo(Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length < 1) {
            return exception.getMessage();
        }
        StackTraceElement traceElement = exception.getStackTrace()[0];
        return String.format("Exception: %s Class: %s LineNumber: %s", exception, traceElement.getClassName(), traceElement.getLineNumber());
    }

    /**
     * 程序异常
     * 该异常表示程序运行时异常. 开发者不应该在代码中编写抛出此异常, 例如 NullPointerException, IndexOutOfBoundsException
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> exception(Exception exception) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            try {
                log.error("Global Exception Message: URI:{} Method:{} Parameter:{}", request.getRequestURI(), request.getMethod(), new ObjectMapper().writeValueAsString(request.getParameterMap()), exception);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("ControllerExceptionAdvice jsonProcessingException Message:", jsonProcessingException);
                log.error("Global Exception Message:", exception);
            }
        } else {
            log.error("Global Exception Message:", exception);
        }
        return Result.fail(exception.getMessage(), getStackInfo(exception));
    }

    /**
     * 参数校验异常
     * 该异常表示对外接口的参数校验异常. 该异常多用于 controller 层. 例如服务器没有接受到指定参数, 或接收到的参数不符合约定
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Object> constraintViolationException(ConstraintViolationException exception) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            try {
                log.warn("Global ConstraintViolationException Message: URI:{} Method:{} Parameter:{}", request.getRequestURI(), request.getMethod(), new ObjectMapper().writeValueAsString(request.getParameterMap()), exception);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("ControllerExceptionAdvice jsonProcessingException Message:", jsonProcessingException);
                log.warn("Global ConstraintViolationException Message:", exception);
            }
        } else {
            log.warn("Global ConstraintViolationException Message:", exception);
        }
        return Result.fail(exception.getMessage(), getStackInfo(exception));
    }

}
