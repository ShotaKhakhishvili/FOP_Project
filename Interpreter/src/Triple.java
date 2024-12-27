/**
 * The Triple class represents a container for holding three related objects of potentially different types.
 * This class provides methods to access and modify the values of these objects.
 *
 * @param <K> Type of the first element in the triple.
 * @param <V> Type of the second element in the triple.
 * @param <R> Type of the third element in the triple.
 */
class Triple<K, V, R> {

    private K first; // First element of type K
    private V second; // Second element of type V
    private R third; // Third element of type R


    /**
     * Constructor to initialize the triple with three values.
     *
     * @param first The first value of type K.
     * @param second The second value of type V.
     * @param third The third value of type R.
     */
    public Triple(K first, V second, R third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public R getThird() {
        return third;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public void setThird(R third) {
        this.third = third;
    }
}
