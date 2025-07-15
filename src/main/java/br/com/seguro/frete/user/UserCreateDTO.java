package br.com.seguro.frete.user;

import br.com.seguro.frete.enums.Role;

public record UserCreateDTO(
    Long id,
    String name,
    String email,
    String password,
    String phone,
    Role role
) {
    
}
