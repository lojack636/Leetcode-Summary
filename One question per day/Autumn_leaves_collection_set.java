/**
小扣出去秋游，途中收集了一些红叶和黄叶，他利用这些叶子初步整理了一份秋叶收藏集 leaves， 
字符串 leaves 仅包含小写字符 r 和 y， 其中字符 r 表示一片红叶，字符 y 表示一片黄叶。
出于美观整齐的考虑，小扣想要将收藏集中树叶的排列调整成「红、黄、红」三部分。
每部分树叶数量可以不相等，但均需大于等于 1。每次调整操作，小扣可以将一片红叶替换成黄叶或者将一片黄叶替换成红叶。
请问小扣最少需要多少次调整操作才能将秋叶收藏集调整完毕。

提示：
    3 <= leaves.length <= 10^5
    leaves 中只包含字符 'r' 和字符 'y'

方法：DP。定义数组dp[len][3]，表示第i个位置时对应满足题意时的最小操作次数
	则dp[i][0]表示第一部分（红色）；dp[i][1]表示第二部分（黄色）；dp[i][2]表示第三部分（红色）
	由题意，第一个位置的状态只能是红色，所以dp[0][0]=需要调整的次数，dp[0][1]=dp[0][2]=MAX_VALUE
	第二个位置不能是第三部分，所以dp[1][2]=MAX_VALUE
	接下来开始遍历每个位置：
		当处于第一部分时，最小操作数为上一位置的次数+当前操作次数（黄色需要改变）
		当处于第二部分时，最小操作数为上一位置的第一部分或第二部分取最小+当前操作次数（红色需要改变）
		当处于第三部分时（位置>1），最小操作数为上一位置的第二部分或第三部分取最小+当前操作次数（黄色需要改变）
	最后返回最后一个位置第三部分的最小次数即可
 */

class Solution {
    public int minimumOperations(String leaves) {
        char[] arr = leaves.toCharArray();
        int len = arr.length;

        int[][] dp = new int[len][3];
        
        // 定义初始态
        dp[0][0] = arr[0] == 'r' ? 0 : 1;
        dp[0][1] = dp[0][2] = Integer.MAX_VALUE;
        dp[1][2] = Integer.MAX_VALUE;

        for (int i = 1; i < len; i ++) {
            int isY = arr[i] == 'y' ? 1 : 0;
            int isR = 1 - isY;
            dp[i][0] = dp[i - 1][0] + isY;
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + isR;
            if (i > 1) {
                dp[i][2] = Math.min(dp[i - 1][1], dp[i - 1][2]) + isY;
            }
        } 

        return dp[len - 1][2];
    }
}