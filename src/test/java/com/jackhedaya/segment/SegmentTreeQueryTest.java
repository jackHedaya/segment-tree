package com.jackhedaya.segment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SegmentTreeQueryTest extends SegmentTreeTestBase {
    @Test
    void maxSegmentTreeSimple() {
        MaxSegmentTree tree = new MaxSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

        int expected1 = 5;
        int got1 = tree.query(0, 4);

        assertEquals(expected1, got1);

        int expected2 = 3;
        int got2 = tree.query(1, 2);

        assertEquals(expected2, got2);
    }

    @Test
    void minSegmentTreeSimple() {
        MinSegmentTree tree = new MinSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

        int expected1 = 1;
        int got1 = tree.query(0, 4);

        assertEquals(expected1, got1);

        int expected2 = 2;
        int got2 = tree.query(1, 2);

        assertEquals(expected2, got2);
    }

    @Test
    void sumSegmentTreeSimple() {
        SumSegmentTree tree = new SumSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

        int expected1 = 15;
        int got1 = tree.query(0, 4);

        assertEquals(expected1, got1);

        int expected2 = 5;
        int got2 = tree.query(1, 2);

        assertEquals(expected2, got2);
    }

    @Test
    void maxSegmentTreeStress() {
        Integer[] arr = getRandomIntArray(1_000);

        MaxSegmentTree tree = new MaxSegmentTree(arr);

        for (int i = 0; i < Config.STRESS_QUERY_COUNT; i++) {
            Range range = getRandomRange(1_000);

            int start = range.getStart();
            int end = range.getEnd();

            Integer expected = naiveMaxQuery(arr, start, end);
            Integer got = tree.query(start, end);

            assertEquals(expected, got);
        }
    }

    @Test
    void minSegmentTreeStress() {
        Integer[] arr = getRandomIntArray(1_000);

        MinSegmentTree tree = new MinSegmentTree(arr);

        for (int i = 0; i < Config.STRESS_QUERY_COUNT; i++) {
            Range range = getRandomRange(1_000);

            int start = range.getStart();
            int end = range.getEnd();

            Integer expected = naiveMinQuery(arr, start, end);
            Integer got = tree.query(start, end);

            assertEquals(expected, got);
        }
    }

    @Test
    void sumSegmentTreeStress() {
        Integer[] arr = getRandomIntArray(1_000);

        SumSegmentTree tree = new SumSegmentTree(arr);

        for (int i = 0; i < Config.STRESS_QUERY_COUNT; i++) {
            Range range = getRandomRange(1_000);

            int start = range.getStart();
            int end = range.getEnd();

            Integer expected = naiveSumQuery(arr, start, end);
            Integer got = tree.query(start, end);

            assertEquals(expected, got);
        }
    }
}
