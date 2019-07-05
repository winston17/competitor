package com.yzc.bigdata.competitor.util.baseUtil;

public class IntegerUtil {

	/**
	 * 判断Integer 与 int 数值是否相等（null判断）
	 * @param param1--'Integer类型数据'
	 * @param param2--'int数值'
	 * @return
	 * @author 暴走的花哥
	 * @date 2018年2月27日10:49:51
	 */
	public static boolean IsIntegerEqualInt(Integer	param1,int param2) {
		if (param1==null) {
			return false;
		}
		if (param1==param2) {
			return true;
		}
		return false;
	}
	
	 /**
     * 数字转中文
     * 
     * @param string
     * @return
     * @author douqr 2018-01-16
     */
    public static String intToChinese(int param)
    {
        
        String string = Integer.toString(param);
        
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        
        StringBuffer result = new StringBuffer("");
        int n = string.length();
        for (int i = 0; i < n; i++)
        {
            int num = string.charAt(i) - '0';
            if (i != n - 1 && num != 0)
            {
                result.append(s1[num]).append(s2[n - 2 - i]);
            }
            else
            {
                result.append(s1[num]);
            }
        }
        return result.toString();
    }
    
}
