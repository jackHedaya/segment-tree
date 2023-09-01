import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

interface SegmentTree {
  public int query(int left, int right);

  public void update(int index, int value);
}

public class MaxSegmentTree implements SegmentTree {

  private Node root;

  static class Node {
    int max;
    int lo;
    int hi;

    Node left;
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

  public MaxSegmentTree(int[] n) {
    Deque<Deque<Node>> queue = new LinkedList<>();

    Deque<Node> start = new LinkedList<Node>();

    for (int i = 0; i < n.length; i++) {
      int num = n[i];
      start.add(new Node(num, i, i));
    }

    queue.add(start);

    while (!queue.isEmpty()) {
      Deque<Node> level = queue.poll();
      Deque<Node> nextLevel = new LinkedList<>();

      if (level.size() == 1) {
        this.root = level.poll();
        break;
      }

      while (level.size() >= 2) {
        Node n1 = level.poll();
        Node n2 = level.poll();

        Node newn = new Node(Math.max(n1.max, n2.max), n1.lo, n2.hi);
        newn.left = n1;
        newn.right = n2;

        nextLevel.add(newn);
      }

      if (!level.isEmpty()) {
        nextLevel.add(level.poll());
      }

      queue.add(nextLevel);
    }

  }

  @Override
  public int query(int left, int right) {
    return query(root, left, right);
  }

  private int query(Node n, int left, int right) {
    if (right < n.lo)
      return Integer.MIN_VALUE;
    if (left > n.hi)
      return Integer.MIN_VALUE;

    if (n.lo >= left && n.hi <= right)
      return n.max;

    int max = Integer.MIN_VALUE;

    if (n.left != null && left <= n.left.hi)
      max = Math.max(max, query(n.left, left, right));

    if (n.right != null && right >= n.right.lo)
      max = Math.max(max, query(n.right, left, right));

    return max;
  }

  public int updateRange(int left, int right, int value) {
    return updateRange(root, left, right, value);
  }

  public int updateRange(Node n, int left, int right, int value) {
    if (right < n.lo)
      return 1;
    if (left > n.hi)
      return 1;

    if (n.lo >= left && n.hi <= right) {
      n.max = Math.max(n.max, value);
    }

    if (n.left != null && left <= n.left.hi)
      return 1 + updateRange(n.left, left, right, value);

    if (n.right != null && right >= n.right.lo)
      return 1 + updateRange(n.right, left, right, value);

    return 1;
  }

  @Override
  public void update(int index, int value) {
    updateRange(index, index, value);
  }
}