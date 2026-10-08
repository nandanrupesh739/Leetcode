class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int ans = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                int j = i;

                while (j < nums.length && nums[j] == 1) {
                    j++;
                }

                ans = Math.max(ans, j - i);
                i = j;
            }
        }

        return ans;
    }
}