package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class BaseLogger {

	// Returns a Logger instance for logging INFO messages
    public static Logger info() {
        return new Logger("application_log.txt", "INFO");
    }

    // Returns a Logger instance for logging ERROR messages
    public static Logger error() {
        return new Logger("application_error.txt", "ERROR");
    }

    // Logger class for writing log messages
    public static class Logger {
        private String logFile;
        private String logLevel;

        public Logger(String logFile, String logLevel) {
            this.logFile = logFile;
            this.logLevel = logLevel;
        }

        // Method to log a message
        public void log(String message) {
            String logEntry = String.format("[%s][%s] %s", LocalDateTime.now(), logLevel, message);
            writeLog(logEntry);
        }

     // Method to write the log entry to the log file
        private void writeLog(String logEntry) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(logEntry);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error writing to the log file:");
                e.printStackTrace();
            }
        }
    }
}

