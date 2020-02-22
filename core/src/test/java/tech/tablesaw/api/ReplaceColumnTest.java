package tech.tablesaw.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReplaceColumnTest {
    // Note: index out of bound and wrong input date type can be handled by java, so no need to write it down.

    @Test
    void newColIsNull() {
        DoubleColumn dc0 = DoubleColumn.create("c0", new double[]{1, 2, 3, 4, 5});
        DoubleColumn dc1 = DoubleColumn.create("c1", new double[]{6, 7, 8, 9, 10});
        DoubleColumn replacement = DoubleColumn.create("c1", new double[]{});
        Table t = Table.create("populated", dc0, dc1);
        boolean flag = false;
        try {
            t.replaceColumn(1, replacement);
        } catch (Exception e) {
            flag = true;
        } finally {
            assertTrue(flag);
        }
    }

    @Test
    void wrongColSize() {
        DoubleColumn dc0 = DoubleColumn.create("c0", new double[]{1, 2, 3, 4, 5});
        DoubleColumn dc1 = DoubleColumn.create("c1", new double[]{6, 7, 8, 9, 10});
        DoubleColumn replacement = DoubleColumn.create("c1", new double[]{1, 2, 3, 4});
        Table t = Table.create("populated", dc0, dc1);
        boolean flag = false;
        try {
            t.replaceColumn(1, replacement);
        } catch (Exception e) {
            flag = true;
        } finally {
            assertTrue(flag);
        }
    }

    @Test
    void duplicateColName() {
        DoubleColumn dc0 = DoubleColumn.create("c0", new double[]{1, 2, 3, 4, 5});
        DoubleColumn dc1 = DoubleColumn.create("c1", new double[]{6, 7, 8, 9, 10});
        DoubleColumn replacement = DoubleColumn.create("c0", new double[]{10, 20, 30, 40, 50});
        Table t = Table.create("populated", dc0, dc1);
        boolean flag = false;
        try {
            t.replaceColumn(1, replacement);
        } catch (Exception e) {
            flag = true;
        } finally {
            assertTrue(flag);
        }
    }

    @Test
    void testReplaceColumn() {
        DoubleColumn dc0 = DoubleColumn.create("c0", new double[]{1, 2, 3, 4, 5});
        DoubleColumn dc1 = DoubleColumn.create("c1", new double[]{6, 7, 8, 9, 10});
        DoubleColumn replacement = DoubleColumn.create("c1", new double[]{10, 20, 30, 40, 50});
        Table t = Table.create("populated", dc0, dc1);

        t.replaceColumn(1, replacement);
        assertSame(t.column(1), replacement);
        assertEquals(t.columnIndex(replacement), 1);
    }

}
