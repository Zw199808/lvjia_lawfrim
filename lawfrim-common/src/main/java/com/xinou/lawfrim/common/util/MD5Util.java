package com.xinou.lawfrim.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: XGLLHZ
 * @date: 2020/5/27 下午5:25
 * @description:
 */
public class MD5Util {

    /**
     * 生成签名结果(新版本使用)
     *
     * @param sArray 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildMysignV1(Map<String, String> sArray,
                                       String secretKey) {
        String mysign = "";
        try {
            String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            prestr = prestr + "&secret_key=" + secretKey; // 把拼接后的字符串再与安全校验码连接起来
            mysign = getMD5String(prestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mysign;
    }

    /**
     * 生成签名结果（老版本使用）
     *
     * @param sArray 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildMysign(Map<String, String> sArray) {


        String mysign = "";
        try {
            mysign = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    //不排序
    public static String createLinkStringMax(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 生成32位大写MD5值
     */
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String getMD5String(String str) {
        try {
            if (str == null || str.trim().length() == 0) {
                return "";
            }
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
                        + HEX_DIGITS[bytes[i] & 0xf]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(MD5("123456"));
    }

    /**
     * 对传入的数据提取摘要
     *
     * @param buffer
     * @return
     */
    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


//    private static StringUtil2 chinese(String chinese){
//        StringUtil2 util = new StringUtil2();
//        char[]chars = chinese.toCharArray() ;
//        int index = 0 ;
//        StringBuffer buffer = new StringBuffer();
//        for(char c : chars){
//            String temp = String.valueOf(c) ;
//            if(temp.getBytes().length == 1){
//                util.map.put( index , temp ) ;
//            }else{
//                buffer.append( temp );
//            }
//            index++;
//        }
//        util.chinese = buffer.toString() ;
//        return util ;
//    }
//
//    public static String gbk2utf8(String chenese) {
//        StringUtil2 strUtil = chinese( chenese ) ;
//        char c[] = strUtil.chinese.toCharArray( ) ;
//        byte[] fullByte = new byte[3 * c.length];
//        for (int i = 0; i < c.length; i++) {
//            int m = (int) c[i];
//            String word = Integer.toBinaryString(m);
//
//            StringBuffer sb = new StringBuffer();
//            int len = 16 - word.length();
//            for (int j = 0; j < len; j++) {
//                sb.append("0");
//            }
//            sb.append(word);
//            sb.insert(0, "1110");
//            sb.insert(8, "10");
//            sb.insert(16, "10");
//
//            String s1 = sb.substring(0, 8);
//            String s2 = sb.substring(8, 16);
//            String s3 = sb.substring(16);
//
//            byte b0 = Integer.valueOf(s1, 2).byteValue();
//            byte b1 = Integer.valueOf(s2, 2).byteValue();
//            byte b2 = Integer.valueOf(s3, 2).byteValue();
//            byte[] bf = new byte[3];
//            bf[0] = b0;
//            fullByte[i * 3] = bf[0];
//            bf[1] = b1;
//            fullByte[i * 3 + 1] = bf[1];
//            bf[2] = b2;
//            fullByte[i * 3 + 2] = bf[2];
//        }
//        String reutrnStr = null ;
//        try {
//            reutrnStr = new String(fullByte, "UTF-8");
//        } catch (Exception e) {
//        }
//        StringBuffer returnBuffer = new StringBuffer(  reutrnStr );
//        for(Map.Entry<Integer, String> entry : strUtil.map.entrySet()){
//            returnBuffer.insert(entry.getKey() , entry.getValue() ) ;
//        }
//
//        return returnBuffer.toString() ;
//    }

}
