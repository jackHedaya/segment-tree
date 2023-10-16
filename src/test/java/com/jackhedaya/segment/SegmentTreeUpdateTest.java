package com.jackhedaya.segment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SegmentTreeUpdateTest extends SegmentTreeTestBase {
  @Test
  void maxSegmentTreeSimple() {
    MaxSegmentTree tree = new MaxSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

    int expected1 = 4;
    int got1 = tree.query(0, 3);

    assertEquals(expected1, got1);

    tree.updateRange(0, 3, 2);

    int expected2 = 2;
    int got2 = tree.query(0, 3);

    assertEquals(expected2, got2);
  }

  @Test
  void minSegmentTreeSimple() {
    MinSegmentTree tree = new MinSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

    int expected1 = 1;
    int got1 = tree.query(0, 3);

    assertEquals(expected1, got1);

    tree.updateRange(0, 3, 2);

    int expected2 = 2;
    int got2 = tree.query(0, 3);

    assertEquals(expected2, got2);
  }

  @Test
  void sumSegmentTreeSimple() {
    SumSegmentTree tree = new SumSegmentTree(new Integer[] { 1, 2, 3, 4, 5 });

    int expected1 = 10;
    int got1 = tree.query(0, 3);

    assertEquals(expected1, got1);

    tree.updateRange(0, 3, 2);

    int expected2 = 8;
    int got2 = tree.query(0, 3);

    assertEquals(expected2, got2);
  }

  @Test
  void maxSegmentTreeStress() {
    Integer[] arr = getRandomIntArray(1_000);

    MaxSegmentTree tree = new MaxSegmentTree(arr);

    for (int i = 0; i < Config.STRESS_UPDATE_COUNT; i++) {
      Range updateRange = getRandomRange(1_000);

      tree.updateRange(updateRange.getStart(), updateRange.getEnd(), 1);
      naiveUpdateRange(arr, updateRange.getStart(), updateRange.getEnd(), 1);

      for (int j = 0; j < Config.STRESS_QUERY_COUNT; j++) {
        Range range = getRandomRange(1_000);

        int start = range.getStart();
        int end = range.getEnd();

        Integer expected = naiveMaxQuery(arr, start, end);
        Integer got = tree.query(start, end);

        assertEquals(expected, got);
      }
    }
  }

  @Test
  void minSegmentTreeStress() {
    Integer[] arr = getRandomIntArray(1_000);

    MinSegmentTree tree = new MinSegmentTree(arr);

    for (int i = 0; i < Config.STRESS_UPDATE_COUNT; i++) {
      Range updateRange = getRandomRange(1_000);

      tree.updateRange(updateRange.getStart(), updateRange.getEnd(), 1);
      naiveUpdateRange(arr, updateRange.getStart(), updateRange.getEnd(), 1);

      for (int j = 0; j < Config.STRESS_QUERY_COUNT; j++) {
        Range range = getRandomRange(1_000);

        int start = range.getStart();
        int end = range.getEnd();

        Integer expected = naiveMinQuery(arr, start, end);
        Integer got = tree.query(start, end);

        assertEquals(expected, got);
      }
    }
  }

  @Test
  void sumSegmentTreeStress() {
    Integer[] arr = getRandomIntArray(1_000);

    SumSegmentTree tree = new SumSegmentTree(arr);

    for (int i = 0; i < Config.STRESS_UPDATE_COUNT; i++) {
      Range updateRange = getRandomRange(1_000);

      tree.updateRange(updateRange.getStart(), updateRange.getEnd(), 1);
      naiveUpdateRange(arr, updateRange.getStart(), updateRange.getEnd(), 1);

      for (int j = 0; j < Config.STRESS_QUERY_COUNT; j++) {
        Range range = getRandomRange(1_000);

        int start = range.getStart();
        int end = range.getEnd();

        Integer expected = naiveSumQuery(arr, start, end);
        Integer got = tree.query(start, end);

        assertEquals(expected, got);
      }
    }
  }
}
