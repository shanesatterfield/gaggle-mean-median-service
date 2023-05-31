package com.mymusic.meanmedian.handlers;

import com.mymusic.meanmedian.dto.CalculationRequest;
import com.mymusic.meanmedian.dto.CalculationResponse;
import com.mymusic.meanmedian.service.MeanService;
import com.mymusic.meanmedian.service.MedianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeanMedianHandler extends BaseAPIHandler<CalculationRequest, CalculationResponse> {

    @Autowired
    private MeanService meanService;

    @Autowired
    private MedianService medianService;

    public MeanMedianHandler() {
        super(CalculationRequest.class);
    }

    @Override
    public CalculationResponse handleRequest(CalculationRequest request) {
        var values = request.values();
        return new CalculationResponse(
                meanService.calculateMean(values),
                medianService.calculateMedian(values)
        );
    }
}
