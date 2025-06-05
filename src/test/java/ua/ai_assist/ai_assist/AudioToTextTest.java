package ua.ai_assist.ai_assist;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileUrlResource;
import org.springframework.test.context.TestPropertySource;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * you should have env var SPRING_AI_OPENAI_APIKEY= with valid token
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.ai.openai.audio.speech.options.model=gpt-4o-mini-transcribe"

})
@Tag("local")
class AudioToTextTest {
    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;


    @Test
    void testFileToText() throws MalformedURLException {
        var audioFile = Path.of("/tmp/tts/tts-test.mp3").toUri().toURL();
        AudioTranscriptionPrompt request = new AudioTranscriptionPrompt(new FileUrlResource(audioFile));
        AudioTranscriptionResponse response = transcriptionModel.call(request);
        Assertions.assertThat(response.getResult().getOutput())
                .containsIgnoringCase("hi")
                .containsIgnoringCase("tell")
                .containsIgnoringCase("to")
                .containsIgnoringCase("me")
                .containsIgnoringCase("hello")
                .containsIgnoringCase("user");

    }
}
