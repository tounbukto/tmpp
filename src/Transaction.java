import javax.sound.midi.MetaMessage;

public class Transaction {
    private ValueSet writeSet;
    private ValueSet readSet;
    private int      clock;

    public static int commitSuccess = 0;
    public static int commitAbort = 0;

    private Transaction() {
        int mem_size = Memory.memory.values.length;
        writeSet = new ValueSet(mem_size);
        readSet  = new ValueSet(mem_size);
    }

    public static ThreadLocal<Transaction> Transaction =
            new ThreadLocal<Transaction>() {
                protected synchronized Transaction initialValue() {
                    return new Transaction();
                }
            };

    public void abort() throws TransactionAbort {
        commitAbort++;
        readSet.clear();
        writeSet.clear();
        throw new TransactionAbort();
    }

    public void begin() {
        this.clock = Memory.memory.clock;
    }

    public int read(int idx) throws TransactionAbort {
        if(this.writeSet.containsIdx(idx)) {
            return this.writeSet.map[idx].value;
        }
        Value value = Memory.memory.values[idx];
        if(value.counter > this.clock) abort();
        this.readSet.set(idx, value);
        return value.value;
    }

    public void write(int idx, int newVal) throws TransactionAbort {
        if(this.writeSet.containsIdx(idx)) {
            this.writeSet.map[idx].value = newVal;
            return;
        }
        Value value = new Value(newVal);
        this.writeSet.set(idx, value);
    }

    public void commit() throws TransactionAbort {
        synchronized(Memory.memory) {
            if(!writeSet.checkWrite(clock) || !readSet.checkRead(clock)) {
                abort();
            }
            for(int i=0 ; i< writeSet.n_elements ; ++i){
                Memory.memory.values[writeSet.elements[i]].value = writeSet.map[writeSet.elements[i]].value;
                Memory.memory.values[writeSet.elements[i]].counter = Memory.memory.clock;
            }
            writeSet.clear();
            readSet.clear();
            commitSuccess++;
            Memory.memory.clock++;
        }
    }
}