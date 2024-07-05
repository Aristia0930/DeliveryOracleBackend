package org.example.backend.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitVo {
    private String email;
    private LocalDate visitDate;
}
