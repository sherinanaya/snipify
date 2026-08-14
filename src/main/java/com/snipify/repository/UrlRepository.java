package com.snipify.repository;
import com.snipify.model.UrlMapping;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import java.util.*;
public final class UrlRepository {
 private final DynamoDbClient db; private final String table;
 public UrlRepository(DynamoDbClient db,String table){this.db=db;this.table=table;}
 public boolean create(UrlMapping m){var item=Map.of("shortCode",AttributeValue.fromS(m.shortCode()),"originalUrl",AttributeValue.fromS(m.originalUrl()),"createdAt",AttributeValue.fromS(m.createdAt()),"expiresAt",AttributeValue.fromN(m.expiresAt().toString()),"status",AttributeValue.fromS(m.status()));try{db.putItem(PutItemRequest.builder().tableName(table).item(item).conditionExpression("attribute_not_exists(shortCode)").build());return true;}catch(ConditionalCheckFailedException e){return false;}}
 public Optional<UrlMapping> find(String code){var r=db.getItem(GetItemRequest.builder().tableName(table).key(Map.of("shortCode",AttributeValue.fromS(code))).build());if(!r.hasItem())return Optional.empty();var i=r.item();return Optional.of(new UrlMapping(i.get("shortCode").s(),i.get("originalUrl").s(),i.get("createdAt").s(),Long.parseLong(i.get("expiresAt").n()),i.get("status").s()));}
}
