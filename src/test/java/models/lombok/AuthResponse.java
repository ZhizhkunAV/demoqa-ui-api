package models.lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class AuthResponse {
    private String userId;
    private String username;
    private String password;
    private String token;
    private String expires;
    @JsonProperty("created_date")
    private String createdDate;
    private String isActive;
}
