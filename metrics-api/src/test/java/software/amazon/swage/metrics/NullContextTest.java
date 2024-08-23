package software.amazon.swage.metrics;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.amazon.swage.collection.ImmutableTypedMap;
import software.amazon.swage.collection.TypedMap;

public class NullContextTest {

    @Test
    public void defaultContextHasNoAttributes() {
        assertTrue(NullContext.empty().attributes().isEmpty());
    }

    @Test
    public void nullContextCanHaveAttributes() {
        TypedMap.Key<String> ID = TypedMap.key("ID", String.class);
        TypedMap attributes = ImmutableTypedMap.Builder.with(ID, "id").build();
        NullContext context = new NullContext(attributes);
        assertSame(attributes, context.attributes());
    }
}
