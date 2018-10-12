package com.bootdo.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author xumx
 * @date 2018/9/28
 */
public class ZipCodeUtils {

    private static Map<String, String> zipCodeMap = new HashMap<String, String>();

    static {
        File file = new File("ZipCode.txt");
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(" ");
                if (row.length == 2) {
                    zipCodeMap.put(row[0], row[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getZipCode(String province, String city) {
        if (city != null) {
            return zipCodeMap.get(city);
        } else if (province != null) {
            return zipCodeMap.get(province);
        }
        return "";
    }

    public static void main(String[] args) {
//        String address,province,city,district;
//        int index = 0;
//        address = "上海市上海市黄浦区淮海中路887号";
//        if(true) {
//            index = address.indexOf("省");
//            if (index < 0) {
//                index = address.indexOf("市");
//            }
//            province = address.substring(0, index + 1);
//            address = address.substring(index + 1);
//            index = address.indexOf("市");
//            city = address.substring(0, index + 1);
//            address = address.substring(index + 1);
//        }
//        index  = address.indexOf("区");
//        if(index<0){
//            index = address.indexOf("县");
//        }
//        district = address.substring(0,index+1);
//        System.out.println(province+city+district+"|"+address);
//    }
//        System.out.println(getZipCode(null, null, "徐汇区"));
//
        String a = "广西壮族自治区12345678";
        System.out.println(a.substring(-8));
    }
}