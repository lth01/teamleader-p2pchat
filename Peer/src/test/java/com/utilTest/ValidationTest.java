package com.utilTest;

import com.util.Validation;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidationTest {

    @DisplayName("IP 주소 검사 메소드 테스트")
    @Test
    void validateIpTest() {
        List<String> IPs = List.of("1.1.1.1", "192.108.153.112", "aaa.bb.cc.dd",
            "1234.59678.3442.4937",
            "127.0.0.1", "255.255.255.255", "123.213.140.67", "ac.2f.12.f9");

        int result = (int) IPs.stream()
            .map(Validation::validateIp)
            .filter(e -> e)
            .count();

        Assertions.assertEquals(5, result);
    }
}
