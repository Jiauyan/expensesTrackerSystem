package com.example.expensestrackersystem.controller;

import com.example.expensestrackersystem.dto.GraphDTO;
import com.example.expensestrackersystem.dto.StatsDTO;
import com.example.expensestrackersystem.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/chart/{userId}")
    public ResponseEntity<GraphDTO> getChartDetails(@PathVariable Integer userId) {
        GraphDTO graphData = statsService.getChartData(userId);
        return ResponseEntity.ok(graphData);
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<StatsDTO> getStats(@PathVariable Integer userId) {
        StatsDTO statsData = statsService.getStats(userId);
        return ResponseEntity.ok(statsData);
    }
}
