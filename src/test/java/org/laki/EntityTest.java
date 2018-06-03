package org.laki;

import org.junit.Assert;
import org.junit.Test;

public class EntityTest {

    @Test
    public void testEquals() {
        Entity entity1 = new Entity(1, 2, 3, Character.MIN_VALUE);
        Entity entity2 = new Entity(1, 2, 3, Character.MIN_VALUE);

        Assert.assertTrue(entity1.equals(entity2));
    }
}
