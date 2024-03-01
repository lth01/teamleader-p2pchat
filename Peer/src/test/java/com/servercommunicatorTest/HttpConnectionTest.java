package com.servercommunicatorTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.servercommunication.HttpConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpConnectionTest {

    @DisplayName("POST Request 전송 메소드 테스트")
    @Test
    void sendPostRequestTest() throws MalformedURLException {
        URL testURL = new URL("https://webhook.site/0cc48f98-1e33-4428-aecd-b4671b53a667");
        Map<String, String> testData = new HashMap<>();
        testData.put("Test_ID", "10");
        testData.put("Test_text", "This is Test Text");
        testData.put("Test_List", List.of(1, 2, 3, 4, 5).toString());

        Assertions.assertDoesNotThrow(() -> {
            JsonNode response = HttpConnection.sendPostRequest(testURL, testData);
        });
    }
}
