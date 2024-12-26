class Triple<K, V, R> {
    private K first;
    private V second;
    private R third;

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