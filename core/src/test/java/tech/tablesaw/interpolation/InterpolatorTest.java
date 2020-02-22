package tech.tablesaw.interpolation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.columns.numbers.DoubleColumnType;

public class InterpolatorTest {

    private static final double missing = DoubleColumnType.missingValueIndicator();
    public static final String MISSING_STR = "missing";

    // Helper function: convert string to double array
    public static double[] strToDoubleArray(String input) {
        String[] chunks = input.replaceAll("\\[", "").replaceAll(
                "\\]", "").replaceAll("\\s", "").split(",");
        double[] results = new double[chunks.length];
        for (int i = 0; i < chunks.length; ++i) {
            try {
                if (chunks[i].compareTo(MISSING_STR) == 0) {
                    results[i] = missing;
                } else {
                    results[i] = Double.parseDouble(chunks[i]);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            ;
        }
        return results;
    }

    /** New test cases with partitioning and parameterized test*/
    @ParameterizedTest(name = "{0}: {1}, {2}")
    @CsvSource({
            // array size = 0
            "'len-0-frontfill', '[]', '[]'",
            "'len-1-backfill', '[]', '[]'",

            // array size = 1, with 0 missing value
            "'len-1-frontfill', '[0.181]', '[0.181]'",
            "'len-1-backfill', '[0.181]', '[0.181]'",
            // array size = 1, with 1 missing value
            "'len-1-frontfill', '[missing]', '[missing]'",
            "'len-1-backfill', '[missing]', '[missing]'",

            // array size = n ( n > 1), with 0 missing value
            "'len-n-frontfill', '[ 0.181, 0.186, 0.181]', '[0.181, 0.186, 0.181]'",
            "'len-n-backfill', '[ 0.181, 0.186, 0.181]', '[0.181, 0.186, 0.181]'",
            // array size = n ( n > 1), with 1 missing value placed at the beginning, middle, and end of the array
            "'len-n-frontfill', '[ missing, 0.181, 0.123, 0.186, 0.181]', '[missing, 0.181, 0.123, 0.186, 0.181]'",
            "'len-n-frontfill', '[ 0.181, 0.123, missing, 0.186, 0.181]', '[0.181, 0.123, 0.123, 0.186, 0.181]'",
            "'len-n-frontfill', '[ 0.181, 0.123, 0.186, 0.181, missing]', '[0.181, 0.123, 0.186, 0.181, 0.181]'",

            "'len-n-backfill', '[ missing, 0.181, 0.123, 0.186, 0.181]', '[0.181, 0.181, 0.123, 0.186, 0.181]'",
            "'len-n-backfill', '[ 0.181, 0.123, missing, 0.186, 0.181]', '[0.181, 0.123, 0.186, 0.186, 0.181]'",
            "'len-n-backfill', '[ 0.181, 0.123, 0.186, 0.181, missing]', '[0.181, 0.123, 0.186, 0.181, missing]'",

            // array size = n ( n > 1), with 2 ~ n - 1 missing value in different distribution
            "'len-n-frontfill', '[ missing, missing, 0.181, 0.123, 0.186, 0.181]', '[missing, missing, 0.181, 0.123, 0.186, 0.181]'",
            "'len-n-frontfill', '[ 0.181, 0.123, missing, missing, 0.186, 0.181]', '[0.181, 0.123, 0.123, 0.123, 0.186, 0.181]'",
            "'len-n-frontfill', '[ 0.181, 0.123, 0.186, 0.181, missing, missing]', '[0.181, 0.123, 0.186, 0.181, 0.181, 0.181]'",

            "'len-n-backfill', '[ missing, missing, 0.181, 0.123, 0.186, 0.181]', '[0.181, 0.181, 0.181, 0.123, 0.186, 0.181]'",
            "'len-n-backfill', '[ 0.181, 0.123, missing, missing, 0.186, 0.181]', '[0.181, 0.123, 0.186, 0.186, 0.186, 0.181]'",
            "'len-n-backfill', '[ 0.181, 0.123, 0.186, 0.181, missing, missing]', '[0.181, 0.123, 0.186, 0.181, missing, missing]'",

            "'len-n-frontfill', '[missing, 0.181, missing, 0.123, missing]', '[missing, 0.181, 0.181, 0.123, 0.123]'",
            "'len-n-frontfill', '[0.181, missing, 0.123, missing, 0.888, missing]', '[0.181, 0.181, 0.123, 0.123, 0.888, 0.888]'",
            "'len-n-frontfill', '[missing, missing, 0.181, 0.186, missing, missing, 0.181]', '[missing, missing, 0.181, 0.186, 0.186, 0.186, 0.181]'",

            "'len-n-backfill', '[missing, missing, 0.181, 0.186, missing, 0.181, missing]', '[0.181, 0.181, 0.181, 0.186, 0.181, 0.181, missing]'",
            "'len-n-backfill', '[0.181, missing, 0.123, missing, 0.888, missing]', '[0.181, 0.123, 0.123, 0.888, 0.888, missing]'",
            "'len-n-backfill', '[missing, 0.181, missing, 0.123, missing]', '[0.181, 0.181, 0.123, 0.123, missing]'",

            // array size = n (n > 1), with n missing value
            "'len-n-frontfill', '[missing, missing, missing, missing, missing, missing]', '[missing, missing, missing, missing, missing, missing]'",
            "'len-n-backfill', '[missing, missing, missing, missing, missing, missing]', '[missing, missing, missing, missing, missing, missing]'",

    })
    public void testParametrized(String partition, String data, String expected) {
        double[] inputData = InterpolatorTest.strToDoubleArray(data);
        double[] expectedOutput = InterpolatorTest.strToDoubleArray(expected);
        DoubleColumn col = DoubleColumn.create("testCol", inputData);
        if (partition.contains("frontfill")) {
            col = (DoubleColumn) col.interpolate().frontfill();
        } else if (partition.contains("backfill")) {
            col = (DoubleColumn) col.interpolate().backfill();
        }
        System.out.println(col.print());
        assertArrayEquals(expectedOutput, col.asDoubleArray());
    }


    /** Below are original test cases */
    @Test
    public void testFrontfill() {
        DoubleColumn col =
                (DoubleColumn)
                        DoubleColumn.create(
                                "testCol",
                                new double[]{missing, missing, 0.181, 0.186, missing, missing, 0.181})
                                .interpolate()
                                .frontfill();
        assertArrayEquals(
                new double[]{missing, missing, 0.181, 0.186, 0.186, 0.186, 0.181}, col.asDoubleArray());
    }

    @Test
    public void testBackfill() {
        DoubleColumn col =
                (DoubleColumn)
                        DoubleColumn.create(
                                "testCol",
                                new double[]{missing, missing, 0.181, 0.186, missing, 0.181, missing})
                                .interpolate()
                                .backfill();
        assertArrayEquals(
                new double[]{0.181, 0.181, 0.181, 0.186, 0.181, 0.181, missing}, col.asDoubleArray());
    }
}
