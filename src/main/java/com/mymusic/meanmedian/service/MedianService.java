package com.mymusic.meanmedian.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MedianService {

    public double calculateMedian(final List<Double> values) {
        final List<Double> sortedValues = new ArrayList<>(values);
        Collections.sort(sortedValues);

        final int middle = sortedValues.size() / 2;
        if (sortedValues.size() % 2 == 1) {
            return sortedValues.get(middle);
        } else {
            return (sortedValues.get(middle - 1) + sortedValues.get(middle)) / 2;
        }
    }
}
