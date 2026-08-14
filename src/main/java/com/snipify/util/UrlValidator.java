package com.snipify.util;
import java.net.URI;
public final class UrlValidator {private UrlValidator(){} public static boolean isValid(String v){if(v==null||v.isBlank()||v.length()>2048)return false;try{var u=URI.create(v.trim());return ("http".equalsIgnoreCase(u.getScheme())||"https".equalsIgnoreCase(u.getScheme()))&&u.getHost()!=null&&u.getUserInfo()==null;}catch(Exception e){return false;}}}
