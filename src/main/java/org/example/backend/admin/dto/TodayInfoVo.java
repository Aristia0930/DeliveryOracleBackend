package org.example.backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodayInfoVo {
    int orderCount;
    int visitorCount;
    int totalPriceSum;
}
