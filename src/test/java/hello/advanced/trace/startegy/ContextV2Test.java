package hello.advanced.trace.startegy;

import hello.advanced.trace.startegy.code.strategy.ContextV2;
import hello.advanced.trace.startegy.code.strategy.StartegyLogic1;
import hello.advanced.trace.startegy.code.strategy.StartegyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV2Test {
    @Test
    @DisplayName("전략 패턴 적용")
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StartegyLogic1());
        context.execute(new StartegyLogic2());
    }
    @Test
    @DisplayName("전략 패턴 적용")
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직 1 실행"));
        context.execute(() -> log.info("비즈니스 로직 2 실행"));
    }
}
