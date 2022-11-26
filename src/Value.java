public class Value {
    public int value;
    public int counter;

    public Value() {
        this.value = 0;
        this.counter = 0;
    }
    public Value(int value) {
        this.value = value;
        this.counter = 0;
    }

    public Value(int value, int counter) {
        this.value = value;
        this.counter = counter;
    }

    public String toString() {
        return "value => {"+this.value+", "+this.counter+"}";
    }
}