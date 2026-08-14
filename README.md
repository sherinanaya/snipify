# Snipify

## Introduction
A serverless URL-shortening platform using  using Base62 encoding, with ElasticCache caching to achieve low-latency URL redirection to reduce database lookups.

Given an original URL "https://example.com/products/cloud-platform", this application will generate a 7-letter shortCode like "8mK2xQ" and generate a short URL like "https://api-domain/8mK2xQ". 

Stack: Java, AWS Lambda, Amazon DynamoDB, Amazon ElastiCache Serverless, Amazon S3, Amazon API Gateway, Redis, REST APIs, HTML, CSS, JavaScript

## API Deffinition
- `POST /api/urls`: validates a URL, generates a seven-character Base62 code, and conditionally stores it.
- `GET /{shortCode}`: resolves an active, non-expired mapping and returns HTTP 302.
- `GET /api/urls/{shortCode}/statistics`: fetch URL metadata and return stats including total visit count & URL details