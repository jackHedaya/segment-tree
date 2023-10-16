public class Main {

  final static boolean DEBUG = false;

  public static void main(String[] args) {

    if (DEBUG) {
      MaxSegmentTree tree = new MaxSegmentTree(new int[] { 1, 2, 3, 4, 5 });

      System.out.println(tree.query(0, 4));
      return;
    }

    MaxSegmentTreeTester tester = new MaxSegmentTreeTester(1_000_000);

    tester.testSegmentTree(100);
    tester.testUpdate(25, 100);
  }
}
