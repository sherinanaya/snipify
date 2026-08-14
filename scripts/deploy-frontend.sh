#!/usr/bin/env bash
set -euo pipefail
STACK=${1:-snipify-dev}
BUCKET=$(aws cloudformation describe-stacks --stack-name "$STACK" --query "Stacks[0].Outputs[?OutputKey=='WebsiteBucketName'].OutputValue" --output text)
API=$(aws cloudformation describe-stacks --stack-name "$STACK" --query "Stacks[0].Outputs[?OutputKey=='ApiBaseUrl'].OutputValue" --output text)
printf 'window.SNIPIFY_CONFIG = { API_BASE_URL: "%s" };
' "$API" > frontend/config.js
aws s3 sync frontend/ "s3://$BUCKET" --exclude 'config.example.js' --delete
echo "Frontend uploaded to $BUCKET"
aws cloudformation describe-stacks --stack-name "$STACK" --query "Stacks[0].Outputs[?OutputKey=='WebsiteUrl'].OutputValue" --output text
