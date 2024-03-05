package com.servercommunicatorTest;

import com.message.ConnectMessage;
import com.message.CreateMessage;
import com.message.DisconnectMessage;
import com.message.Message;
import com.message.TalkMessage;
import com.servercommunication.HttpConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpConnectionTest {

    @DisplayName("POST Request 전송 메소드 테스트")
    @Test
    void sendPOSTRequestTest() throws MalformedURLException {
        URL testURL = new URL("https://webhook.site/0cc48f98-1e33-4428-aecd-b4671b53a667");
        List<Message> testData = List.of(new ConnectMessage("test1", "room1"),
            new CreateMessage("test2", "room2", 4),
            new DisconnectMessage("test3", "test3 disconnect"),
            new TalkMessage("test4", 5, "This is a test data"),
            new CreateMessage("test5", "room5", 0));

        testData.forEach(message -> {
            Assertions.assertDoesNotThrow(
                () -> HttpConnection.sendPOSTRequest(testURL, message));
        });
    }

    @DisplayName("GET Request 전송 메소드 테스트")
    @Test
    void sendGETRequestTest() throws MalformedURLException {
        URL testURL = new URL("https://webhook.site/0cc48f98-1e33-4428-aecd-b4671b53a667");

        for (int i = 0; i < 5; i++) {
            Assertions.assertDoesNotThrow(() -> HttpConnection.sendGETRequest(testURL));
        }
    }
}
