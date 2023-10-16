import java.util.Random;

public class Main {

  final static boolean DEBUG = false;

  public static void main(String[] args) {

    if (DEBUG) {
      MaxSegmentTree tree = new MaxSegmentTree(new int[] { 1, 2, 3, 4, 5 });

      System.out.println(tree.query(0, 4));
      return;
    }

    MaxSegmentTreeTester maxTester = new MaxSegmentTreeTester(1_000_000);
    maxTester.setName("Max");

    maxTester.testSegmentTree(100);
    maxTester.testUpdate(25, 100);

    SegmentTreeTester<Integer> sumTester = new SegmentTreeTester<>(1_000_000) {
      public Integer getRandom() {
        Random rd = new Random();

        return rd.nextInt(1_000_000);
      }

      public Integer merge(Integer val1, Integer val2) {
        return Math.max(val1, val2);
      }
    };

    sumTester.setName("Sum");

    sumTester.testSegmentTree(100);
    maxTester.testUpdate(25, 100);
  }
}
