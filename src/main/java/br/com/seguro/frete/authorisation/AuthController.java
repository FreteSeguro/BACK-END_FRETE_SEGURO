package br.com.seguro.frete.authorisation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.seguro.frete.user.User;
import br.com.seguro.frete.user.UserCreateDTO;
import br.com.seguro.frete.user.UserRepository;
import br.com.seguro.frete.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticatorDTO DTO) {
        var email = new UsernamePasswordAuthenticationToken(DTO.email(), DTO.password());
        var authentication = authenticationManager.authenticate(email);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserCreateDTO DTO) {
        if (userRepository.findByEmail(DTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(DTO.password());
        userService.createUser(DTO, encodedPassword);
        return ResponseEntity.ok().build();
    }
}