package comp352.assignment.one;
/*
 * Q1: Explain how recursion might be used in the sequence generation in this assignment?
 * A1: We calculate the value at a specific point in the sequence by recursively calculating all values used for its calculation. In this case, those values come before it. We calculate the values in the sequence using the equation: value n = n-2 + n-3 + (2 * n-4). If the value of n is less than 5, we know that its value is 1 due to the constraints of the problem (This is our base case). 
 * 
 * Q2: How does the size of the input sequence affect the memory requirements of your program?
 * A2: With each recursive call, a new stack frame must be created. This stack consumes memory which is finite. The stacks are only "freed" from memory when the base case is achieved and the frames are popped. With larger inputs, there are more recursive calls and therefore more memory is consumed.
 */

public class RecursiveFibonacciTracker {
    static long calls = 0;

    static long fib(int n) {
        calls++;
        if (n <= 5) {
            return 1;
        }
        return fib(n-2) + fib(n-3) + (2 * fib(n-4));
    }

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Enter only 1 argument.");
            System.exit(0);
        }

        int length = Integer.parseInt(args[0]);
        if (length == 0) {
            System.out.println("Value passed must be greater than 0.");
            System.exit(0);
        }
        
        for (int i=1; i <= length; i++) {
            System.out.print(fib(i) + " ");
        }
        System.out.println("\nCalls: " + calls);
    }
}
