package hello.advanced.threadlocal.utils;

public final class ThreadUtils {

    public static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
