package common;

public class Stopwatch implements AutoCloseable {
    private final Metrics metrics;
    private final long start;

    public Stopwatch(Metrics m) {
        this.metrics = m;
        this.start = System.nanoTime();
    }

    @Override
    public void close() {
        metrics.timeNanos += System.nanoTime() - start;
    }
}

