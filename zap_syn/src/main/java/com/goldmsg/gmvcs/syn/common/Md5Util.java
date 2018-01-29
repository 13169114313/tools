package com.goldmsg.gmvcs.syn.common;

/**
 * @author avx
 * 采用md5加密 确保数据安全性
 */
public class Md5Util {
  public static String getMD5(String msg,String salt){
    return "";
  }

  /**
   * 测试
   * @param args
   */
  public static void main(String[] args) {
  String str= getMD5("123456","ceship");
   System.out.println(str);
  }
}
