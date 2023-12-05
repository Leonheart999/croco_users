package ge.lchitiashvili.croco_users.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}
