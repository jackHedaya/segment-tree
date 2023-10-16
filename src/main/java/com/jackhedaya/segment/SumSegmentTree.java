package com.jackhedaya.segment;

public class SumSegmentTree extends SegmentTree<Integer> {
  public SumSegmentTree(Integer[] array) {
    super(array);
  }

  @Override
  Integer merge(Integer val1, Integer val2) {
    return val1 + val2;
  }
}
