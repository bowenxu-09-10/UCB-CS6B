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

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /**
     * Check whether equals() works right.
     */
    public void equalsTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();
        lld1.addLast("I");
        lld1.addLast("love");
        lld1.addLast("Toby");
        for (String str : lld1) {
            System.out.println(str);
        }
        ArrayDeque<String> lld2 = new ArrayDeque<>();
        lld2.addLast("I");
        lld2.addLast("love");
        lld2.addLast("Toby");
        for (String str : lld2) {
            System.out.println(str);
        }

        assertEquals(true, lld1.equals(lld2));
        LinkedListDeque<String> lld3 = new LinkedListDeque<>();
        lld3.addLast("I");
        lld3.addLast("love");
        lld3.addLast("Toby");
        assertEquals(false, lld1.equals(lld3));
    }
}
