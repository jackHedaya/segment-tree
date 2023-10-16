public class MaxSegmentTree extends SegmentTree<Integer> {
  public MaxSegmentTree(Integer[] array) {
    super(array);
  }

  public MaxSegmentTree(int[] array) {
    super(intToIntegerArray(array));
  }

  @Override
  Integer merge(Integer val1, Integer val2) {
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
