package software.amazon.swage.metrics.record;

import org.junit.jupiter.api.Test;
import software.amazon.swage.collection.TypedMap;
import software.amazon.swage.metrics.MetricContext;

import static org.junit.jupiter.api.Assertions.assertSame;

public class NullRecorderTest {

    @Test
    public void contextReturnsAttributes() {
        TypedMap attributes = TypedMap.empty();
        NullRecorder recorder = new NullRecorder();
        MetricContext context = recorder.context(attributes);
        assertSame(attributes, context.attributes());
    }
}
