package hello.advanced.trace.startegy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 주입받는 방식
 */
@Slf4j
public class ContextV2 {
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        strategy.call();
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultInterval = endTime - startTime;
        log.info("result interval = {}", resultInterval);
    }
}
