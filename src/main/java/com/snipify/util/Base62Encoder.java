package com.snipify.util;
public final class Base62Encoder {
 private static final char[] A="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
 private Base62Encoder(){}
 public static String encode(long n){if(n<0)throw new IllegalArgumentException("non-negative required");if(n==0)return "0";var b=new StringBuilder();while(n>0){b.append(A[(int)(n%62)]);n/=62;}return b.reverse().toString();}
}
