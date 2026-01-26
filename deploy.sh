#!/bin/bash

# Snowflake ID Generator Deployment Script
set -e

NAMESPACE=${NAMESPACE:-default}
RELEASE_NAME=${RELEASE_NAME:-snowflake-id-generator}
CHART_PATH="./snowflake-id-generator"

echo "🚀 Deploying Snowflake ID Generator to namespace: $NAMESPACE"

# Create namespace if it doesn't exist
kubectl create namespace $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# Install/upgrade the Helm chart
echo "📦 Installing Helm chart..."
helm upgrade --install $RELEASE_NAME $CHART_PATH \
  --namespace $NAMESPACE \
  --wait \
  --timeout 10m

echo "✅ Deployment completed!"

# Show status
echo ""
echo "📊 Deployment Status:"
kubectl get pods -n $NAMESPACE
kubectl get svc -n $NAMESPACE
kubectl get ingress -n $NAMESPACE

# Show access information
echo ""
echo "🌐 Access Information:"
if kubectl get ingress -n $NAMESPACE $RELEASE_NAME-snowflake-id-generator-ingress >/dev/null 2>&1; then
  echo "Ingress URL:"
  kubectl get ingress -n $NAMESPACE $RELEASE_NAME-snowflake-id-generator-ingress -o jsonpath='{.spec.rules[0].host}' | xargs -I {} echo "http://{}"
else
  SERVICE_TYPE=$(kubectl get svc -n $NAMESPACE $RELEASE_NAME-snowflake-id-generator-service -o jsonpath='{.spec.type}')
  if [ "$SERVICE_TYPE" = "NodePort" ]; then
    NODE_PORT=$(kubectl get svc -n $NAMESPACE $RELEASE_NAME-snowflake-id-generator-service -o jsonpath='{.spec.ports[0].nodePort}')
    NODE_IP=$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[?(@.type=="InternalIP")].address}')
    echo "NodePort URL: http://$NODE_IP:$NODE_PORT"
  fi
fi

echo ""
echo "🧪 Test the application:"
echo "curl http://<your-url>/v1/next-id"

echo ""
echo "📋 Useful commands:"
echo "kubectl get pods -n $NAMESPACE"
echo "kubectl logs -l app=snowflake-id-generator -n $NAMESPACE"
echo "helm status $RELEASE_NAME -n $NAMESPACE"
