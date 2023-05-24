package ca.concordia.algorithms;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
