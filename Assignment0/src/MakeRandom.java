import java.util.Random;

public class MakeRandom {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.out.println("Invalid Number of Arguments Passed");
            System.exit(0);
        }
        int[] nums;
        int count = 0;
        long seed;
        Random rnd = null;
        if (args.length == 1) {
            rnd = new Random();
            count = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            seed = Long.parseLong(args[0]);
            count = Integer.parseInt(args[1]);
            rnd = new Random(seed);
        }
        nums = new int[count];
        for (int i=0; i < nums.length; i++) {
            nums[i] = rnd.nextInt(Integer.MAX_VALUE);
            System.out.print(nums[i] + " ");
        }
    }   
}
