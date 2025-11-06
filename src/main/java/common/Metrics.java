package common;

public class Metrics {
    public long dfsVisits = 0;
    public long edgesSeen = 0;
    public long queuePushes = 0;
    public long queuePops = 0;
    public long timeNanos = 0;

    @Override
    public String toString() {
        return "Metrics{dfsVisits=" + dfsVisits
                + ", edgesSeen=" + edgesSeen
                + ", queuePushes=" + queuePushes
                + ", queuePops=" + queuePops
                + ", timeNanos=" + timeNanos + "}";
    }
}
