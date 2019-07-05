package com.yzc.bigdata.competitor.util.baseUtil;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 判断结果
	 * @author douqr 2017-12-29
	 */
	public static Boolean isNullOrEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否全部是汉字 全部是汉字返回true,否则返回false
	 */
	public static boolean isAllChinese(String str) {
		if (str != null && str.length() > 0) {
			char[] strChar = str.toCharArray();
			for (int i = 0; i < strChar.length; i++) {
				if ((strChar[i] < 0x4e00) || (strChar[i] > 0x9fbb)) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * 字符串是否包含汉字
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		if (str != null && str.length() > 0) {
			char[] strChar = str.toCharArray();
			for (int i = 0; i < strChar.length; i++) {
				if ((strChar[i] >= 0x4e00) && (strChar[i] <= 0x9fbb)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否是单个汉字
	 */
	public static boolean isOneChinese(String str) {
		if (str != null) {
			char[] strChar = str.toCharArray();
			if ((strChar.length == 1) && (strChar[0] >= 0x4e00)
					&& (strChar[0] <= 0x9fbb)) {
				return true;
			}
		}

		return false;
	}
	
	 /* 计算两个词的相似度：编辑距离 */
		public static double levenshteinSimilarity(String leftWord,String rightWord) {

			if (leftWord == null) {
				if (rightWord != null) {
					return 0.0;
				} else {
					return 1.0;
				}
			}
			if (rightWord == null) {
				if (leftWord != null) {
					return 0.0;
				}
			}

			int leftLength = leftWord.length();// 左文本的长度
			int rightLength = rightWord.length();// 右文本的长度
			int maxLength = leftLength > rightLength ? leftLength : rightLength;// 两文本最大长度
			int[][] levenshteinMatrix = new int[leftLength + 1][rightLength + 1];// 矩阵

			if (leftLength == 0) {
				return rightLength;
			}
			if (rightLength == 0) {
				return leftLength;
			}

			int i;
			int j;
			int temp;
			char[] leftChars = leftWord.toCharArray();
			char[] rightChars = rightWord.toCharArray();
			char leftChar;
			char rightChar;
			for (i = 0; i <= leftLength; i++) { // 初始化第一列
				levenshteinMatrix[i][0] = i;
			}
			for (j = 0; j <= rightLength; j++) { // 初始化第一行
				levenshteinMatrix[0][j] = j;
			}
			for (i = 1; i <= leftLength; i++) { // 遍历str
				leftChar = leftChars[i - 1];
				// 去匹配target
				for (j = 1; j <= rightLength; j++) {
					rightChar = rightChars[j - 1];
					if (leftChar == rightChar) {
						temp = 0;
					} else {
						temp = 1;
					}
					int top = levenshteinMatrix[i - 1][j] + 1;
					int left = levenshteinMatrix[i][j - 1] + 1;
					int lt = levenshteinMatrix[i - 1][j - 1] + temp;
					// 左边+1,上边+1, 左上角+temp取最小
					int min = top > left ? left : top;
					levenshteinMatrix[i][j] = min > lt ? lt : min;
				}
			}
			return 1 - (double) levenshteinMatrix[leftLength][rightLength]
					/ maxLength;
		}
}
