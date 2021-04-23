package com.antonbaeckelandt.debeziumtest.controllers;

import com.antonbaeckelandt.debeziumtest.statistics.StatisticsCalculator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsCalculator statisticsCalculator;

    @GetMapping("/statistics")
    @CrossOrigin
    public ResponseEntity statistics() throws JsonProcessingException {
        return new ResponseEntity<>(statisticsCalculator.getStatisticsAsJson(), HttpStatus.OK);
    }

}
