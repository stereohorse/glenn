import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG

appender("Console", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d %-5p %logger{10} %msg%n"
    }
}

root(DEBUG, ["Console"])
