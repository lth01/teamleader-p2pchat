package com.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    protected Boolean isSuccess;    // 로직 성공 여부
    protected String message;       // 성공시 OK, 실패시 에러 메시지
}
