package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;
    /**
     * Creates a MaxArrayDeque with the given Comparator
     */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max(Comparator<T> c) {
        if (c == null) {
            c = comparator;
        }
        T max = get(0);
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(get(i), max) > 0) {
                max = get(i);
            }
        }
        return max;
    }
}
