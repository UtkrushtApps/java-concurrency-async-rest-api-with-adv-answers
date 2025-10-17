FROM eclipse-temurin:17-jre-alpine AS base

WORKDIR /app

# Add a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Grant permission to log dir
RUN mkdir -p /var/log/notification-api && chown -R appuser:appgroup /var/log/notification-api

# Copy built jar
COPY --chown=appuser:appgroup build/libs/notification-api.jar app.jar

USER appuser
EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=3s --start-period=10s --retries=3 \
  CMD wget --spider -q http://localhost:8080/api/notifications/healthz || exit 1

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]
