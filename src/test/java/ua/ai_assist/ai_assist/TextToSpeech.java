package ua.ai_assist.ai_assist;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

/**
 * you should have env var SPRING_AI_OPENAI_APIKEY= with valid token
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.ai.openai.audio.speech.options.model=gpt-4o-mini-tts",
        "spring.ai.openai.audio.speech.options.voice=alloy",
        "spring.ai.openai.audio.speech.options.speed=1",
        "spring.ai.openai.audio.speech.options.response-format=mp3",

})
@Tag("local")
class TextToSpeech {
    @Autowired
    @Qualifier("openAiAudioSpeechModel")
    private SpeechModel speechModel;

    /**
     * cost for gpt-4o-mini-tts 0.6$ intput and 12$ output for 1M tokens
     */
    @Test
    @SneakyThrows
    void testTextToSpeech() {
        var promt = new SpeechPrompt("hi tell to me hello user");
        var fileResult = new File(Path.of("/tmp/tts/tts-test.mp3").toUri());
        if (!fileResult.exists()) {
            fileResult.createNewFile();
        }
        var response = speechModel.call(promt);
        var outStream = new FileOutputStream(fileResult);
        outStream.write(response.getResult().getOutput());
        outStream.close();

    }
}
