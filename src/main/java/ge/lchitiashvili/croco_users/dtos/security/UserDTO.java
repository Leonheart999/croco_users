package ge.lchitiashvili.croco_users.dtos.security;

import ge.lchitiashvili.croco_users.models.security.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private User.Type type;
    private String username;
    private String privateNumber;
    private String email;
    private Boolean active;
}
