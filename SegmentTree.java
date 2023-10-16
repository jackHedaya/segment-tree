import java.util.Arrays;

interface ISegmentTree<T> {
  /**
   * Query the property between left and right
   */
  public T query(int left, int right);

  /**
   * Update the property at index to value
   */
  public void update(int index, T value);

  /**
   * Update the property between start and end (inclusively) to value
   */
  public void updateRange(int start, int end, T value);
}

/**
 * An implementation of a Generic Segment Tree
 */
public abstract class SegmentTree<T> implements ISegmentTree<T> {
  /**
   * This generic array should **never** be accessed outside of the class
   * as generic arrays are hacky in Java and will throw if used incorrectly
   */
  private T[] segmentVals;
  private int[] segmentStarts;
  private int[] segmentEnds;

  /**
   * Constructs a segment tree to enable efficient range queries on a generic
   * input array.
   */
  public SegmentTree(T[] array) {

    // Get the bottom level of the binary tree
    // Minimize d s.t. 2^d >=len(array)
    int bottomLevelSize = findNextPowerOfTwo(array.length);

    // The total number of nodes in the perfect binary tree
    int numTreeNodes = 2 * bottomLevelSize - 1;

    // Initialize tree arrays in a hacky way
    segmentVals = createGenericArray(numTreeNodes);
    segmentStarts = new int[numTreeNodes];
    segmentEnds = new int[numTreeNodes];

    // Populate the bottom-most level of the segment tree with the original array
    // values. Implicitly casts to Integer and leaves any extra nodes as null
    for (int i = 0; i < array.length; i++) {
      segmentVals[bottomLevelSize + i - 1] = array[i];
    }

    // Set the segment boundaries for the bottom-most level. Unset nodes are still
    // assigned boundaries so that the tree is still a perfect binary tree
    for (int i = bottomLevelSize - 1; i < numTreeNodes; i++) {
      segmentStarts[i] = i - bottomLevelSize + 1;
      segmentEnds[i] = i - bottomLevelSize + 1;
    }

    // Build the rest of the tree by calculating the comparison of each node's
    // children and setting the segment boundaries of each node
    for (int i = bottomLevelSize - 2; i >= 0; i--) {
      int leftIdx = getLeftChildIdx(i);
      int rightIdx = getRightChildIdx(i);

      segmentVals[i] = safeMerge(segmentVals[leftIdx], segmentVals[rightIdx]);

      segmentStarts[i] = segmentStarts[leftIdx];
      segmentEnds[i] = segmentEnds[rightIdx];
    }
  }

  private T query(int nodeIdx, int from, int to) {
    int leftBound = segmentStarts[nodeIdx];
    int rightBound = segmentEnds[nodeIdx];

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's comparison value represents the "best"
    // of all numbers within the range
    if (leftBound >= from && rightBound <= to)
      return segmentVals[nodeIdx];

    int leftChildIdx = getLeftChildIdx(nodeIdx);
    int rightChildIdx = getRightChildIdx(nodeIdx);

    T best = null;

    // If there's a partial overlap, we traverse downward
    if (isValidIdx(leftChildIdx) && leftBound <= segmentEnds[leftChildIdx])
      best = safeMerge(best, query(leftChildIdx, from, to));

    if (isValidIdx(rightChildIdx) && rightBound >= segmentStarts[rightChildIdx])
      best = safeMerge(best, query(rightChildIdx, from, to));

    return best;
  }

  private void updateRange(int idx, int from, int to, T value) {
    int leftBound = segmentStarts[idx];
    int rightBound = segmentEnds[idx];

    int leftIdx = getLeftChildIdx(idx);
    int rightIdx = getRightChildIdx(idx);

    // If node's range is completely covered by the provided bounds, update the
    // comparison value
    if (leftBound >= from && rightBound <= to)
      segmentVals[idx] = merge(segmentVals[idx], value);

    // If there's a partial overlap, we traverse downward
    if (isValidIdx(leftIdx) && leftBound <= segmentEnds[leftIdx])
      updateRange(leftIdx, from, to, value);

    if (isValidIdx(rightIdx) && rightBound >= segmentStarts[rightIdx])
      updateRange(rightIdx, from, to, value);
  }

  /**
   * Safely returns the merged value even if one or both values are null
   */
  private T safeMerge(T val1, T val2) {
    if (val1 == null && val2 == null)
      return null;
    if (val1 == null)
      return val2;
    if (val2 == null)
      return val1;

    return merge(val1, val2);
  }

  /**
   * Indicates whether the provided index is valid for the tree arrays
   */
  private boolean isValidIdx(int idx) {
    return idx >= 0 && idx < segmentVals.length;
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
    if (from <= 0)
      return 1;

    // If `from` is already a power of two, return it
    if ((from & (from - 1)) == 0)
      return from;

    // Shift to the left by 1 to flip the next power of 2
    int num = from << 1;

    // Using a variation of Brian Kernighan’s Bit Counting Algorithm to clear
    // the rightmost flipped bit until right before it becomes 0
    while ((num & (num - 1)) != 0)
      num &= num - 1;

    return num;
  }

  /**
   * Creates a generic array in a hacky way.
   * **Do not** use outside of this class
   * 
   * This is SafeVarargs because dummy will never be populated
   */
  @SafeVarargs
  private T[] createGenericArray(int size, T... dummy) {
    if (dummy.length > 0)
      throw new IllegalArgumentException(
          "do not provide values for dummy argument");

    return Arrays.copyOf(dummy, size);
  }

  private void checkRange(int left, int right) {
    if (!isValidIdx(left))
      throw new IndexOutOfBoundsException(left);

    if (!isValidIdx(right))
      throw new IndexOutOfBoundsException(left);

    if (left > right)
      throw new IllegalArgumentException("left may not be greater than right");
  }

  @Override
  public void update(int index, T value) {
    if (!isValidIdx(index))
      throw new IndexOutOfBoundsException(index);

    updateRange(index, index, value);
  }

  @Override
  public void updateRange(int left, int right, T value) {
    checkRange(left, right);

    updateRange(0, left, right, value);
  }

  @Override
  public T query(int left, int right) {
    checkRange(left, right);

    return query(0, left, right);
  }

  /**
   * The merger function to decide the value of combining two nodes
   */
  abstract T merge(T val1, T val2);
}