package deque;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    /** Test if the add and isEmpty funciton works well*/
    @Test
    public void addIsEmptyTest() {
        ArrayDeque<String> alst = new ArrayDeque();
        assertEquals(true, alst.isEmpty());
        for (int i = 0; i < 10; i++) {
            alst.addFirst("Me");
            alst.addLast("Love");
            alst.addFirst("Toby");
        }
        assertEquals(false, alst.isEmpty());
    }

    /** Test if the remove funciton works well*/
    @Test
    public void removeTest() {
        ArrayDeque<Integer> alst = new ArrayDeque();
        assertEquals(true, alst.isEmpty());
        for (int i = 0; i < 10; i++) {
            alst.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals((Integer) i, alst.removeLast());
        }
    }

    @Test
    /**
     * Add a few this to the list, checking get() is correct.
     */
    public void getTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<String>();
        lld1.addFirst("Toby");
        lld1.addFirst("love");
        String actual = lld1.get(1);
        String expected = "Toby";
        assertEquals(expected, actual);
        lld1.addFirst("I");
        String actual2 = lld1.get(1);
        String expected2 = "love";
        assertEquals(expected, actual);
    }
}
