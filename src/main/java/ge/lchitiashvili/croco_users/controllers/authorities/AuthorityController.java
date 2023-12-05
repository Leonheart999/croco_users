package ge.lchitiashvili.croco_users.controllers.authorities;

import ge.lchitiashvili.croco_users.dtos.security.AuthorityDTO;
import ge.lchitiashvili.croco_users.models.security.Authority;
import ge.lchitiashvili.croco_users.services.authorities.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorities")
@PreAuthorize("hasAuthority('AUTHORITY')")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    List<AuthorityDTO> search(@RequestParam(required = false) String name){
            return authorityService.ENTITY_DTO_List(authorityService.search(name));
    }
}
