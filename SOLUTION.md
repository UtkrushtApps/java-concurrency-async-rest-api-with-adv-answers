# Solution Steps

1. Refactor the NotificationService to use a thread-safe AtomicInteger counter, and ensure all mutable shared state is thread-safe. Use @Async with a custom ThreadPoolTaskExecutor for async jobs.

2. Implement a configuration class (AsyncConfig) with @EnableAsync and a Bean defining a tunable ThreadPoolTaskExecutor (core/max pool size, queue capacity, thread prefix, and graceful shutdown).

3. Update NotificationController to return 202 response for async request intake, add /stats endpoint for monitoring processed count, and a /healthz endpoint for container readiness/liveness.

4. Tighten the NotificationRequest model as a simple POJO.

5. Configure application.properties for file-based logging, server port, and Spring Boot graceful shutdown.

6. Rewrite the Dockerfile to use a slim OpenJDK 17 JRE Alpine image, create a non-root user, minimize layers, move log directory, use HEALTHCHECK for /healthz, and productionize JVM constraints.

7. In docker-compose.yml, set explicit resource limits and reservations, improved logging options, map persistent log volumes, implement service healthchecks, and expose the API port.

8. Make sure all async jobs are handled by the custom executor, avoid blocking I/O on main threads, and surface enough process telemetry for operations.

9. Validate the improved image is much smaller and builds rapidly, and confirm the compose stack is resilient, quick to restart, and logs centrally.

10. Test concurrency and load, confirming response times improve, thread leakage is prevented, and resource usage is within Docker set limits.

