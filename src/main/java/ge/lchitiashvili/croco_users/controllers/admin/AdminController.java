package ge.lchitiashvili.croco_users.controllers.admin;

import ge.lchitiashvili.croco_users.dtos.security.UserDTO;
import ge.lchitiashvili.croco_users.models.security.User;
import ge.lchitiashvili.croco_users.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> search(@RequestParam(required = false) String privateNumber,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) Boolean onlyActive
    ) {
        return userService.ENTITY_DTO_List(userService.search(privateNumber, email,onlyActive, User.Type.ADMIN));
    }

}
