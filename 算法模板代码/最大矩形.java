        int n = heights.length;
        int[] h, left, right, s;
        int tt = 0;
        h = new int[n + 2];
        left = new int[n + 2];  // left first number's index smaller than itself
        right = new int[n + 2]; // right first number's index smaller than itself
        s = new int[n + 2];     
        for (int i = 1; i <= n; i++) h[i] = heights[i - 1];
        h[0] = -1;
        h[n + 1] = -1;
        s[0] = 0;
        for (int i = 1; i <= n; i++) {
            while (h[s[tt]] >= h[i]) tt--;
            left[i] = s[tt];
            s[++tt] = i;
        }
        tt = 0;
        s[0] = n + 1;
        for (int i = n; i >= 1; i--) {
            while (h[s[tt]] >= h[i]) tt--;
            right[i] = s[tt];
            s[++tt] = i;
        }
        
        // get max res
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, (right[i] - left[i] - 1) * h[i]);
        }
        return res;