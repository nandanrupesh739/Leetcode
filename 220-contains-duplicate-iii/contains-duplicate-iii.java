import java.util.*;

class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        TreeSet<Long> set = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            long val = nums[i];

            Long ceil = set.ceiling(val - valueDiff);

            if (ceil != null && ceil <= val + valueDiff) {
                return true;
            }

            set.add(val);

            if (i >= indexDiff) {
                set.remove((long) nums[i - indexDiff]);
            }
        }

        return false;
    }
}