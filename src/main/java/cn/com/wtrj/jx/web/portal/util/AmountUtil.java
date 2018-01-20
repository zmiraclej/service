package cn.com.wtrj.jx.web.portal.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/** 
 * com.util.AmountUtils 
 * @description  金额元分之间转换工具类 
 */  
public class AmountUtil {  
    /**金额为分的格式 */  
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";  
      
    /**  
     * 将分为单位的转换为元并返回金额格式的字符串 （除100） 
     *  
     * @param amount 
     * @return 
     * @throws Exception  
     */  
    public static String changeF2Y(Long amount) throws Exception{  
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {  
            throw new Exception("金额格式有误");  
        }  
          
        int flag = 0;  
        String amString = amount.toString();  
        if(amString.charAt(0)=='-'){  
            flag = 1;  
            amString = amString.substring(1);  
        }  
        StringBuffer result = new StringBuffer();  
        if(amString.length()==1){  
            result.append("0.0").append(amString);  
        }else if(amString.length() == 2){  
            result.append("0.").append(amString);  
        }else{  
            String intString = amString.substring(0,amString.length()-2);  
            for(int i=1; i<=intString.length();i++){  
                if( (i-1)%3 == 0 && i !=1){  
                    result.append(",");  
                }  
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));  
            }  
            result.reverse().append(".").append(amString.substring(amString.length()-2));  
        }  
        if(flag == 1){  
            return "-"+result.toString();  
        }else{  
            return result.toString();  
        }  
    }  
      
    /** 
     * 将分为单位的转换为元 （除100） 
     *  
     * @param amount 
     * @return 
     * @throws Exception  
     */  
    public static String changeF2Y(String amount) throws Exception{  
        if(!amount.matches(CURRENCY_FEN_REGEX)) {  
            throw new Exception("金额格式有误");  
        }  
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();  
    }  
      
    /**  
     * 将元为单位的转换为分 （乘100） 
     *  
     * @param amount 
     * @return 
     */  
    public static String changeY2F(Long amount){  
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();  
    }  
      
    /**  
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额 
     * 元最多两位小数，超过的部分会被舍去。
     * 返回的是长整型
     * @param amount 
     * @return 
     */  
    public static String changeY2F(String amount){  
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){//小数点的索引和总长度差值>=3，表示有两位以上小数，保留两位小数
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){//长度-小数点索引为2，表示一位小数，去掉小数点，末尾填0
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString();
    }
    
    
    /**
     * 格式化价格，价格保留两位小数(200.00数据库查询结果为200，页面需要展示为200.00)
     * @param price 待格式化的数据
     * @return 格式化后的两位小数金额
     */
    public static BigDecimal formatPrice(BigDecimal price){
    	DecimalFormat decimalFormat=new DecimalFormat("#.00");
    	return new  BigDecimal(decimalFormat.format(price))	;
    }
      
    public static void main(String[] args) {
    	BigDecimal b1=new BigDecimal("20");
    	BigDecimal b2=new BigDecimal("20.00");
    	System.out.println(b1.compareTo(b2));
    	/*OrderPreviewBean b=new OrderPreviewBean();
    	b.setTotalMoney(formatPrice(new BigDecimal(0)));
    	System.out.println(b.getTotalMoney());*/
    	
//        try {  
//            System.out.println("结果："+changeF2Y("-000a00"));  
//        } catch(Exception e){  
//            System.out.println("----------->>>"+e.getMessage());  
////          return e.getErrorCode();  
//        }   
//      System.out.println("结果："+changeY2F("1.00000000001E10"));  
//        System.out.println(AmountUtil.changeY2F(1203L));
//        System.out.println(AmountUtil.changeY2F("122.22332"));  
//        try {
//			System.out.println(AmountUtil.changeF2Y("13222"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        System.out.println(Long.parseLong(AmountUtils.changeY2F("1000000000000000")));  
//        System.out.println(Integer.parseInt(AmountUtils.changeY2F("10000000")));  
//        System.out.println(Integer.MIN_VALUE);  
//        long a = 0;  
//        System.out.println(a);  
          
    }  
}  