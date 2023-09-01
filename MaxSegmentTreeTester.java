import java.util.Random;

public class MaxSegmentTreeTester {
  int[] arr;

  public MaxSegmentTreeTester(int n) {
    Random rn = new Random();
    int[] arr = new int[n];

    for (int i = 0; i < n; i++) {
      arr[i] = rn.nextInt(1_000_000);
    }

    this.arr = arr;
  }

  public MaxSegmentTreeTester(int[] arr) {
    this.arr = arr;
  }

  public void testMax(int[] arr, int numIterations) {
    MaxSegmentTree st = new MaxSegmentTree(arr);

    for (int i = 0; i < numIterations; i++) {
      Range range = Range.getRandom(arr.length);

      int start = range.start;
      int end = range.end;

      String message = "#query with range [" + start + ":" + end + "]";

      int got = st.query(start, end);
      int expected = naiveMax(arr, start, end);
      String error = message + " failed. Expected: " + expected + ". Got: " + got;
      assertt(error, message, got == expected);
    }
  }

  public void testMax(int numIterations) {
    this.testMax(arr, numIterations);
  }

  public void testUpdate(int numIterations, int numSubIterations) {
    MaxSegmentTree st = new MaxSegmentTree(arr);

    for (int i = 0; i < numIterations; i++) {
      Random rn = new Random();
      int[] arr = this.arr.clone();

      Range updateRange = Range.getRandom(arr.length);
      int updateStart = updateRange.start;
      int updateEnd = updateRange.end;
      int updateValue = rn.nextInt(1_000_000);

      System.out.println("Updated [" + updateStart + ":" + updateEnd + "] to " + updateValue);
      st.updateRange(updateStart, updateEnd, updateValue);

      naiveUpdate(arr, updateStart, updateEnd, updateValue);

      testMax(arr, numSubIterations);
    }
  }

  private static void assertt(String msg, String title, boolean expression) {
    if (!expression)
      throw new RuntimeException(msg);
    else
      System.out.println("Passed " + title);
  }

  private static int naiveMax(int[] arr, int left, int right) {
    int max = Integer.MIN_VALUE;

    for (int i = left; i <= right; i++)
      max = Math.max(max, arr[i]);

    return max;
  }

  private static int naiveUpdate(int[] arr, int left, int right, int value) {
    for (int i = left; i <= right; i++)
      arr[i] = value;

    return right - left;
  }

  private static class Range {
    public int start;
    public int end;

    private Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public static Range getRandom(int upperBound) {
      Random rn = new Random();
      int start = 1;
      int end = 0;

      while (start > end) {
        start = rn.nextInt(upperBound);
        end = rn.nextInt(upperBound);
      }

      return new Range(start, end);
    }
  }
}