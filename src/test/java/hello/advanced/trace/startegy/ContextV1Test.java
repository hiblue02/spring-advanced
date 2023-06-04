package hello.advanced.trace.startegy;

import hello.advanced.trace.startegy.code.strategy.ContextV1;
import hello.advanced.trace.startegy.code.strategy.StartegyLogic1;
import hello.advanced.trace.startegy.code.strategy.StartegyLogic2;
import hello.advanced.trace.startegy.code.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV1Test {

    @Test
    void strategy1() {
        StartegyLogic1 startegyLogic1 = new StartegyLogic1();
        ContextV1 context1 = new ContextV1(startegyLogic1);

        context1.execute();

        StartegyLogic2 startegyLogic2 = new StartegyLogic2();
        ContextV1 context2 = new ContextV1(startegyLogic1);

        context2.execute();
    }

    @Test
    void strategy2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1");
            }
        };

        ContextV1 context1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1 = {}", strategyLogic1.getClass());
        context1.execute();
    }

    @Test
    void strategy3() {

        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1");
            }
        });
        context1.execute();
    }

    @Test
    void strategy4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직 1"));
        context1.execute();
    }
}
