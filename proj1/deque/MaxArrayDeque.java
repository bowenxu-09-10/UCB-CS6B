package deque;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    public MaxArrayDeque() {
        super();
    }

    public static void main(String[] args) {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>();
        mad.addLast("I");
        mad.addLast("love");
        mad.addLast("Toby");
        mad.printDeque();
    }
}
