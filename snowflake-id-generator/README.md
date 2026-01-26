# Snowflake ID Generator Helm Chart

This Helm chart deploys the Snowflake ID Generator application with PostgreSQL database and Fluentd log collection.

## Prerequisites

- Kubernetes 1.19+
- Helm 3.0+
- Contour Ingress Controller (for ingress functionality)

## Installation

### Add the chart repository (if applicable)

```bash
helm repo add your-repo https://your-repo-url
helm repo update
```

### Install the chart

```bash
# Install with default values
helm install snowflake-id-generator ./snowflake-id-generator

# Install with custom values
helm install snowflake-id-generator ./snowflake-id-generator \
  --set postgresql.password=your-password \
  --set ingress.hosts[0].host=your-domain.com
```

## Configuration

The following table lists the configurable parameters of the Snowflake ID Generator chart and their default values.

| Parameter | Description | Default |
|-----------|-------------|---------|
| `replicaCount` | Number of application replicas | `1` |
| `image.repository` | Application image repository | `snowflake-id-generator` |
| `image.tag` | Application image tag | `"0.0.2"` |
| `image.pullPolicy` | Image pull policy | `IfNotPresent` |
| `service.type` | Service type | `NodePort` |
| `service.port` | Service port | `80` |
| `service.targetPort` | Target port | `8080` |
| `service.nodePort` | NodePort (when type is NodePort) | `30080` |
| `ingress.enabled` | Enable ingress | `true` |
| `ingress.className` | Ingress class name | `contour` |
| `ingress.hosts[0].host` | Ingress host | `snowflake.dev.local` |
| `autoscaling.enabled` | Enable autoscaling | `true` |
| `autoscaling.minReplicas` | Minimum replicas | `1` |
| `autoscaling.maxReplicas` | Maximum replicas | `3` |
| `postgresql.enabled` | Enable PostgreSQL | `true` |
| `postgresql.replicas` | PostgreSQL replicas | `2` |
| `postgresql.database` | Database name | `snowflake_id_generator_db` |
| `postgresql.username` | Database username | `postgres` |
| `postgresql.password` | Database password | `postgres` |
| `fluentd.enabled` | Enable Fluentd | `true` |
| `test.enabled` | Enable test resources | `false` |

## Components

This chart deploys the following components:

1. **Snowflake ID Generator Application**: Spring Boot application serving IDs
2. **PostgreSQL StatefulSet**: Database for ID generation state
3. **Fluentd DaemonSet**: Log collection from application pods (worker nodes only)
4. **Horizontal Pod Autoscaler**: Automatic scaling based on CPU/memory usage
5. **Ingress**: HTTP routing to the application
6. **Services**: Internal networking

## Usage

After installation, get the application URL:

```bash
# For ingress
kubectl get ingress

# For NodePort service
kubectl get svc snowflake-id-generator-service

# Port forward for local access
kubectl port-forward svc/snowflake-id-generator-service 8080:80
```

Access the API:
```bash
curl http://localhost:8080/v1/next-id
```

## Database Migration

The application uses Flyway for database migrations. Tables will be created automatically on startup.

## Monitoring

- Application health checks: `/management/health/liveness` and `/management/health/readiness`
- Metrics endpoint: `/management/metrics` (requires actuator)
- Logs are collected by Fluentd and output to stdout

## Security

- Database credentials are stored in Kubernetes secrets
- Consider enabling TLS for production deployments
- Configure proper RBAC for your cluster

## Troubleshooting

```bash
# Check pod status
kubectl get pods

# Check logs
kubectl logs -l app=snowflake-id-generator

# Check database connection
kubectl exec -it postgres-0 -- psql -U postgres -d snowflake_id_generator_db

# Check Fluentd logs
kubectl logs -l app=fluentd
```

## Uninstall

```bash
helm uninstall snowflake-id-generator

# Clean up PVCs if needed
kubectl delete pvc -l app=postgres
```
