package ua.ai_assist.ai_assist.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ua.ai_assist.ai_assist.api.AssistantCommandGeneratorCli;
import ua.ai_assist.ai_assist.api.AssistantHelpCli;


@Slf4j
@Component
@RequiredArgsConstructor
@CommandLine.Command(name = "api", mixinStandardHelpOptions = true, description = "Console AI assistant",
        version = "0.1.0", synopsisSubcommandLabel = "COMMAND",
        subcommands = {
                AssistantHelpCli.class,
                AssistantCommandGeneratorCli.class
        })
public class ApiCommands {

}