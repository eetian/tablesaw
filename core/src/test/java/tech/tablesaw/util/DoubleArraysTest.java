/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.tablesaw.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.table.TableSliceGroup;

public class DoubleArraysTest {

    @Test
    public void testTo2dArray() throws Exception {
        Table table = Table.read().csv("../data/tornadoes_1950-2014.csv");
        TableSliceGroup tableSliceGroup = table.splitOn("Scale");
        int columnNuumber = table.columnIndex("Injuries");
        DoubleArrays.to2dArray(tableSliceGroup, columnNuumber);
    }

    @Test
    public void testToN() {
        double[] array = {0, 1, 2};
        assertTrue(Arrays.equals(array, DoubleArrays.toN(3)));
    }

    /** To improve the test coverage, add the following new test cases */
    /**
     * to2dArray(double[], double[]): double[][]
     */
    @Test
    public void to2dArrayTest_1() {
        double[] da1 = new double[]{1, 2, 3, 4, 5, 6};
        double[] da2 = new double[]{7, 8, 9, 10, 11, 12};
        double[][] expected = new double[][]{{1, 7}, {2, 8}, {3, 9}, {4, 10}, {5, 11}, {6, 12}};
        double[][] actual = DoubleArrays.to2dArray(da1, da2);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    /**
     * to2dArray(NumericColumn<?>, NumericColumn<?>): double[][]
     */
    @Test
    public void to2dArrayTest_2() {
        DoubleColumn dc1 = DoubleColumn.create("dc1", new double[]{1.1, 2.2, 3.3});
        DoubleColumn dc2 = DoubleColumn.create("dc2", new double[]{11.11, 22.22, 33.33});
        double[][] expected = new double[][]{{1.1, 11.11}, {2.2, 22.22}, {3.3, 33.33}};
        double[][] actual = DoubleArrays.to2dArray(dc1, dc2);
        assertTrue(Arrays.deepEquals(expected, actual));
    }
}
