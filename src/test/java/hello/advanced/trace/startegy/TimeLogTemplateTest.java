package hello.advanced.trace.startegy;

import hello.advanced.trace.startegy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class TimeLogTemplateTest {

    @Test
    @DisplayName("템플릿 콜백 패턴")
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직1 출력"));
        template.execute(() -> log.info("비즈니스 로직2 출력"));
    }
}
