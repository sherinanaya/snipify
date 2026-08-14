package com.snipify.util;
import java.security.SecureRandom;
public final class ShortCodeGenerator {private static final SecureRandom R=new SecureRandom();private static final long SPACE=3_521_614_606_208L;private ShortCodeGenerator(){} public static String generate(){return String.format("%7s",Base62Encoder.encode(R.nextLong(SPACE))).replace(' ','0');}}
