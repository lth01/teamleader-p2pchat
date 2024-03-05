package com.servercommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.util.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.message.Message;
import com.message.ResultResponse;

public class HttpConnection {

    private static HttpURLConnection httpURLConnection = null;
    private static PrintWriter wr = null;
    private static BufferedReader br = null;

    // 생성자 사용 방지
    private HttpConnection() {}

    /**
     * <p>Key-Value 형식의 데이터를 Json 포멧으로 변경 후 이 데이터를 서버 URL로 POST Request를 보냅니다.</p>
     * <p>만약 전송 도중 문제가 생겼을 경우 "Error"와 "Error 내용"을 Key-Value로 가지는 String을 반환합니다.</p>
     * <p>전송이 성공적으로 이루어졌다면 서버의 Response를 String으로 반환합니다.</p>
     *
     * @param serverURL POST Method로 Request를 보내고자 하는 서버의 URL
     * @param data      보내고자 하는 데이터
     * @return 서버 Response String
     */
    public static String sendPOSTRequest(URL serverURL, Message data) {
        String response = null;

        try {
            // HttpURLConnection 객체 기본 설정 및 서버로 데이터를 보내기 위한 PrintWriter 객체 생성
            setFieldsPOST(serverURL);

            // 서버로 데이터 전송 후 PrintWriter 닫음
            wr.write(JsonParser.convertObjectToJsonString(data));
            wr.close();

            // Http 통신이 원활하게 이루어지지 않았다면 예외 발생
            if (!isConnectOk()) {
                throw new IOException("Http Status Code: " + httpURLConnection.getResponseMessage());
            }

            // 서버의 응답을 받기 위한 BufferedReader 객체 생성
            br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));

            // 서버의 Response를 Json으로 만듬
            response = getResponse();
        } catch (IOException e) {
            // 만약 도중 문제가 생겼다면 { "Error": e.getMessage() } 로 Json을 만든다.
            response = "{ \"status\": \"" + e.getMessage() +"\" }";
        } finally {
            // 사용된 BufferedReader 객체를 닫고 Disconnect한다.
            try {
                closeFields();
            } catch (IOException ignored) {
            }
        }

        return response;
    }

    /**
     * <p>서버의 URL로 GET Request를 보냅니다.</p>
     * <p>만약 전송 도중 문제가 생겼을 경우 "Error"와 "Error 내용"을 Key-Value로 가지는 String을 반환합니다.</p>
     * <p>전송이 성공적으로 이루어졌다면 서버의 Response를 String으로 반환합니다.</p>
     *
     * @param serverURL GET Method로 Request를 보내고자 하는 서버의 URL
     * @return 서버 Response String
     */
    public static String sendGETRequest(URL serverURL) {
        String response = null;

        try {
            setFieldsGET(serverURL);
            httpURLConnection.connect();

            // Http 통신이 원활하게 이루어지지 않았다면 예외 발생
            if (!isConnectOk()) {
                throw new IOException("Http Status Code: " + httpURLConnection.getResponseMessage());
            }

            // 서버의 응답을 받기 위한 BufferedReader 객체 생성
            br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));

            response = getResponse();
        } catch (IOException e) {
            // 만약 도중 문제가 생겼다면 { "Error": e.getMessage() } 로 Json을 만든다.
            response = "{ \"status\": \"" + e.getMessage() +"\" }";
        } finally {
            // 사용된 BufferedReader 객체를 닫고 Disconnect한다.
            try {
                closeFields();
            } catch (IOException ignored) {
            }
        }

        return response;
    }

    // HttpURLConnection 객체 기본 설정 및 서버로 데이터를 보내기 위한 PrintWriter 객체 생성 메소드
    // 인자로 받은 URL로 Connection을 열고 POST Method로 설정한다.
    // HTTP Header로 Content-Type과 Accept, Accept-Encoding을 설정하고
    // Connect Timeout은 5초로 설정한다.
    // POST로 데이터를 보내주기 위해 doOutput 필드를 true로 설정한다.
    private static void setFieldsPOST(URL serverURL) throws IOException {
        httpURLConnection = (HttpURLConnection) serverURL.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Accept-Encoding", "UTF-8");
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setDoOutput(true);

        wr = new PrintWriter(
            new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8),
            true);
    }

    // HttpURLConnection 객체 기본 설정 메소드
    // 인자로 받은 URL로 Connection을 열고 GET Method로 설정한다.
    // HTTP Header로 Content-Type과 Accept, Accept-Encoding을 설정하고
    // Connect Timeout은 5초로 설정한다.
    private static void setFieldsGET(URL serverURL) throws IOException {
        httpURLConnection = (HttpURLConnection) serverURL.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Accept-Encoding", "UTF-8");
        httpURLConnection.setConnectTimeout(5000);
    }

    // 다 사용된 객체를 닫고 Disconnect하는 메소드
    private static void closeFields() throws IOException {
        httpURLConnection.disconnect();
        br.close();
    }

    // 서버에게 받은 Response를 String으로 반환하는 메소드
    private static String getResponse() throws JsonProcessingException {
        StringBuffer response = new StringBuffer();

        try {
            String responseLine = null;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            response.delete(0, response.length() - 1);
            response.append("\"Error\": \"").append(e.getMessage()).append("\"");
        }

        return response.toString();
    }

    private static boolean isConnectOk() throws IOException {
        int statusCode = httpURLConnection.getResponseCode();
        return (statusCode == HttpURLConnection.HTTP_OK
            || statusCode == HttpURLConnection.HTTP_NOT_MODIFIED);
    }

    private static ResultResponse handleException(Exception e) {
        return new ResultResponse(e.getMessage());
    }
}
