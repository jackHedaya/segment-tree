
public class Main {
  public static void main(String[] args) {

    MaxSegmentTreeTester tester = new MaxSegmentTreeTester(1_000_000);

    tester.testMax(100);
    tester.testUpdate(25, 100);
  }
}
