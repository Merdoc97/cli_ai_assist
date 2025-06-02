package ua.ai_assist.ai_assist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Map;

@Component
@CommandLine.Command(name = "ai-help", mixinStandardHelpOptions = true,
        description = "current command can help with any command which you need to help," +
                " it's analogue of man command but with examples")
@RequiredArgsConstructor
public class AssistantHelpCli implements Runnable {
    private final String promt = """
            Please give me a complete help to <command> command, response should be as in console.
            response contains examples of usage.
            """;
    private final ChatModel chatModel;
    @CommandLine.Option(names = {"-c", "--command"}, required = true, description = "command to help")
    private String command;

    @Override
    public void run() {
        var template = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .template(promt)
                .build();
        chatModel.stream(
                        template.render(Map.of("command", command)))
                .doOnNext(System.out::print)
                .then().block();
    }
}
