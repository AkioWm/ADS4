public enum BinOp {
    PLUS("+") {
        public int apply(int x, int y) { return x + y; }
    },
    MINUS("-") {
        public int apply(int x, int y) { return x - y; }
    };

    private final String javaRepr;

    BinOp(String javaRepr) {
        this.javaRepr = javaRepr;
    }

    public abstract int apply(int x, int y);
}
