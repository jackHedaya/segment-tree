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

  /**
   * Constructs a segment tree to enable efficient range-maximum queries
   * on the input array.
   */
  public MaxSegmentTree(int[] array) {

    // Get the bottom level of the binary tree
    // Minimize d s.t. 2^d >=len(array)
    int bottomLevelSize = findNextPowerOfTwo(array.length);

    // The total number of nodes in the perfect binary tree
    int numTreeNodes = 2 * bottomLevelSize - 1;

    // Initialize tree arrays
    maxes = new Integer[numTreeNodes];
    segmentStarts = new Integer[numTreeNodes];
    segmentEnds = new Integer[numTreeNodes];

    // Populate the bottom-most level of the segment tree with the original array
    // values. Implicitly casts to Integer and leaves any extra nodes as null
    for (int i = 0; i < array.length; i++) {
      maxes[bottomLevelSize + i - 1] = array[i];
    }

    // Set the segment boundaries for the bottom-most level. Unset nodes are still
    // assigned boundaries so that the tree is still a perfect binary tree
    for (int i = bottomLevelSize - 1; i < numTreeNodes; i++) {
      segmentStarts[i] = i - bottomLevelSize + 1;
      segmentEnds[i] = i - bottomLevelSize + 1;
    }

    // Build the rest of the tree by calculating the maxes of each node's children
    // and setting the segment boundaries of each node
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

  private void updateRange(int idx, int from, int to, int value) {
    int leftBound = segmentStarts[idx];
    int rightBound = segmentEnds[idx];

    int leftIdx = getLeftChildIdx(idx);
    int rightIdx = getRightChildIdx(idx);

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's max represents the max of all numbers
    // within the range. We can simply update the max value of the node.
    if (leftBound >= from && rightBound <= to)
      maxes[idx] = Math.max(maxes[idx], value);

    // If there's a partial overlap, we traverse downward
    if (isValidIdx(leftIdx) && leftBound <= segmentEnds[leftIdx])
      updateRange(leftIdx, from, to, value);

    if (isValidIdx(rightIdx) && rightBound >= segmentStarts[rightIdx])
      updateRange(rightIdx, from, to, value);
  }

  /**
   * Indicates whether the provided index is valid for the maxes array
   */
  private boolean isValidIdx(int idx) {
    return idx >= 0 && idx < maxes.length;
  }

  /**
   * Returns the index of the left child of the node at idx
   */
  private int getLeftChildIdx(int idx) {
    return 2 * idx + 1;
  }

  /**
   * Returns the index of the right child of the node at idx
   */
  private int getRightChildIdx(int idx) {
    return 2 * idx + 2;
  }

  /**
   * Finds the smallest power of two that is greater than or equal to `from`
   */
  private int findNextPowerOfTwo(int from) {
    int count = 0;

    while (from != 0) {
      from >>>= 1;
      count++;
    }

    return (int) Math.pow(2, count);
  }

  /**
   * Safely returns the max of two Integers, even if one or both are null
   */
  private Integer safeMax(Integer num1, Integer num2) {
    if (num1 == null && num2 == null)
      return null;
    if (num1 == null)
      return num2;
    if (num2 == null)
      return num1;

    return Math.max(num1, num2);
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
}