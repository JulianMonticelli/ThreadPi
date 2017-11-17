import java.util.Random;

public class ThreadPi {

    public static void main(String[] args) {

        long numIterations = 0;
        int numThreads = 0;

        if (args.length != 2) {
            System.err.println("This program requires two integer arguments, numIterations and numThreads");
            System.exit(0);
        } else {
            try {
                numIterations = Long.parseLong(args[0]);
                numThreads = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        ThreadPi main = new ThreadPi();
        main.piFind(numIterations, numThreads);
    }

    public void piFind(long numIterations, int numThreads) {
        if (numIterations < 10) {
            System.err.println("You have too few iterations. The number of iterations should always be greater than or equal to 10.");
            System.exit(0);
        }

        if (numThreads < 1) {
            System.err.println("You must have at least one thread.");
            System.exit(0);
        }

        if (numThreads > numIterations) {
            System.err.println("Having more threads than iterations is nonsensical. Please fix this.");
            System.exit(0);
        }
        
        if (numIterations % numThreads != 0) {
            System.err.println("It would be nice to have a number of iterations that is exactly divisible by the number of threads.");
        }


        PiFinderThread[] thread = new PiFinderThread[numThreads];

        long[] numIn = new long[numThreads];

        for (int i = 0; i < numThreads; i++) {
            thread[i] = new PiFinderThread(i, numIterations / numThreads, numIn);
            thread[i].start();
        }

        for (Thread t : thread) {
            try {
                t.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        long totalIn = 0;
        for (long i : numIn) {
            totalIn += i;
        }

        float valOfPiOneQuad = totalIn / (float)numIterations;
        float pi = valOfPiOneQuad * 4;
        System.out.println("Value of pi = " + pi);

    }
    
    public class PiFinderThread extends Thread {
        public PiFinderThread(int threadNum, long numberToDo, long[] numIn) {
            this.threadNum = threadNum;
            this.numberToDo = numberToDo;
            this.numIn = numIn;   
        }


        int threadNum;
        long numberToDo;
        long[] numIn;

        @Override
        public void run() {
            Random rand = new Random();
            float xPos;
            float yPos;
            for (long i = (threadNum * numberToDo); i < ( (threadNum + 1) * (numberToDo)); i++) {
                xPos = rand.nextFloat();
                yPos = rand.nextFloat();
                if ( ( Math.pow(xPos, 2) + Math.pow(yPos, 2) ) < 1) {
                    numIn[threadNum]++;
                }
            }
        }
    }
}
