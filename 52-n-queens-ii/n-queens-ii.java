class Solution {
    int count = 0;

    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];

        solve(0, n, cols, diag1, diag2);
        return count;
    }

    void solve(int row, int n, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) {
            count++;
            return;
        }

        for (int col = 0; col < n; col++) {
            int d1 = row - col + n;
            int d2 = row + col;

            if (!cols[col] && !diag1[d1] && !diag2[d2]) {
                cols[col] = true;
                diag1[d1] = true;
                diag2[d2] = true;

                solve(row + 1, n, cols, diag1, diag2);

                cols[col] = false;
                diag1[d1] = false;
                diag2[d2] = false;
            }
        }
    }
}