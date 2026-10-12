
import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        long mod = 1000000007;
        int[] count = new int[31];

        for (int x : nums) {
            int bit = 0;
            while (x > 0) {
                if ((x & 1) == 1) {
                    count[bit]++;
                }
                x >>= 1;
                bit++;
            }
        }

        long result = 0;

        while (k-- > 0) {
            long value = 0;

            for (int i = 30; i >= 0; i--) {
                if (count[i] > 0) {
                    value += (1L << i);
                    count[i]--;
                }
            }

            result = (result + value * value) % mod;
        }

        return (int) result;
    }
}
