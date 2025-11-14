package deque;

import java.util.Comparator;

public class MaxArrayLauncher<T> {
    public static Comparator<Integer> getIntComparator() {
        return new IntComparator();
    }

    /**
     * IntComparator, return positive number if a > b,
     * return 0 if a = b, return negative if a < b.
     */
    private static class IntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    public static void main(String[] args) {
        MaxArrayDeque<Integer> adl = new MaxArrayDeque<>(getIntComparator());
        adl.addFirst(1);
        adl.addLast(2);
        adl.addLast(100);
        adl.addLast(20);
        System.out.println(adl.max(null));
    }
}
