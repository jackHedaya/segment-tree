import java.util.Random;

public class MaxSegmentTreeTester extends SegmentTreeTester<Integer> {
  public MaxSegmentTreeTester(int[] arr) {
    super(intToIntegerArray(arr));
  }

  public MaxSegmentTreeTester(int n) {
    super(n);
  }

  public Integer getRandom() {
    Random rd = new Random();

    return rd.nextInt(1_000_000);
  }

  public Integer merge(Integer val1, Integer val2) {
    return Math.max(val1, val2);
  }

  private static Integer[] intToIntegerArray(int[] array) {
    Integer[] out = new Integer[array.length];

    for (int i = 0; i < array.length; i++) {
      out[i] = array[i];
    }

    return out;
  }
}
