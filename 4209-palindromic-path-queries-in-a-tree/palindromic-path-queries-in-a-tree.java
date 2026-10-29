
import java.util.*;

class Solution {
    int n, timer, LOG;
    int[] tin, tout, depth, bit, own;
    int[][] up;
    char[] s;
    List<Integer>[] adj;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        this.n = n;
        this.s = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        own = new int[n];
        up = new int[LOG][n];
        bit = new int[n + 2];

        timer = 0;
        dfs(0, 0);

        // Initialize Fenwick tree with Euler difference array.
        for (int u = 0; u < n; u++) {
            add(tin[u], own[u]);
            add(tout[u] + 1, own[u]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int newMask = 1 << (c - 'a');
                int delta = own[u] ^ newMask;

                if (delta != 0) {
                    add(tin[u], delta);
                    add(tout[u] + 1, delta);
                    own[u] = newMask;
                    this.s[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int lca = getLCA(u, v);

                int pathMask = prefix(tin[u])
                             ^ prefix(tin[v])
                             ^ own[lca];

                ans.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return ans;
    }

    void dfs(int u, int parent) {
        tin[u] = ++timer;
        up[0][u] = parent;
        depth[u] = (u == parent) ? 0 : depth[parent] + 1;
        own[u] = 1 << (s[u] - 'a');

        for (int j = 1; j < LOG; j++)
            up[j][u] = up[j - 1][up[j - 1][u]];

        for (int v : adj[u]) {
            if (v != parent)
                dfs(v, u);
        }

        tout[u] = timer;
    }

    void add(int i, int val) {
        for (; i < bit.length; i += i & -i)
            bit[i] ^= val;
    }

    int prefix(int i) {
        int res = 0;
        for (; i > 0; i -= i & -i)
            res ^= bit[i];
        return res;
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int t = u;
            u = v;
            v = t;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0)
                u = up[j][u];
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }
}
