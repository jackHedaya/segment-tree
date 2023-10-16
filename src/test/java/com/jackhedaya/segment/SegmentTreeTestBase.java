package com.jackhedaya.segment;

import java.util.Random;

public abstract class SegmentTreeTestBase {

  protected static Integer naiveMaxQuery(Integer[] arr, int start, int end) {
    int max = Integer.MIN_VALUE;

    for (int i = start; i <= end; i++)
      max = Math.max(max, arr[i]);

    return max;
  }

  protected static Integer naiveMinQuery(Integer[] arr, int start, int end) {
    int min = Integer.MAX_VALUE;

    for (int i = start; i <= end; i++)
      min = Math.min(min, arr[i]);

    return min;
  }

  protected static Integer naiveSumQuery(Integer[] arr, int start, int end) {
    int sum = 0;

    for (int i = start; i <= end; i++)
      sum += arr[i];

    return sum;
  }

  protected static void naiveUpdateRange(Integer[] arr, int start, int end, int val) {
    for (int i = start; i <= end; i++)
      arr[i] = val;
  }

  protected static Integer[] getRandomIntArray(int n) {
    Random rn = new Random();

    Integer[] arr = new Integer[n];

    for (int i = 0; i < n; i++)
      arr[i] = rn.nextInt(Config.RANDOM_UPPER_BOUND);

    return arr;
  }

  protected static Range getRandomRange(int upperBound) {
    Random rn = new Random();
    int start = 1;
    int end = 0;

    while (start > end) {
      start = rn.nextInt(upperBound);
      end = rn.nextInt(upperBound);
    }

    return new Range(start, end);
  }

  protected static class Range {
    private int start;
    private int end;

    private Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public int getStart() {
      return this.start;
    }

    public int getEnd() {
      return this.end;
    }
  }

}