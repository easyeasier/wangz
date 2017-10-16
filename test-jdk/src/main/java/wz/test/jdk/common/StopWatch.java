package wz.test.jdk.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * performance tool
 * <p>
 * Created by liyiguang on 16/5/26.
 */
public class StopWatch {

    private static Logger LOG = LoggerFactory.getLogger(StopWatch.class);

    private long startTime;

    private long lapStartTime;

    private String tagName;

    private List<String> steps = new ArrayList<>();

    public static StopWatch create(String tagName) {

        return new StopWatch(tagName);
    }

    private StopWatch(String tagName) {
        this.tagName = tagName;

        long start = System.nanoTime();

        this.startTime = start;
        this.lapStartTime = start;
    }

    public void lap(String stepName) {

        long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - lapStartTime);
        int index = steps.size() + 1;
        String step = String.format("T%d(%s)/%d", index, stepName, elapsedTime);
        steps.add(step);

        //reset
        this.lapStartTime = System.nanoTime();
    }

    public void log() {
        StringBuilder stringBuilder = createLog();
        LOG.warn(stringBuilder.toString());
    }

    public void logSlow(long slow) {

        long totalElapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        if (totalElapsedTime > slow) {
            StringBuilder stringBuilder = createLog();
            LOG.warn(stringBuilder.toString());
        }
    }

    private StringBuilder createLog() {
        long totalElapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        StringBuilder stringBuilder = new StringBuilder(tagName);
        stringBuilder.append(' ');
        stringBuilder.append(" Total/");
        stringBuilder.append(totalElapsedTime);
        stringBuilder.append(' ');

        for (String step : steps) {
            stringBuilder.append(step);
            stringBuilder.append(' ');
        }
        return stringBuilder;
    }

}
