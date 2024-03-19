<configuration>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/${logger}.log</file> <!-- Dynamic log file name based on class name -->
        <encoder>
            <pattern>%date{ISO8601} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>