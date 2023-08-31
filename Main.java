public class Main {
  public static void main(String[] args) {
    // Max Segment Tree
    MaxSegmentTree st = new MaxSegmentTree(new int[] { 1, 2, 3, 4, 5, });

    // assert
    assert st.query(0, 8) == 9;
    assert st.query(0, 4) == 5;
    assert st.query(4, 8) == 9;
    assert st.query(3, 5) == 6;
    assert st.query(3, 3) == 4;
    assert st.query(0, 0) == 1;
    assert st.query(8, 8) == 9;

    // update
    st.update(0, 10);
    assert st.query(0, 8) == 10;
    assert st.query(0, 4) == 5;

    st.update(4, 11);
    assert st.query(0, 8) == 11;
    assert st.query(4, 8) == 11;

    // update range
    st.updateRange(0, 4, 1);
    assert st.query(0, 8) == 12;
    assert st.query(0, 4) == 6;
    assert st.query(4, 8) == 11;
  }
}
