import java.util.Deque;
import java.util.LinkedList;

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
  private Node root;

  public MaxSegmentTree(int[] n) {
    /*
     * - Works by grouping 2 nodes and adding the new node to a new level
     * - An odd numbered node is simply passed up as is
     */

    /*
     * For example:
     * {0, 1, 2, 3, 4}
     * 
     * {0:0}, {1:1}, {2:2}, {3:3}, {4:4}
     * {0:1}, {2:3}, {4}
     * {0:3}, {4}
     * {0:4}
     */

    Deque<Deque<Node>> queue = new LinkedList<>();

    // Initial queue of nodes with a range from [i:i]
    Deque<Node> start = new LinkedList<Node>();

    for (int i = 0; i < n.length; i++) {
      int num = n[i];
      start.add(new Node(num, i, i));
    }

    queue.add(start);

    // While the levels queue is not empty there are still more levels to process
    while (!queue.isEmpty()) {
      Deque<Node> level = queue.poll();
      Deque<Node> nextLevel = new LinkedList<>();

      if (level.size() == 1) {
        this.root = level.poll();
        break;
      }

      // Group nodes together. Keep track of their max within their range.
      while (level.size() >= 2) {
        Node n1 = level.poll();
        Node n2 = level.poll();

        int max = Math.max(n1.max, n2.max);

        Node newn = new Node(max, n1.lo, n2.hi);
        newn.left = n1;
        newn.right = n2;

        nextLevel.add(newn);
      }

      // if there's an odd numbered node, send to the next level
      if (!level.isEmpty())
        nextLevel.add(level.poll());

      queue.add(nextLevel);
    }
  }

  private int query(Node n, int left, int right) {
    // Sanity check, prevent out of bounds
    if (right < n.lo)
      return Integer.MIN_VALUE;
    if (left > n.hi)
      return Integer.MIN_VALUE;

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's max represents the max of all numbers
    // within the range
    if (n.lo >= left && n.hi <= right)
      return n.max;

    int max = Integer.MIN_VALUE;

    // If there's a partial overlap, we traverse downward
    if (n.left != null && left <= n.left.hi)
      max = Math.max(max, query(n.left, left, right));

    if (n.right != null && right >= n.right.lo)
      max = Math.max(max, query(n.right, left, right));

    return max;
  }

  private void updateRange(Node n, int left, int right, int value) {
    // Sanity check
    if (right < n.lo)
      return;
    if (left > n.hi)
      return;

    // If node's range is completely covered by the provided bounds, we don't need
    // to continue traversing because node's max represents the max of all numbers
    // within the range. We can simply update the max value of the node.
    if (n.lo >= left && n.hi <= right)
      n.max = Math.max(n.max, value);

    // If there's a partial overlap, we traverse downward
    if (n.left != null && left <= n.left.hi)
      updateRange(n.left, left, right, value);

    if (n.right != null && right >= n.right.lo)
      updateRange(n.right, left, right, value);
  }

  @Override
  public void update(int index, int value) {
    updateRange(index, index, value);
  }

  @Override
  public void updateRange(int left, int right, int value) {
    updateRange(root, left, right, value);
  }

  @Override
  public int query(int left, int right) {
    return query(root, left, right);
  }

  /**
   * A private representation of a Node in the tree. Example:
   * 
   * Node node = new Node(max: 100, lo: 0, hi: 25) // The largest value between 0
   * and
   * 25
   * (inclusively) is 100
   * 
   * node's left pointer will point to [0:n] and node's right pointer will point
   * to [n + 1:25]'
   */
  private static class Node {
    /**
     * The max value in the range
     */
    int max;

    /**
     * The lower bound of the node's range
     */
    int lo;
    /**
     * The (inclusive) upper bound of the node's range
     */
    int hi;

    /**
     * A pointer to the smaller half of this node's range
     */
    Node left;

    /**
     * A pointer to the larger half of this node's range
     */
    Node right;

    public Node(int max, int lo, int hi) {
      this.max = max;
      this.lo = lo;
      this.hi = hi;
    }

    @Override
    public String toString() {
      return "[" + this.lo + ":" + this.hi + "] max = " + this.max;
    }
  }
}