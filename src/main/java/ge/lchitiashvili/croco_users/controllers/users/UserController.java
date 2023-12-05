package ge.lchitiashvili.croco_users.controllers.users;

import ge.lchitiashvili.croco_users.dtos.security.AuthorityDTO;
import ge.lchitiashvili.croco_users.dtos.security.UserDTO;
import ge.lchitiashvili.croco_users.models.security.User;
import ge.lchitiashvili.croco_users.services.authorities.AuthorityService;
import ge.lchitiashvili.croco_users.services.users.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;
    private final AuthorityService authorityService;

    @GetMapping
    public List<UserDTO> search(@RequestParam(required = false) String privateNumber,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) Boolean onlyActive
    ) {
        return userService.ENTITY_DTO_List(userService.search(privateNumber, email,onlyActive, User.Type.USER));
    }

    @DeleteMapping("{id}")
    public UserDTO delete(@PathVariable long id) {
        return userService.ENTITY_DTO(userService.delete(id));
    }

    @GetMapping("{id}")
    public UserDTO get(@PathVariable long id) {
         return userService.ENTITY_DTO(userService.get(id));
    }

    @PutMapping("{id}")
    public UserDTO edit(@PathVariable long id, @RequestBody User user) {
        return userService.ENTITY_DTO(userService.edit(id, user));
    }

    @PostMapping
    public UserDTO save(@RequestBody User user) {
        return userService.ENTITY_DTO(userService.addNew(user));
    }

    @PostMapping("{id}/authority")
    public void addAuthorityToUser(@PathVariable long id,@RequestParam long authorityId){
            userService.addUserAuthority(id,authorityId);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestParam String oldPassword,@RequestParam String newPassword){
        userService.changePassword(oldPassword,newPassword);
    }


    @GetMapping("{id}/authority")
    @PreAuthorize("hasAuthority('USER_AUTHORITY')")
    public List<AuthorityDTO> getUserAuthorities(@PathVariable long id){
        return authorityService.ENTITY_DTO_List(userService.getUserAuthorities(id));
    }
}
