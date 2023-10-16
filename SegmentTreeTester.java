
import java.util.Arrays;
import java.util.Random;

@FunctionalInterface
interface GetRandomFunction<T> {
  public T apply();
}

public abstract class SegmentTreeTester<T> {
  T[] arr;

  private String name = null;

  public SegmentTreeTester(T[] arr) {
    this.arr = arr;
  }

  @SuppressWarnings("unchecked")
  public SegmentTreeTester(int n, T... dummy) {
    if (dummy.length > 0)
      throw new IllegalArgumentException(
          "Do not provide values for dummy argument.");

    this.arr = Arrays.copyOf(dummy, n);

    for (int i = 0; i < n; i++) {
      arr[i] = getRandom();
    }
  }

  public void testSegmentTree(T[] arr, int numIterations) {
    SegmentTree<T> st = new SegmentTree<T>(arr) {
      @Override
      T merge(T val1, T val2) {
        return SegmentTreeTester.this.merge(val1, val2);
      }
    };

    for (int i = 0; i < numIterations; i++) {
      Range range = Range.getRandom(arr.length);

      int start = range.start;
      int end = range.end;

      String message = "";
      String error = "";

      if (name != null) {
        message = name + ": ";
        error = name + ": ";
      }

      message += "#query with range [" + start + ":" + end + "]";

      T got = st.query(start, end);
      T expected = naiveQuery(arr, start, end);

      error += message + " failed. Expected: " + expected + ". Got: " + got;

      assertt(error, message, got.equals(expected));
    }
  }

  public void testSegmentTree(int numIterations) {
    this.testSegmentTree(arr, numIterations);
  }

  public void testUpdate(int numIterations, int numSubIterations) {
    SegmentTree<T> st = new SegmentTree<T>(arr) {
      @Override
      T merge(T val1, T val2) {
        return SegmentTreeTester.this.merge(val1, val2);
      }
    };

    for (int i = 0; i < numIterations; i++) {
      T[] arr = this.arr.clone();

      Range updateRange = Range.getRandom(arr.length);
      int updateStart = updateRange.start;
      int updateEnd = updateRange.end;
      T updateValue = getRandom();

      System.out.println("Updated [" + updateStart + ":" + updateEnd + "] to " + updateValue);
      st.updateRange(updateStart, updateEnd, updateValue);

      naiveUpdate(arr, updateStart, updateEnd, updateValue);

      testSegmentTree(arr, numSubIterations);
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  private static void assertt(String msg, String title, boolean expression) {
    if (!expression)
      throw new RuntimeException(msg);
    else
      System.out.println("Passed " + title);
  }

  private T naiveQuery(T[] arr, int left, int right) {
    T best = null;

    for (int i = left; i <= right; i++) {
      best = safeMerge(best, arr[i]);
    }

    return best;
  }

  private void naiveUpdate(T[] arr, int left, int right, T value) {
    for (int i = left; i <= right; i++)
      arr[i] = value;
  }

  private T safeMerge(T val1, T val2) {
    if (val1 == null && val2 == null)
      return null;
    if (val1 == null)
      return val2;
    if (val2 == null)
      return val1;

    return merge(val1, val2);
  }

  abstract T getRandom();

  abstract T merge(T val1, T val2);

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