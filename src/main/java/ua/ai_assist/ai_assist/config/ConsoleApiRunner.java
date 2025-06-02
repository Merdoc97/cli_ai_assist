package ua.ai_assist.ai_assist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * common runner for all commands
 */
@Component
@RequiredArgsConstructor
public class ConsoleApiRunner implements CommandLineRunner, ExitCodeGenerator {
    private final CommandLine.IFactory factory;
    private final ApiCommands apiCommands;

    private int exitCode;

    @Override
    public void run(final String... args) {
        exitCode = new CommandLine(apiCommands, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
