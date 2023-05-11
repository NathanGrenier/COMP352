import java.util.ArrayList;
import java.util.Random;

public class MakeRandom {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.out.println("Invalid Number of Arguments Passed");
            System.exit(0);
        }
        if (args.length == 1) {
            Random rnd = new Random();
            int count = Integer.parseInt(args[0]);
            int[] nums = new int[count];
            for (int i=0; i < nums.length; i++) {
                nums[i] = rnd.nextInt(Integer.MAX_VALUE);
                System.out.print(nums[i] + " ");
            }
        } else if (args.length == 2) {
            long seed = Long.parseLong(args[0]);
            int count = Integer.parseInt(args[1]);
            Random rnd = new Random(seed);
            int[] nums = new int[count];
            for (int i=0; i < nums.length; i++) {
                nums[i] = rnd.nextInt(Integer.MAX_VALUE);
                System.out.print(nums[i] + " ");
            }
        }
    }   
}
