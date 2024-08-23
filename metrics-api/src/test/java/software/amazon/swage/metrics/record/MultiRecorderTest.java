package software.amazon.swage.metrics.record;

import java.time.Instant;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import software.amazon.swage.collection.TypedMap;
import software.amazon.swage.metrics.Metric;
import software.amazon.swage.metrics.MetricContext;
import software.amazon.swage.metrics.Unit;

@MockitoSettings(strictness = Strictness.WARN)
public class MultiRecorderTest {
    private static final Metric METRIC = Metric.define("Metric");

    @Spy private NullRecorder delegate1;
    @Spy private NullRecorder delegate2;

    private final Instant timestamp = Instant.now();
    private final TypedMap attributes = TypedMap.empty();

    private MultiRecorder recorder;

    @BeforeEach
    public void init() {
        recorder = new MultiRecorder(Arrays.asList(delegate1, delegate2));
    }

    @Test
    public void recordIsSentToAllDelegates() {
        MetricContext context = recorder.context(attributes);
        context.record(METRIC, 42L, Unit.NONE, timestamp);
        verify(delegate1).record(eq(METRIC), eq(42L), eq(Unit.NONE), eq(timestamp), argThat(t -> attributes == t.attributes()));
        verify(delegate2).record(eq(METRIC), eq(42L), eq(Unit.NONE), eq(timestamp), argThat(t -> attributes == t.attributes()));
    }

    @Test
    public void countIsSentToAllDelegates() {
        MetricContext context = recorder.context(attributes);
        context.count(METRIC, 42L);
        verify(delegate1).count(eq(METRIC), eq(42L), argThat(t -> attributes == t.attributes()));
        verify(delegate2).count(eq(METRIC), eq(42L), argThat(t -> attributes == t.attributes()));
    }

    @Test
    public void closeIsSentToAllDelegates() {
        MetricContext context = recorder.context(attributes);
        context.close();
        verify(delegate1).close(argThat(t -> attributes == t.attributes()));
        verify(delegate2).close(argThat(t -> attributes == t.attributes()));
    }
}
