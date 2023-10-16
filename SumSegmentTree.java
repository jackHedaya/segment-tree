public class SumSegmentTree extends SegmentTree<Integer> {
  public SumSegmentTree(Integer[] array) {
    super(array);
  }

  public SumSegmentTree(int[] array) {
    super(intToIntegerArray(array));
  }

  @Override
  Integer merge(Integer val1, Integer val2) {
    return val1 + val2;
  }

  private static Integer[] intToIntegerArray(int[] array) {
    Integer[] out = new Integer[array.length];

    for (int i = 0; i < array.length; i++) {
      out[i] = array[i];
    }

    return out;
  }
}
