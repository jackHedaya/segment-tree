interface SegmentTree {
  /**
   * Query the queryable property between left and right
   */
  public int query(int left, int right);

  /**
   * Update the queryable property at index to value
   */
  public void update(int index, int value);

  /**
   * Update the queryable property between start and end (inclusively) to value
   */
  public void updateRange(int start, int end, int value);
}

/**
 * An implementation of a Max Segment Tree
 */
public class MaxSegmentTree implements SegmentTree {
  private Integer[] maxes;
  private Integer[] segmentStarts;
  private Integer[] segmentEnds;

  public MaxSegmentTree(int[] array) {

    // Get the bottom level of the binary tree
    // Minimize d s.t. 2^d >=len(array)
    int bottomLevelSize = findNextPowerOfTwo(array.length);

    // The total number of nodes in the perfect binary tree
    int numNodes = 2 * bottomLevelSize - 1;

    // Initialize tree arrays
    maxes = new Integer[numNodes];
    segmentStarts = new Integer[numNodes];
    segmentEnds = new Integer[numNodes];

    // Copies original array values to the end of the array, implicitly casting to
    // Integer
    for (int i = 0; i < array.length; i++) {
      maxes[bottomLevelSize + i - 1] = array[i];
    }

    for (int i = bottomLevelSize - 1; i < numNodes; i++) {
      segmentStarts[i] = i - bottomLevelSize + 1;
      segmentEnds[i] = i - bottomLevelSize + 1;
    }

    for (int i = bottomLevelSize - 2; i >= 0; i--) {
      int leftIdx = getLeftChildIdx(i);
      int rightIdx = getRightChildIdx(i);

      maxes[i] = safeMax(maxes[leftIdx], maxes[rightIdx]);

      segmentStarts[i] = segmentStarts[leftIdx];
      segmentEnds[i] = segmentEnds[rightIdx];
    }
  }

  private int query(int nodeIdx, int from, int to) {

    int leftBound = segmentStarts[nodeIdx];
    int rightBound = segmentEnds[nodeIdx];

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's max represents the max of all numbers
    // within the range
    if (leftBound >= from && rightBound <= to)
      return maxes[nodeIdx];

    int leftChildIdx = getLeftChildIdx(nodeIdx);
    int rightChildIdx = getRightChildIdx(nodeIdx);

    int max = Integer.MIN_VALUE;

    // If there's a partial overlap, we traverse downward
    if (isValidIdx(leftChildIdx) && leftBound <= segmentEnds[leftChildIdx])
      max = Math.max(max, query(leftChildIdx, from, to));

    if (isValidIdx(rightChildIdx) && rightBound >= segmentStarts[rightChildIdx])
      max = Math.max(max, query(rightChildIdx, from, to));

    return max;
  }

  private void updateRange(int idx, int left, int right, int value) {
    int to = segmentEnds[idx];
    int from = segmentStarts[idx];

    int leftIdx = getLeftChildIdx(idx);
    int rightIdx = getRightChildIdx(idx);

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's max represents the max of all numbers
    // within the range. We can simply update the max value of the node.
    if (from >= left && to <= right)
      maxes[idx] = Math.max(maxes[idx], value);

    // If there's a partial overlap, we traverse downward
    if (isValidIdx(leftIdx) && from <= segmentEnds[leftIdx])
      updateRange(leftIdx, left, right, value);

    if (isValidIdx(rightIdx) && to >= segmentStarts[rightIdx])
      updateRange(rightIdx, left, right, value);

  }

  @Override
  public void update(int index, int value) {
    updateRange(index, index, value);
  }

  @Override
  public void updateRange(int left, int right, int value) {
    updateRange(0, left, right, value);
  }

  @Override
  public int query(int left, int right) {
    return query(0, left, right);
  }

  private boolean isValidIdx(int idx) {
    return idx >= 0 && idx < maxes.length;
  }

  private int getLeftChildIdx(int idx) {
    return 2 * idx + 1;
  }

  private int getRightChildIdx(int idx) {
    return 2 * idx + 2;
  }

  private int findNextPowerOfTwo(int from) {
    int count = 0;

    while (from != 0) {
      from >>>= 1;
      count++;
    }

    return (int) Math.pow(2, count);
  }

  private Integer safeMax(Integer num1, Integer num2) {
    if (num1 == null && num2 == null)
      return null;
    if (num1 == null)
      return num2;
    if (num2 == null)
      return num1;

    return Math.max(num1, num2);
  }
}