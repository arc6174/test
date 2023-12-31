package com.softeem.crm.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static  boolean isMobile(String phone){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        // 验证手机号
        String s2="^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";
        if(StringUtils.isNotBlank(phone)){
            p = Pattern.compile(s2);
            m = p.matcher(phone);
            b = m.matches();
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println(isMobile("19041449876"));
    }
}
