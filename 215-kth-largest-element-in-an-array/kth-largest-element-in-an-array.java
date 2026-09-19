class Solution {

    public int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        int left = 0, right = nums.length - 1;

        while (left <= right) {

            int mid = left + ((right - left) >>> 1);

            // Median-of-three pivot
            int pivot = median(nums[left], nums[mid], nums[right]);

            int lt = left;
            int i = left;
            int gt = right;

            // 3-way partition
            while (i <= gt) {
                if (nums[i] < pivot) {
                    swap(nums, lt++, i++);
                } else if (nums[i] > pivot) {
                    swap(nums, i, gt--);
                } else {
                    i++;
                }
            }

            if (target < lt) {
                right = lt - 1;
            } else if (target > gt) {
                left = gt + 1;
            } else {
                return pivot;
            }
        }

        return -1;
    }

    private int median(int a, int b, int c) {
        if (a < b) {
            if (b < c) return b;
            return a < c ? c : a;
        } else {
            if (a < c) return a;
            return b < c ? c : b;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}