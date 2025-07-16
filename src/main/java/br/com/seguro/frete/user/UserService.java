package br.com.seguro.frete.user;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public User createUser(UserCreateDTO dto, String encodedPassword) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public UserResponseDTO findUserById(Long id) {
        return UserMapper.toResponseDTO(findUserByIdOrThrow(id));
    }

    public void deleteUser(Long id) {
        User user = findUserByIdOrThrow(id);
        userRepository.delete(user);
    }
    
    public UserResponseDTO updateUser(Long id, UserCreateDTO dto) {
        User user = findUserByIdOrThrow(id);
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setRole(dto.role());
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
