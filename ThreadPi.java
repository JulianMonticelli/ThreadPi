import java.util.Random;

public class ThreadPi {

    public static void main(String[] args) {

        int numIterations = 0;
        int numThreads = 0;

        if (args.length != 2) {
            System.out.println("This program requires two integer arguments, numIterations and numThreads");
            System.exit(0);
        } else {
            try {
                numIterations = Integer.parseInt(args[0]);
                numThreads = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        ThreadPi main = new ThreadPi();
        main.piFind(numIterations, numThreads);
    }

    public void piFind(int numIterations, int numThreads) {
        if (numIterations < 10) {
            System.out.println("You have too few iterations. The number of iterations should always be greater than or equal to 10.");
            System.exit(0);
        }

        if (numThreads < 1) {
            System.out.println("You must have at least one thread.");
            System.exit(0);
        }

        if (numThreads > numIterations) {
            System.out.println("Having more threads than iterations is nonsensical. Please fix this.");
            System.exit(0);
        }

        float[] xPos = new float[numIterations];
        float[] yPos = new float[numIterations];

        PiFinderThread[] thread = new PiFinderThread[numThreads];

        int[] numIn = new int[numThreads];

        for (int i = 0; i < numThreads; i++) {
            thread[i] = new PiFinderThread(i, numIterations / numThreads, xPos, yPos, numIn);
            thread[i].start();
        }

        for (Thread t : thread) {
            try {
                t.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        int totalIn = 0;
        for (int i : numIn) {
            totalIn += i;
        }

        float valOfPiOneQuad = totalIn / (float)numIterations;
        float pi = valOfPiOneQuad * 4;
        System.out.println("Value of pi = " + pi);

    }
    
    public class PiFinderThread extends Thread {
        public PiFinderThread(int threadNum, int numberToDo, float[] xPos, float[] yPos, int[] numIn) {
            this.threadNum = threadNum;
            this.numberToDo = numberToDo;
            this.xPos = xPos;
            this.yPos = yPos;
            this.numIn = numIn;   
        }


        int threadNum;
        int numberToDo;
        float[] xPos;
        float[] yPos;
        int[] numIn;

        @Override
        public void run() {
            Random rand = new Random();
            for (int i = (threadNum * numberToDo); i < ( (threadNum + 1) * numberToDo); i++) {
                xPos[i] = rand.nextFloat();
                yPos[i] = rand.nextFloat();
                if ( ( Math.pow(xPos[i], 2) + Math.pow(yPos[i], 2) ) < 1) {
                    numIn[threadNum]++;
                }
            }
        }
    }
}
