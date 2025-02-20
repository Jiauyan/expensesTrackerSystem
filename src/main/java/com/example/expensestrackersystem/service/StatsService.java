package com.example.expensestrackersystem.service;

import com.example.expensestrackersystem.dto.GraphDTO;
import com.example.expensestrackersystem.dto.StatsDTO;

public interface StatsService {

    GraphDTO getChartData(Integer userId);

    StatsDTO getStats(Integer userId);
}
