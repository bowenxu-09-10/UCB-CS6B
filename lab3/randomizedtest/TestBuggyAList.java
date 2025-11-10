package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        for (int i = 4; i < 7; i++) {
            correct.addLast(i);
            buggy.addLast(i);
        }
        for (int i = 0; i < 2; i++) {
            correct.removeLast();
            buggy.removeLast();
        }
        int expected = correct.removeLast();
        int actual = buggy.removeLast();
        assertEquals(expected, actual);
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> lst = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                lst.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                if (L.size() > 0) {
                    int exp = L.getLast();
                    int act = lst.getLast();
                    assertEquals(exp, act);
                    int expected = L.removeLast();
                    int actual = lst.removeLast();
                    assertEquals(expected, actual);
                }
            }
        }
    }
}
