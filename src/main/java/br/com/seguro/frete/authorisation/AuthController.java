package br.com.seguro.frete.authorisation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.seguro.frete.user.User;
import br.com.seguro.frete.user.UserCreateDTO;
import br.com.seguro.frete.user.UserMapper;
import br.com.seguro.frete.user.UserRepository;
import br.com.seguro.frete.user.UserResponseDTO;
import br.com.seguro.frete.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticatorDTO DTO) {
        var email = new UsernamePasswordAuthenticationToken(DTO.email(), DTO.password());
        var authentication = authenticationManager.authenticate(email);
        var user = (User) authentication.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserCreateDTO DTO) {
        if (userRepository.findByEmail(DTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(DTO.password());
        User user = userService.createUser(DTO, encodedPassword);
        UserResponseDTO response = UserMapper.toResponseDTO(user);
        return ResponseEntity.ok(response);
    }

}