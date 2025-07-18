package br.com.seguro.frete.authorisation;

import br.com.seguro.frete.enums.Role;

public record LoginResponseDTO(
    String token,
    Long id,
    String name,
    String email,
    Role role
) {}
