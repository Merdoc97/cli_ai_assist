package ua.ai_assist.ai_assist;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Base64;

/**
 * you should have env var SPRING_AI_OPENAI_APIKEY= with valid token
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.ai.openai.image.options.model=dall-e-3",
        "spring.ai.openai.image.options.response-format=b64_json",
        "spring.ai.openai.image.options.width=1024",
        "spring.ai.openai.image.options.height=1024",
        "logging.level.org.springframework.ai=DEBUG",
        "spring.ai.retry.max-attempts=2"
})
@Tag("local")
class ImageModelToByteTest {

    @Autowired
    @Qualifier("openAiImageModel")
    private ImageModel imageModel;

    @Test
    @SneakyThrows
    void testImageGeneration() {
        var promt = """
                generate small cat
                """;

        var fileResult = new File(Path.of("/tmp/tts/image-test.jpeg").toUri());
        if (!fileResult.exists()) {
            fileResult.createNewFile();
        }
        var response = imageModel.call(new ImagePrompt(promt));
        var outStream = new FileOutputStream(fileResult);
        var base64Out=response.getResult().getOutput().getB64Json();
        outStream.write(Base64.getDecoder().decode(base64Out.getBytes()));
        outStream.close();
    }
}
