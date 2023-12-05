package ge.lchitiashvili.croco_users.controllers;

import ge.lchitiashvili.croco_users.config.util.JwtUtils;
import ge.lchitiashvili.croco_users.requests.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @GetMapping("/hello")
    public String welcome(){
        return "WELCOME";
    }


    @PostMapping("/logIn")
    public String logIn(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        String token=jwtUtils.generateToken(authRequest.getUserName());
        return token;
    }
}
