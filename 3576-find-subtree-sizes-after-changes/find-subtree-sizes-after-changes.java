class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++)
            tree[i] = new ArrayList<>();

        for (int i = 1; i < n; i++)
            tree[parent[i]].add(i);

        int[] newParent = parent.clone();
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs1(0, tree, s, last, newParent);

        List<Integer>[] newTree = new ArrayList[n];
        for (int i = 0; i < n; i++)
            newTree[i] = new ArrayList<>();

        for (int i = 1; i < n; i++)
            newTree[newParent[i]].add(i);

        int[] ans = new int[n];
        dfs2(0, newTree, ans);

        return ans;
    }

    void dfs1(int node, List<Integer>[] tree, String s,
              int[] last, int[] newParent) {

        int c = s.charAt(node) - 'a';
        int prev = last[c];
        last[c] = node;

        for (int child : tree[node]) {
            int ch = s.charAt(child) - 'a';

            if (last[ch] != -1)
                newParent[child] = last[ch];

            dfs1(child, tree, s, last, newParent);
        }

        last[c] = prev;
    }

    int dfs2(int node, List<Integer>[] tree, int[] ans) {
        ans[node] = 1;

        for (int child : tree[node]) {
            ans[node] += dfs2(child, tree, ans);
        }

        return ans[node];
    }
}