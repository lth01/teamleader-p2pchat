package com.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Input값에 대한 검증 메소드를 모아놓은 Util 클래스입니다.
 */
public class Validation {

    // 생성자 사용 방지
    private Validation() {
    }

    /**
     * IPv4 주소 형식이 맞는지 확인하는 validation 메소드
     *
     * @param ip IPv4 주소값
     * @return IPv4 주소 형식에 해당하면 True, 그렇지 않다면 False를 반환합니다.
     */
    public static boolean validateIp(String ip) {
        return Pattern.matches(
            "\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\b",
            ip);
    }
}
