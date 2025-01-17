package state.admin.memberManage.application.common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import state.admin.memberManage.application.common.api.Api;
import state.common.exception.ErrorCode;

/**
 * 예상치 못한 예외가 일어나거나 캐치하지 못한 에러가 일어났을때 처리해줌
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception) { // 모든에러가 여기에 옴
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}
