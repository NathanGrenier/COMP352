package ca.concordia.algorithms;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Q2: How did the structuring pass you performed, specifically the reversals chosen, affect swaps and comparisons? Was anything else affected?
 * A2: Since we want the final array to be in DECR order, reversing the runs that are already in DECR order reduces the efficiency by increasing the number of comparisons and swaps needed to order the array. Since the largest int values are now farther away from the beginning of the array, more swaps and comparisons will be needed to shift them over to their proper position.
 * 
 * Q3: How do you feel the size of the specific runs you recorded (DESCENDING order of length 2) impacted
performance?
 * A3: The result of swapping the DECR runs of size 2 (which takes 1 swap) is that we will need to re-swap it later to get it in DECR order. If we implemented a structuring pass that was beneficial, we would gain the most out of reversing runs of the longest length as they would allow us to perform half the number of swaps (in a run, each number has a counterpart on the opposite end. You skip all the values in between and swap these directly).  
 * 
 * Q4: What would implementing this as a Doubly Linked List do? How would the specified structuring affect
results?
 * A4: A doubly linked list would take up more memory than an array, needing 2 pointers to and from each node. If you wanted to swap 2 nodes, the process would be much more tedious. You would need to swap all the pointers affected by the change (~6 pointers). You could also opt to swap the node by simply swapping the value in the nodes. This change wouldn't affect performance in a positive way compared to an array because they would simply be doing the same thing (in the best case without the pointer swap). The specified structuring would still harm the results as the linked list would be transformed into a state that is farther from its sorted state. 
 */

public class StructSort {
    static orderType sortType = orderType.DECR;
    static int DECR_count_len2 = 0;
    static int reversal_count = 0;
    static int structuring_compares = 0;
    static int compares = 0;
    static int swaps = 0;
    enum orderType {
        DECR,
        INCR;
    }
    
    static void swap(Integer[] nums, int firstIndex, int secondIndex) {
        swaps++; // Increment the swap count
        int temp = nums[firstIndex];
        nums[firstIndex] = nums[secondIndex];
        nums[secondIndex] = temp;
    }

    static void insertSort(Integer[] nums, orderType sortType) {
        boolean firstIter;
        if (sortType == orderType.DECR) {
            for (int i=1; i < nums.length; i++) {
                firstIter = true;
                for (int j=i; j > 0 && firstIter; j--) {
                    compares++;
                    if (nums[j-1] < nums[j]) {
                        swap(nums, j, j-1);
                        continue;
                    }
                    firstIter = false;
                }
            }
        } else if (sortType == orderType.INCR) {
            for (int i=1; i < nums.length; i++) {
                firstIter = true;
                for (int j=i; j > 0 && firstIter; j--) {
                    compares++;
                    if (nums[j-1] > nums[j]) {
                        swap(nums, j, j-1);
                        continue;
                    }
                    firstIter = false;
                }
            }
        }
    }

    static void reverseOrder(Integer[] nums, int startIndex, int endIndex) {
        reversal_count++;   // Increment the reversal count
        for (int i=startIndex; i < endIndex - ((endIndex - startIndex) / 2); i++) {
            swap(nums, i, endIndex - (i - startIndex));
        }
    }

    static void structPass(Integer[] nums) {
        int start = 0;
        int end = 0;
        orderType order = null;

        for (int i=0; i < nums.length - 1; i++) {
            compares++;
            structuring_compares++;
            if (nums[i+1] < nums[i]) {
                if (order != orderType.DECR && order != null) { // Going from INCR run to DECR run (i.e End of INCR run)
                    start = i + 1;
                    order = null;
                    continue;
                }
                order = orderType.DECR;
            } else {
                if (order != orderType.INCR && order != null) { // Going from DECR to INCR (i.e End of DECR run)
                    if (((end - start) + 1) == 2) {DECR_count_len2++;}
                    reverseOrder(nums, start, end);
                    start = i + 1;
                    order = null;
                    continue;
                }
                order = orderType.INCR;
            }
            end = i + 1;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Insufficient arguments passed.");
            System.exit(0);
        }
        String inputFile = args[0];

        Scanner read = null;
        try {
            read = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            System.out.printf("Incorrect file name: %s\n", inputFile);
            System.exit(0);
        }

        ArrayList<Integer> nums_arrayList = new ArrayList<>();
        while(read.hasNextInt()) {
            nums_arrayList.add(read.nextInt());
        }
        Integer[] nums = new Integer[nums_arrayList.size()]; 
        
        nums = nums_arrayList.toArray(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();

        structPass(nums);
        // for (int num : nums) {
        //     System.out.print(num + " ");
        // }
        // System.out.println(" | After Pre-Process");

        insertSort(nums, sortType);

        System.out.printf("We sorted in %s order\n", sortType);
        System.out.printf("We counted %d DECR runs of length 2\n", DECR_count_len2);
        System.out.printf("We performed %d reversals of runs in %s order\n", reversal_count, sortType);
        System.out.printf("We performed %d compares during structuring\n", structuring_compares);
        System.out.printf("We performed %d compares overall\n", compares);
        System.out.printf("We performed %d swaps overall\n", swaps);
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
