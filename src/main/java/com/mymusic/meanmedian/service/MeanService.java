package com.mymusic.meanmedian.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeanService {

    public double calculateMean(final List<Double> values) {
        final double sum = values.stream().reduce(0.0, Double::sum);
        return sum / values.size();
    }
}
