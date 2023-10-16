package com.jackhedaya.segment;

public class MaxSegmentTree extends SegmentTree<Integer> {
  public MaxSegmentTree(Integer[] array) {
    super(array);
  }

  @Override
  Integer merge(Integer val1, Integer val2) {
    return Math.max(val1, val2);
  }
}
