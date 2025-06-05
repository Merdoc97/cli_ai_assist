package ua.ai_assist.ai_assist;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * you should have env var SPRING_AI_OPENAI_APIKEY= with valid token
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.ai.openai.image.options.model=dall-e-3",
        "spring.ai.openai.image.options.response-format=url",
        "spring.ai.openai.image.options.width=1024",
        "spring.ai.openai.image.options.height=1024",
        "logging.level.org.springframework.ai=DEBUG",
        "spring.ai.retry.max-attempts=2"
})
@Tag("local")
class ImageModelToUrlTest {

    @Autowired
    @Qualifier("openAiImageModel")
    private ImageModel imageModel;

    /**
     * example of result
     * https://oaidalleapiprodscus.blob.core.windows.net/private/org-xJXk6aMiDNKa0p21tmkGqmXS/user-RoHJK0zVP6Uwgj95MnRrRXbf/img-HvE4FbGJZWczbBg8jsUesnoH.png?st=2025-06-05T16%3A15%3A11Z&se=2025-06-05T18%3A15%3A11Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=cc612491-d948-4d2e-9821-2683df3719f5&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-06-04T22%3A21%3A15Z&ske=2025-06-05T22%3A21%3A15Z&sks=b&skv=2024-08-04&sig=bt4W9s7mvC1ve5A9NrM1vMHOWsiyjLlydwBTj2ejVSs%3D
     * https://oaidalleapiprodscus.blob.core.windows.net/private/org-xJXk6aMiDNKa0p21tmkGqmXS/user-RoHJK0zVP6Uwgj95MnRrRXbf/img-vzxyagWmRAQLbJF9kC42tPCx.png?st=2025-06-05T16%3A21%3A03Z&se=2025-06-05T18%3A21%3A03Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=cc612491-d948-4d2e-9821-2683df3719f5&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-06-05T17%3A21%3A03Z&ske=2025-06-06T17%3A21%3A03Z&sks=b&skv=2024-08-04&sig=mAB8y/DMXBEa4zFMqo8/0bWbdD%2BvVBCzUXX%2BhilwFJA%3D
     * https://oaidalleapiprodscus.blob.core.windows.net/private/org-xJXk6aMiDNKa0p21tmkGqmXS/user-RoHJK0zVP6Uwgj95MnRrRXbf/img-3GioLI2pTvVar69QCKbgxY1l.png?st=2025-06-05T16%3A25%3A55Z&se=2025-06-05T18%3A25%3A55Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=cc612491-d948-4d2e-9821-2683df3719f5&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-06-04T22%3A05%3A58Z&ske=2025-06-05T22%3A05%3A58Z&sks=b&skv=2024-08-04&sig=e0YkWSRB%2BEUHJs/eMjlyqAgAiM4C3gfLcRFtYFVnaY8%3D
     * dall-e-3 cost 0.04$ per image
     */
    @Test
    void testImageGeneration() {
        var promt = """
                generate small cat
                """;
        var options=new OpenAiImageOptions();
        options.setN(1);
        options.setModel("dall-e-3");
        options.setResponseFormat("url");
        options.setWidth(1024);
        options.setHeight(1024);
        options.setUser("user");
        var response = imageModel.call(new ImagePrompt(promt,options));
        System.out.println(response.getResult().getOutput().getUrl());
    }
}
