package com.example.talk.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/7 11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expires;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("session_key")
    private String sessionKey;

    @JsonProperty("session_secret")
    private String sessionSecret;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error")
    private String error;
}
