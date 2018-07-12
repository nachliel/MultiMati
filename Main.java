/****************************************************************
 * MultiMati V1.1 - The Multipication of Matrices for you!
 * (C) Nachliel Shiloh Hills all rights reserved! :)
 *  Tel: +972-54-680-5830
 *  email: nachliels@mail.tau.ac.il
 *  Date: 10.4.18
 *   Written for Or from Moveo, ofcourse this algorithm will cost you 20 grand a month :)
 *   hope you enjoy the code. I enjoyed the task. last time I programmed Java was before 7 years ago.
 *   The algorithm is very simple, I hope that this is what you looked for.
 *   You can enable printing of matrices (not recommended if you make large matrices... just remove the note bar.
 *   Also you can check the Task Driven Development for comparision. just remove the note bars.
 *****************************************************************/
package com.company;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Main {

    public static void main(String[] args) throws IOException {
        int threadNum;
        int matrixSize = 1000;
        int matrixCount = 2;
        ThreadPool threadPool;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = true;
        //User input :
        System.out.println("MultiMati v1.1.0, Written by Nachliel Shiloh Hills");
        System.out.println("------------------------------------------------------");
        System.out.println("Welcome,");
        System.out.print("Threads: ");
        threadNum = Integer.parseInt(br.readLine());
        threadPool = new ThreadPool(threadNum);

        while (exit) {
            System.out.print("Matrix Size: ");
            matrixSize = Integer.parseInt(br.readLine());
            System.out.print("Number of Matrixs: ");
            matrixCount = Integer.parseInt(br.readLine());

            startRound(threadNum, matrixCount, matrixSize);

            System.out.print("Try Another Matrix? (y/n): ");
            String str = br.readLine();
            if ((str.equals("y")) || (str.equals("yes")))
                exit = true;
            else {
                exit = false;
                System.out.print("Bye,");
            }
        }

    }

    //startRound method used for running the app.
    private static void startRound(int threadsNum, int matrixCount, int matrixSize) {
        MatrixCollection myMatrix = new MatrixCollection(matrixSize,matrixCount);
        int cores = Runtime.getRuntime().availableProcessors();
        int lines = (int)Math.ceil(matrixSize / (double)threadsNum);
        int startLine = 0;
        long startTime = System.nanoTime();

        while (startLine < matrixSize) {
            jugg[threadNum] = new Juggling(myMatrix,startLine,lines);
            myThreads[threadNum] = new Thread(jugg[threadNum]);
            myThreads[threadNum].start();
            startLine += lines;
            threadNum++;
        }
        /*
        System.out.print("Number of Cores: " + cores);
        Juggling [] jugg = new Juggling[threadsNum];
        Thread myThreads [] = new Thread[threadsNum];

        //Thread pooling:
        int threadNum = 0;

        while (startLine < matrixSize) {
            jugg[threadNum] = new Juggling(myMatrix,startLine,lines);
            myThreads[threadNum] = new Thread(jugg[threadNum]);
            myThreads[threadNum].start();
            startLine += lines;
            threadNum++;
        }

        int runOnCore = 0;
        int i = 0;
        while (startLine < matrixSize) {
            if (runOnCore>cores) {
                runOnCore = 0;
                boolean add = true;
                while(add) {
                    if(!myThreads[i].isAlive()) {
                        add = false;
                        jugg[i].restart(startLine,lines);
                        myThreads[i] = new Thread(jugg[i]);
                        myThreads[i].start();
                    }
                    if (i == cores)
                        i = 0;
                    else
                        i++;
                }
            }
            else {
                jugg[threadNum] = new Juggling(myMatrix,startLine,lines);
                myThreads[threadNum] = new Thread(jugg[threadNum]);
                myThreads[threadNum].start();
            }
            startLine += lines;
            threadNum++;
            runOnCore++;
        }

        try {
            for(i=0; i<cores;i++)
                myThreads[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        long timer = System.nanoTime() - startTime;

        //myMatrix.printMatrixs();
        System.out.println("Matrix complete.");
        //Print on console the result matrix. Hide it if unneccessery
        //myMatrix.printResult();
        System.out.println("Completed in " + TimeUnit.NANOSECONDS.toMillis(timer) + " ms, i.e " + timer + " nanoseconds.");


        //Test Driven Development: -- check if the matrix is o.k and time gap between Concurrency (thread based) calculation of matrix to regular calculation.
        startTime = System.nanoTime();
        System.out.println("Test Driven Development Success: " + myMatrix.testDrivenDevelopment());
        long timerSlow = System.nanoTime() - startTime;
        System.out.println("Test/Regular method Completed in " + TimeUnit.NANOSECONDS.toMillis(timerSlow) + " ms, i.e " + timerSlow + " nanoseconds.");
        System.out.println("Thread to regular timed gap: ~" + TimeUnit.NANOSECONDS.toMillis(timerSlow - timer) + " ms,");

    }
}

//Juggling class used for running the multipleByLine method of Matrix Collection class for use in many threads.
class Juggling implements Runnable {
    private int rowNum;
    private int startRow;
    private MatrixCollection matrix;



    public Juggling(MatrixCollection myMatrix,int startLine, int lines) {
        matrix = myMatrix;
        rowNum = lines;
        startRow = startLine;
    }

    public void restart(int startLine, int lines) {
        rowNum = lines;
        startRow = startLine;
    }
    @Override
    public void run() {
        matrix.multiplyByLine(startRow,rowNum);
    }
}

class ThreadPool implements Runnable{
    private int threadsNumber;
    private int[] rowNum;
    private int[] startRow;
    private MatrixCollection matrix;
    private int numOfQuerys = 0;
    Thread myThreads [];
    Juggling [] juggling;

    public ThreadPool(int number,MatrixCollection matrixx ) {
        threadsNumber = number;
        rowNum = new int[number];
        startRow = new int[number];
        myThreads = new Thread[threadsNumber];
        juggling = new Juggling[threadsNumber];
        matrix = matrixx;
        for(int i = 0; i<threadsNumber;i++) {
            myThreads[numOfQuerys] = new Thread();
            myThreads[i].start();
        }
    }


    public void start() {
        for(int i=0; i<numOfQuerys;i++)
            myThreads[i].start();
    }

    public void push(int startLine, int line, boolean execute) {
        rowNum[numOfQuerys] = line;
        startRow[numOfQuerys] = startLine;
        juggling[numOfQuerys] = new Juggling(matrix,startLine,line);
        myThreads[numOfQuerys] = new Thread(juggling[numOfQuerys]);
        if (execute)
            myThreads[numOfQuerys].start();
        numOfQuerys++;
    }

    public void hold() {
        try {
            for(int i=0; i<threadsNumber;i++)
                myThreads[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        matrix.multiplyByLine(startRow,rowNum);
    }
}

class ThreadRunner extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            total += i;
        }
    }
}

class ThreadAPool {
    private int threadsNumber;
    private int[] rowNum;
    private int[] startRow;
    private int numOfQuerys = 0;
    ThreadB myThreads [];
    int queryNum = 0;

    public void ThreadAPool(int number){
        threadsNumber = number;
        rowNum = new int[number];
        startRow = new int[number];
        myThreads = new ThreadB[threadsNumber];
        for(int i = 0; i<threadsNumber;i++) {
            myThreads[i] = new ThreadB();
            try{
                myThreads[i].wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void insertQuery(int line, int numOfLines, MatrixCollection matrix) {
        if (queryNum<threadsNumber)
            myThreads[queryNum++].start(line,numOfLines,matrix);
    }
}

class ThreadB extends Thread{
    @Override
    public void run(int line, int numOfLines, MatrixCollection matrix) {
        synchronized(this){
            matrix.multiplyByLine(line,numOfLines);
        }
    }
}
/*
class Juggle implements Runnable {
    private final ExecutorService pool;

    public Juggle(int poolSize) {
        throws IOException {
            //serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(poolSize);

        }
    }

    public void run(MatrixCollection matrix, int startRow, int rowNum) { // run the service
        try {
            for (; ; ) {
                pool.execute(new Handler(matrix,startRow, rowNum));
            }
        } catch (IOException ex) {
            pool.shutdown();
        }
    }
}

class Handler implements Runnable {
    private int rowNum;
    private int startRow;
    private MatrixCollection matrix;

    public Handler(MatrixCollection myMatrix,int startLine, int lines) {
        matrix = myMatrix;
        rowNum = lines;
        startRow = startLine;
    }
    @Override
    public void run() {
        matrix.multiplyByLine(startRow,rowNum);
    }
}
*/
//Matrix Collection class, Store main matrices, Multiple the matrices, check timing and conssistency of the result matrix.
class MatrixCollection {
    private int sizeOf;
    private int numOf;

    private int[][] mainMatrix;
    private double[] resultMatrix;

    private long startTime;
    private long endTime;

    public MatrixCollection(int sizeOfMatrix, int numOfMatrix) {
        sizeOf = sizeOfMatrix;
        numOf = numOfMatrix;
        Random rand = new Random();
        mainMatrix = new int[numOf][sizeOf*sizeOf];
        resultMatrix = new double[sizeOf*sizeOf];
        //Filling the matrix;
        for (int n = 0; n < numOf; n++)
            for (int i = 0; i < sizeOf; i++)
                for (int j = 0; j < sizeOf; j++)
                    mainMatrix[n][i*sizeOf + j] = rand.nextInt(10) + 1;

        //Prepare result matrix for multiply methods:
        for (int i = 0; i < sizeOf; i++)
            for (int j = 0; j < sizeOf; j++)
                resultMatrix[i*sizeOf+j] = (double) mainMatrix[0][i*sizeOf+j];
    }

    //Multiple the matrices by blocks of lines. used for thread calculation combaind with Juggling class.
    //For more instructions how the method is working go to simple multiplyMatrix method.
    public void multiplyByLine(int startLine,int numOfRows) {
        if (startLine + numOfRows > sizeOf)
            numOfRows = sizeOf;
        else
            numOfRows = startLine + numOfRows;
        double[] cellSum = new double[sizeOf*sizeOf];
        startWatch();
        for (int n = 1; n < numOf; n++) {
            for (int i = startLine; i < numOfRows; i++)
                for (int j = 0; j < sizeOf; j++) {
                    cellSum[i*sizeOf + j] = 0;
                    for (int k = 0; k < sizeOf; k++)
                        cellSum[i*sizeOf + j] += resultMatrix[i*sizeOf+k] * mainMatrix[n][k*sizeOf+j];
                }
            for(int i = startLine; i< numOfRows;i++)
                for(int j=0;j<sizeOf;j++)
                    resultMatrix[i*sizeOf + j] = cellSum[i*sizeOf + j];
            cellSum = new double[sizeOf*sizeOf];
        }
        stopWatch();
    }
    //multiplyMatrix multiply the collection of matrices. one by one.
    // the result is stored and multiplied again with the next matrix and so on...
    public void multiplyMatrix() {
        double[] cellSum = new double[sizeOf*sizeOf];

        //Measure time
        startWatch();
        for (int n = 1; n < numOf; n++) {
            for (int i = 0; i < sizeOf; i++) {
                for (int j = 0; j < sizeOf; j++) {
                    cellSum[i*sizeOf + j] = 0;
                    for (int k = 0; k < sizeOf; k++) {
                        cellSum[i*sizeOf + j] += resultMatrix[i*sizeOf + k] * mainMatrix[n][k*sizeOf + j];
                    }
                }
            }
            resultMatrix = cellSum;
            cellSum = new double[sizeOf*sizeOf];
        }
        stopWatch();
    }
    //Print the result matrix
    public void printResult() {
        System.out.println();
        for (int i = 0; i < sizeOf; i++) {
            for (int j = 0; j < sizeOf; j++) {
                System.out.print(String.format("%.0f", resultMatrix[i*sizeOf + j]) + ", ");
            }
            System.out.println();
        }
    }
    //Prints the main matrices
    public void printMatrixs() {
        System.out.println();
        for (int n = 0; n < numOf; n++) {
            System.out.println("MatrixCollection : " + (n + 1));
            for (int i = 0; i < sizeOf; i++) {
                for (int j = 0; j < sizeOf; j++) {
                    System.out.print(mainMatrix[n][i*sizeOf + j] + ", ");
                }
                System.out.println();
            }
        }
    }
    //Start watch for timing checks.
    private void startWatch() {
        startTime = System.nanoTime();
    }
    //Stop watch for timing checks
    private void stopWatch() {
        endTime = System.nanoTime();
    }
    //Get time in nanoseconds that took for multiplyMatrix or multiplyMatrixByline.
    public long getWatch() {
        return endTime-startTime;
    }
    //This checks if the thread is working and consstatanly.
    public boolean testDrivenDevelopment() {
        double[] firmResultMatrix = new double[sizeOf*sizeOf];
        double[] cellSum = new double[sizeOf*sizeOf];
        //Prepare return matrix
        for (int i = 0; i < sizeOf; i++)
            for (int j = 0; j < sizeOf; j++)
                firmResultMatrix[i*sizeOf + j] = (double) mainMatrix[0][i*sizeOf + j];

        for (int n = 1; n < numOf; n++) {
            for (int i = 0; i < sizeOf; i++)
                for (int j = 0; j < sizeOf; j++) {
                    cellSum[i*sizeOf + j] = 0;
                    for (int k = 0; k < sizeOf; k++) {
                        cellSum[i*sizeOf + j] += firmResultMatrix[i*sizeOf + k] * mainMatrix[n][k*sizeOf + j];
                    }
                }
            firmResultMatrix = cellSum;
            cellSum = new double[sizeOf*sizeOf];
        }

        for(int i=0;i<sizeOf;i++)
            for(int j=0;j<sizeOf;j++)
                if (firmResultMatrix[i*sizeOf + j]!=resultMatrix[i*sizeOf + j])
                    return false;
        return true;
    }
}