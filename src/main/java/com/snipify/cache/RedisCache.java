package com.snipify.cache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snipify.model.UrlMapping;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import java.time.Duration;
import java.util.Optional;
public final class RedisCache {
 private static final ObjectMapper JSON=new ObjectMapper();
 private final RedisClient client; private volatile StatefulRedisConnection<String,String> connection; private final int maxTtl;
 public RedisCache(String host,int port,int maxTtl){this.maxTtl=maxTtl;RedisURI uri=RedisURI.builder().withHost(host).withPort(port).withSsl(true).withVerifyPeer(true).withTimeout(Duration.ofSeconds(2)).build();this.client=RedisClient.create(uri);}
 private StatefulRedisConnection<String,String> connection(){var current=connection;if(current==null||!current.isOpen()){synchronized(this){current=connection;if(current==null||!current.isOpen())connection=current=client.connect();}}return current;}
 public Optional<UrlMapping> get(String code){try{String value=connection().sync().get("url:"+code);return value==null?Optional.empty():Optional.of(JSON.readValue(value,UrlMapping.class));}catch(Exception e){closeBrokenConnection();throw new CacheException(e);}}
 public void put(UrlMapping m){try{long remaining=m.expiresAt()-java.time.Instant.now().getEpochSecond();long ttl=Math.max(1,Math.min(maxTtl,remaining));connection().sync().setex("url:"+m.shortCode(),ttl,JSON.writeValueAsString(m));}catch(Exception e){closeBrokenConnection();throw new CacheException(e);}}
 private void closeBrokenConnection(){var c=connection;connection=null;if(c!=null){try{c.close();}catch(Exception ignored){}}}
 public static final class CacheException extends RuntimeException{public CacheException(Throwable t){super(t);}}
}
