package com.snipify.handler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;import java.util.Map;
final class ApiResponses{private ApiResponses(){}static APIGatewayV2HTTPResponse json(int s,String b){return APIGatewayV2HTTPResponse.builder().withStatusCode(s).withHeaders(Map.of("content-type","application/json","access-control-allow-origin","*")).withBody(b).build();}}
