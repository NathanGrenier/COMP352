import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StructSort {
    static int DECR_count_len2 = 0;
    static int reversal_count = 0;
    static int structuring_compares = 0;
    static int compares = 0;
    static int swaps = 0;
    enum orderType {
        DECR,
        INCR;
    }
    
    static void reverseOrder(Integer[] nums, int start, int end) {

    }

    static void structPass(Integer[] nums) {
        int start = 0;
        int end;
        orderType order = null;
        if (nums.length == 1) {
            return;
        }

        for (int i=0; i < nums.length; i++) {
            if (nums[i] > nums[i+1]) {
                if (order == orderType.DECR || order == null) {
                    end = i + 1;
                } else {
                    order = orderType.INCR;
                    start = i + 1;
                    end = i + 1;
                }
            } else if (nums[i] < nums[i+1]) {

            } else {
                end = i + 1;
            }
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
            System.out.println("Incorrect file name.");
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


    }
}
