def byMilliSecond = timestamp("yyyyMMdd'T'HHmmss")

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "[%d] %-5level- %message%n"
    }
}

root(INFO, ["CONSOLE"])