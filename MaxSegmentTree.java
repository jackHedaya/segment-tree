import java.util.Arrays;

interface SegmentTree {
  public int query(int left, int right);

  public void update(int index, int value);
}

public class MaxSegmentTree implements SegmentTree {
  private int n;
  int[] lo, hi, min, delta;

  public MaxSegmentTree(int n) {
    this.n = n;

    hi = new int[4 * n + 1];
    lo = new int[4 * n + 1];

    min = new int[4 * n + 1];
    delta = new int[4 * n + 1];

    init(1, 0, n - 1);
  }

  void init(int i, int a, int b) {
    lo[i] = a;
    hi[i] = b;

    if (a == b)
      return;

    int m = (a + b) / 2;

    init(2 * i, a, m);
    init(2 * i + 1, m + 1, b);
  }

  @Override
  public int query(int left, int right) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'query'");
  }

  public void updateRange(int left, int right, int value) {

  }

  @Override
  public void update(int index, int value) {
    updateRange(index, index, value);
  }
}