package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        printBeginLog(message, traceId);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if (e == null) {
            printCompleteLog(status, resultTimeMs, traceId);
        } else {
            printExceptionLog(status, e, resultTimeMs, traceId);
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();

        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private static void printBeginLog(String message, TraceId traceId) {
        log.info("[{}] {}{}"
                , traceId.getId()
                , addSpace(START_PREFIX, traceId.getLevel())
                , message);
    }

    private static void printExceptionLog(TraceStatus status, Exception e, long resultTimeMs, TraceId traceId) {
        log.info("[{}] {}{} time={}ms, ex={}"
                , traceId.getId()
                , addSpace(EX_PREFIX, traceId.getLevel())
                , status.getMessage()
                , resultTimeMs
                , e.toString());
    }

    private static void printCompleteLog(TraceStatus status, long resultTimeMs, TraceId traceId) {
        log.info("[{}] {}{} time={}ms"
                , traceId.getId()
                , addSpace(COMPLETE_PREFIX, traceId.getLevel())
                , status.getMessage()
                , resultTimeMs);
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
