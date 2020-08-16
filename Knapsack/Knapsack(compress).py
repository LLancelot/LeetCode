'''
路径压缩DP
'''

def solve_knapsack(profits, weights, capacity):
    # basic check
    n = len(profits)
    if capacity <= 0 or n == 0 or len(weights) != n:
        return 0

    # DEFINE dp[index][capacity+1]
    dp = [[0 for x in range(capacity + 1)] for y in range(2)]

    # IF WE HAVE ONLY ONE WEIGHT, WE WILL TAKE IT IF IT IS NOT MORE THAN THE CAPACITY
    for c in range(0, capacity + 1):
        if weights[0] <= c:
            dp[0][c] = dp[1][c] = profits[0]

    # PROCESS ALL SUB-ARRAYS FOR ALL THE CAPACITIES
    for idx in range(1, n):
        for c in range(0, capacity + 1):
            pf1, pf2 = 0, 0
            pf1 = profits[idx] + dp[(idx - 1) % 2][c - weights[idx]] if weights[idx] <= c else pf1
            pf2 = dp[(idx - 1) % 2][c]
            dp[idx % 2][c] = max(pf1, pf2)

    return dp[(n - 1) % 2][capacity]


def main():
    print(solve_knapsack(profits=[1, 6, 10, 16], weights=[1, 2, 3, 5], capacity=5))
    print(solve_knapsack(profits=[1, 6, 10, 16], weights=[1, 2, 3, 5], capacity=6))
    print(solve_knapsack(profits=[1, 6, 10, 16], weights=[1, 2, 3, 5], capacity=7))


main()
