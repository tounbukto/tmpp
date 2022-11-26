public class Memory {
    public Value[] values;
    public int clock;

    public static Memory memory = new Memory(1024);
    private Memory(int length) {
        this.clock = 1;
        this.values = new Value[length];
        for(int i=0 ; i<length; ++i){
            this.values[i] = new Value();
        }

    }
}
