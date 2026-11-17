import java.util.*;

class Solution {

    public int minimumChanges(String s, int k) {
        int n = s.length();

        int[][] cost = new int[n][n];

        // Precompute semi-palindrome conversion costs
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;
                int best = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {

                    if (len % d != 0) continue;

                    int changes = 0;

                    for (int r = 0; r < d; r++) {

                        int[] group = new int[len / d];

                        int idx = 0;
                        for (int pos = i + r; pos <= j; pos += d) {
                            group[idx++] = pos;
                        }

                        int left = 0;
                        int right = group.length - 1;

                        while (left < right) {
                            if (s.charAt(group[left]) !=
                                s.charAt(group[right])) {
                                changes++;
                            }

                            left++;
                            right--;
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[i][j] = best;
            }
        }

        // DP
        int INF = 1000000;
        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int p = 1; p <= k; p++) {
            for (int i = 2 * p; i <= n; i++) {

                for (int j = 2 * (p - 1); j <= i - 2; j++) {

                    dp[p][i] = Math.min(
                        dp[p][i],
                        dp[p - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
