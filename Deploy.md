# Snipify

## Introduction
A serverless URL-shortening platform using  using Base62 encoding, with ElasticCache caching to achieve low-latency URL redirection to reduce database lookups.

Given an original URL "https://example.com/products/cloud-platform", this application will generate a 7-letter shortCode like "8mK2xQ" and generate a short URL like "https://api-domain/8mK2xQ". 

Stack: Java, AWS Lambda, Amazon DynamoDB, Amazon ElastiCache Serverless, Amazon S3, Amazon API Gateway, Redis, REST APIs, HTML, CSS, JavaScript

## API Deffinition
- `POST /api/urls`: validates a URL, generates a seven-character Base62 code, and conditionally stores it.
- `GET /{shortCode}`: resolves an active, non-expired mapping and returns HTTP 302.
- `GET /api/urls/{shortCode}/statistics`: fetch URL metadata and return stats including total visit count & URL details

## Build and test
```bash
mvn clean test
sam validate
sam build
```

## Deploy
```bash
sam deploy --guided
```
Suggested first-deployment answers:
- Stack name: `snipify-dev`
- AWS Region: choose the intended region
- Parameter StageName: `dev`
- Confirm changes: `Y`
- Allow SAM CLI IAM role creation: `Y`
- Disable rollback: `N`
- Save arguments to configuration file: `Y`

Copy `CreateUrlEndpoint` and `ApiBaseUrl` from the stack outputs.

## Test
```bash
CREATE_URL=$(aws cloudformation describe-stacks --stack-name snipify-dev --query "Stacks[0].Outputs[?OutputKey=='CreateUrlEndpoint'].OutputValue" --output text)

curl -s -X POST "$CREATE_URL" -H "content-type: application/json" -d '{"originalUrl":"https://example.com/products/cloud","expiryDays":30}'
```
Copy `shortUrl` from the response and run:
```bash
curl -i "PASTE_SHORT_URL_HERE"
```
Expected result: HTTP 302 with a `location` header.

## View a saved mapping
```bash
TABLE_NAME=$(aws cloudformation describe-stacks --stack-name snipify-dev --query "Stacks[0].Outputs[?OutputKey=='UrlMappingsTableName'].OutputValue" --output text)
aws dynamodb scan --table-name "$TABLE_NAME" --max-items 10
```

## Redeploy changes
```bash
sam build
sam deploy
```

## Delete all Milestone 1 cloud resources
```bash
sam delete --stack-name snipify-dev
```

## Notes
- The generated short URL includes the configured API stage. A later milestone can replace this with a custom domain.
- DynamoDB TTL cleanup is asynchronous. The redirect handler independently enforces expiry.
