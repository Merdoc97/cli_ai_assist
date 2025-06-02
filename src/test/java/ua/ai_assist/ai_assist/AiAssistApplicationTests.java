package ua.ai_assist.ai_assist;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
@Tag("local")
class AiAssistApplicationTests {

    @Autowired
    private ChatModel chatModel;

    @Test
    void contextLoads() {
        var response = chatModel.call(new Prompt(
                "Please give me a complete help to helm command, response should be as in console."
        ));
        System.out.println(response);
    }

    @Test
    void testWithRoleAsTool() {
        var response = chatModel.call(new Prompt(
                new SystemMessage("Please give me a complete help to helm command, response should be as in console.")
        ));
        System.out.println(response);
    }

    @Test
    void testPromptTemplate() {
        String strTemplate = """
                Please give me a complete help to <command> command, response should be as in console.
                """;
        var template = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .template(strTemplate)
                .build();
        var response = chatModel.call(
                template.render(Map.of("command", "kubectl"))
        );
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    void executeCommand() {
        var command="kubectl get namespaces";
        ProcessBuilder builder=new ProcessBuilder(command.split(" "));
        builder.redirectErrorStream(true);
        System.out.println("Command to execute: "+command);
        System.out.println("Are you sure to execute this command Y\\N");
        Scanner scanner=new Scanner(System.in);
        var answer=scanner.nextLine();
        scanner.close();
        if (answer.equalsIgnoreCase("Y")){
            var output=builder.start();
            var reader=new BufferedReader(new InputStreamReader(output.getInputStream()));
            reader.lines().forEach(System.out::println);
            output.waitFor();
            reader.close();
        }

    }
}
