package models.lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class AuthResponse {
    public String userId;
    public String username;
    public String password;
    public String token;
    public Date expires;
    @JsonProperty("created_date")
    public Date created_date;
    public boolean isActive;
}
