package it.unicam.cs.hackhub.Application.DTOs;

import java.util.Set;

public record TeamDTO(Long id, String name, Set<String> members) {
}
