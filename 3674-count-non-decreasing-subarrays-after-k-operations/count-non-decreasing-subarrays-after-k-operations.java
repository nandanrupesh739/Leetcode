
import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 32 - Integer.numberOfLeadingZeros(n);

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

      
        int[] next = new int[n];
        Arrays.fill(next, n);

        Stack<Integer> st = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[st.peek()] <= nums[i]) {
                st.pop();
            }

            if (!st.isEmpty()) {
                next[i] = st.peek();
            }

            st.push(i);
        }

       
        int[][] up = new int[LOG][n];
        long[][] sum = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];

            if (next[i] < n) {
                sum[0][i] =
                    (long) nums[i] * (next[i] - i)
                    - (prefix[next[i]] - prefix[i]);
            }
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                int mid = up[j - 1][i];

                if (mid < n) {
                    up[j][i] = up[j - 1][mid];
                    sum[j][i] = sum[j - 1][i]
                              + sum[j - 1][mid];
                } else {
                    up[j][i] = n;
                    sum[j][i] = sum[j - 1][i];
                }
            }
        }


        long ans = 0;

        for (int l = 0; l < n; l++) {
            int low = l;
            int high = n - 1;
            int best = l - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                int cur = l;
                long cost = 0;

                for (int j = LOG - 1; j >= 0; j--) {
                    if (up[j][cur] <= mid) {
                        cost += sum[j][cur];
                        cur = up[j][cur];
                    }
                }

                cost += (long) nums[cur] * (mid - cur + 1)
                      - (prefix[mid + 1] - prefix[cur]);

                if (cost <= k) {
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            ans += best - l + 1;
        }

        return ans;
    }
}
