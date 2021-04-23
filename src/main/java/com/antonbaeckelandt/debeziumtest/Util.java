package com.antonbaeckelandt.debeziumtest;

import java.time.LocalDate;
import java.time.Period;

public class Util {

    public static int calculateAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(dateOfBirth, today);
        return period.getYears();
    }

}
