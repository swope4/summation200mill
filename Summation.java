import java.util.Random;

public class Summation extends Thread { //extends the thread for use of threading

    private double[] array;//array, small, high, and partial private access modifier
    private double small;
    private double high;
    private double partial;

    public Summation(double[] array, double small, double high){ //constructor for class summation
        this.array = array; //access specifiers for array, small, and high
        this.small= small;
        this.high = Math.min(high, array.length);}

    public double PartialSum(){ //in-line mutator for the partial sum 
        return partial;}

    public static double sum(double[] array){ //sum in-line mutator for sum 
        return sum(array, 0, array.length);}

    public static double sum(double[] array, double small, double high){ // sum access modifier 
        double total = 0;

        for (int i = (int) small; i < high; i++) {
            total += array[i];}
        return total;}

    public static double parallelSum(double[] arr){ //parallelSum in-line mutator
        return parallelSum(arr, Runtime.getRuntime().availableProcessors());}

    public static int parallelSum(double[] array1, int threads){ // parallel Sum access modifier 
    	
        double size = (double) Math.ceil(array1.length * 1.0 / threads); //setting size for parallel threads
        Summation[] sums = new Summation[threads]; //using constructor for parsing thread 
        for (int i = 0; i < threads; i++) { //for loop for threading
            sums[i] = new Summation(array1, i * size, (i + 1) * size); //setting the amount of threads 
            sums[i].start();} //calling sums in for loop from start to start threading
        try {
            for (Summation sum : sums) { // for threading
                sum.join();}
        } catch (InterruptedException e) {  } // to catch an error

        int total = 0;  // to count the sum
        for (Summation sum : sums) {  // the amount of sums in sum 
            total += sum.PartialSum();} // adding the sum to total

        return total;} //return total
    
    public void run(){ //run method required for running thread
        partial = sum(array, small, high); } //invoking partial in run method so it runs to and calculates

    public static void main(String[] args){ //main method
    	
        Random rand = new Random(); //random function generator
        double[] array = new double[200000000]; //setting array to 200mill
        for (int i = 0; i < array.length; i++) { //for loop for array for counting 
            array[i] = rand.nextDouble() + 1;} 

        long start = System.currentTimeMillis(); //the begging of the time to start counting the process
        Summation.sum(array); //calling the process
        System.out.println("Single: " + (System.currentTimeMillis() - start) ); // printing the time of process
        System.out.println("sum " + Summation.sum(array));// printing the added total

        start = System.currentTimeMillis(); //the begging of the time to start counting the process
        Summation.parallelSum(array); // calling the process
        System.out.println("Parallel: " + (System.currentTimeMillis() - start)); // printing the process
        System.out.println("sum " + Summation.parallelSum(array)); // printing the added total
        System.out.println("this program is going to count the same numbers but its going to parse it differently");
        System.out.println("so what you have is a single process of adding the numbers and a parallel process doing it faster");
        System.out.println("with the same numbers");
    }
}