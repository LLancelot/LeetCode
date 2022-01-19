## 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面

https://url.cy/3q1YC2

输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数在数组的前半部分，所有偶数在数组的后半部分。

 

示例：

输入：nums = [1,2,3,4]
输出：[1,3,2,4] 
注：[3,1,2,4] 也是正确的答案之一。



提示：

0 <= nums.length <= 50000
0 <= nums[i] <= 10000



### 代码

- 双指针指向头尾元素，左指针检测到每个偶数，就与右指针交换即可。

```java
class Solution {
    public int[] exchange(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            if (nums[i] % 2 == 1) {
                i++;
            } else {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j--;
            }
        }
        return nums;
    }
}
```

