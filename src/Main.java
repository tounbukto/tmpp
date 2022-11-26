class Main extends Thread {
    private int idProcess = 0;

    public Main(){}
    public Main(int idProcess) {
        this.idProcess = idProcess;
    }

    public int delay(int n) {
        try {
            if(n == 0)
                n = 16;
            Thread.sleep(1 + (int)(n * Math.random()));
        } catch(InterruptedException e) {}

        return n < 512 ? n << 1 : n;
    }

    public void test() {
        int n = 0;
        try {

            Transaction transaction = Transaction.Transaction.get();

            transaction.begin();

            int val = transaction.read(0);
            transaction.write(0, val + 1);

            transaction.commit();

            System.out.println("---> " + Memory.memory.values[0].value);
        } catch(TransactionAbort abort) {
            n = delay(n);
            test();
        }
    }
    
    public void test1(int idProcess) {
        int n = 0;
        final int chunk = Memory.memory.values.length/2;
        try {
            Transaction transaction = Transaction.Transaction.get();

            transaction.begin();
            for(int i= idProcess ; i< Memory.memory.values.length-idProcess; ++i){
                int val = transaction.read(i);
                transaction.write(i, val + 1);
            }

            transaction.commit();

        }catch(TransactionAbort abort) {
            System.out.println("abooort");
            n = delay(n);
            test1(idProcess);
        }
    }

    public synchronized void testSync() {
        Memory.memory.values[0].value++;
    }

    public void run() {
//        for(int i=0; i<10000; i++)
//            test();
            test1(idProcess);
    }

    public static void main(String args[]) throws Exception {
        int n = 500;
        Thread threads[] = new Thread[n];

        for(int i=0; i<n; i++)
            (threads[i] = new Main(i+1)).start();

        (new Main(0)).run();

        for(int i=0; i<n; i++)
            threads[i].join();

        for(int i=0 ; i<Memory.memory.values.length ; ++i){
            System.out.print(Memory.memory.values[i].value + " - ");
        }
//        System.out.println("\n\n--> " + Memory.memory.values[0].value + " ("
//                + Transaction.commitSuccess + ", "
//                + Transaction.commitAbort + ")");

        System.out.println("\n\n--> ("
                + Transaction.commitSuccess + ", "
                + Transaction.commitAbort + ")");
    }
}