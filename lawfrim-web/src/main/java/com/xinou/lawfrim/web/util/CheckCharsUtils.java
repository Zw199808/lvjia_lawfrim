package com.xinou.lawfrim.web.util;

import io.swagger.models.auth.In;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckCharsUtils implements ConstraintValidator<CheckChars, String> {

    private CheckChars checkChars;

    @Override
    public void initialize(CheckChars constraintAnnotation) {
        this.checkChars =constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char hs = s.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (s.length() > 1) {
                    char ls = s.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!false && s.length() > 1 && i < s.length() - 1) {
                    char ls = s.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public static void main(String[] args) {
        String s = "☺☛selectinsert";
//        int len = s.length();
//        for (int i = 0; i < len; i++) {
//            char hs = s.charAt(i);
//            if (0xd800 <= hs && hs <= 0xdbff) {
//                if (s.length() > 1) {
//                    char ls = s.charAt(i + 1);
//                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
//                    if (0x1d000 <= uc && uc <= 0x1f77f) {
//                        System.out.println(true);
//                    }
//                }
//            } else {
//                // non surrogate
//                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
//                    System.out.println(true);
//                } else if (0x2B05 <= hs && hs <= 0x2b07) {
//                    System.out.println(true);
//                } else if (0x2934 <= hs && hs <= 0x2935) {
//                    System.out.println(true);
//                } else if (0x3297 <= hs && hs <= 0x3299) {
//                    System.out.println(true);
//                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
//                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
//                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
//                    System.out.println(true);
//                }
//                if (!false && s.length() > 1 && i < s.length() - 1) {
//                    char ls = s.charAt(i + 1);
//                    if (ls == 0x20e3) {
//                        System.out.println(true);
//                    }
//                }
//            }
//        }
//        String select =  "select";
//        String update =  "update";
//        String delete =  "delete";
//        String insert =  "insert";
//        System.out.println("****************"+s.contains(select));
//        System.out.println("****************"+s.contains(update));
//        System.out.println("****************"+s.contains(delete));
//        System.out.println("****************"+s.contains(insert));
//        System.out.println(false);
        String str="判断的字符串@";
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        System.out.println(m.find());
    }
}
