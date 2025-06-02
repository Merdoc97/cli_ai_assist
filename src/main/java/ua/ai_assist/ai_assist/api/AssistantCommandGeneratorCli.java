package ua.ai_assist.ai_assist.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Map;

@Component
@CommandLine.Command(name = "ai-cmd", mixinStandardHelpOptions = true,
        description = "current command created for generating command by openai")
@RequiredArgsConstructor
@Slf4j
public class AssistantCommandGeneratorCli implements Runnable {
    private final String promt = """
            Please generate command for description <description>. 
            Response should be as in console format.
            Response should contains example.
            """;
    private final ChatModel chatModel;
    @CommandLine.Option(names = {"-d", "--description"}, required = true, description = "description of help")
    private String command;

    @Override
    public void run() {
        log.debug("command: {}", command);
        var template = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .template(promt)
                .build();

        var response = chatModel.call(
                template.render(Map.of("description", command))
        );
        System.out.println(response);
    }
}
