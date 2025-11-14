package deque;

import org.junit.Test;
import static deque.TimeTest.printTimingTable;
import static deque.TimeTest.timeCompute;
import static org.junit.Assert.*;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /**
     * Add a few this to the list, checking get() is correct.
     */
    public void getTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
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
    /**
     * Print all items in a deque from first to the end.
     */
    public void printDequeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        lld1.addLast("Hello");
        lld1.addFirst("World");
        lld1.addFirst("Hell");
        lld1.printDeque();
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

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
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }
    @Test
    /**
     * RemoveFirst test.
     */
    public void removeFirstTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(5);
        lld1.addFirst(4);
        lld1.addFirst(6);
        lld1.addFirst(7);
        assertEquals((Object) 7, lld1.removeFirst());
        lld1.addFirst(3);
        assertEquals((Object) 3, lld1.removeFirst());
        assertEquals((Object) 6, lld1.removeFirst());
        assertEquals((Object) 4, lld1.removeFirst());
        assertEquals((Object) 5, lld1.removeFirst());
    }

    @Test
    /**
     * RemoveLast test.
     */
    public void removeLastTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 10000; i++) {
            lld1.addLast(i);
        }
        for (int i = 9999; i >= 0; i--) {
            assertEquals((Object) i, lld1.removeLast());
        }
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    /**
     * Time test for add and remove functions. The time shall be constant regardless
     * of the size of the deque.
     */
    public  void timeTest() {
        LinkedListDeque<Integer> Ns = new LinkedListDeque();
        LinkedListDeque<Double> times = new LinkedListDeque<>();
        LinkedListDeque<Integer> opCounts = new LinkedListDeque<>();
        for (int i = 1000; i <= 128_000; i *= 2) {
            timeCompute(i, Ns, times, opCounts);
            Ns.addLast(i);
            opCounts.addLast(i);
        }
        printTimingTable(Ns, times, opCounts);
    }

    @Test
    /**
     * Check whether equals() works right.
     */
    public void equalsTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addLast("I");
        lld1.addLast("love");
        lld1.addLast("Toby");
        for (String str : lld1) {
            System.out.println(str);
        }
        LinkedListDeque<String> lld2 = new LinkedListDeque<>();
        lld2.addLast("I");
        lld2.addLast("love");
        lld2.addLast("Toby");
        for (String str : lld2) {
            System.out.println(str);
        }
        ArrayDeque<String> lld3 = new ArrayDeque<>();
        lld3.addLast("I");
        lld3.addLast("love");
        lld3.addLast("Toby");

        assertEquals(true, lld1.equals(lld2));
        assertEquals(true, lld1.equals(lld3));
    }
}
