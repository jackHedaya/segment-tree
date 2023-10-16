package com.jackhedaya.segment;

public class MinSegmentTree extends SegmentTree<Integer> {
  public MinSegmentTree(Integer[] array) {
    super(array);
  }

  @Override
  Integer merge(Integer val1, Integer val2) {
    return Math.min(val1, val2);
  }
}
