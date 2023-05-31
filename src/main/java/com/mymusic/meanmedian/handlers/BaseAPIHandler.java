package com.mymusic.meanmedian.handlers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymusic.meanmedian.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public abstract class BaseAPIHandler<RequestType, ResponseType> implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private Validator validator;

    @Autowired
    private ObjectMapper objectMapper;

    // Pass in the class object to get around type erasure
    private final Class<RequestType> requestType;

    public BaseAPIHandler(final Class<RequestType> requestType) {
        this.requestType = requestType;
    }

    public abstract ResponseType handleRequest(RequestType request);

    @Override
    public APIGatewayProxyResponseEvent apply(final APIGatewayProxyRequestEvent event) {
        try {
            // Parse JSON request body
            final RequestType request = objectMapper.readValue(event.getBody(), requestType);

            // Request Validation
            final Set<ConstraintViolation<RequestType>> violations = validator.validate(request);

            if (violations != null && violations.size() > 0) {
                // Collect error messages
                final List<String> errors = violations
                        .stream()
                        .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
                        .toList();

                // Return bad request error
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(400)
                        .withBody(objectMapper.writeValueAsString(new ErrorResponse(errors)));
            }

            // Run the business logic of the handler
            final ResponseType response = this.handleRequest(request);

            // Wrap the response
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(objectMapper.writeValueAsString(response));

        } catch (final JsonMappingException exception) {
            return buildError(400, new ErrorResponse(List.of("Failed to deserialize JSON request body")));
        } catch (JsonProcessingException e) {
            return buildError(500, new ErrorResponse(List.of("Failed to serialize JSON response body")));
        }
    }

    public APIGatewayProxyResponseEvent buildError(final int statusCode, final ErrorResponse errors) {
        final APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withStatusCode(statusCode);

        // Add error body and handle potential exception
        try {
            final String responseBody = objectMapper.writeValueAsString(errors);
            response.withBody(responseBody);
        } catch (JsonProcessingException e) {
            // Handle error
        }

        return response;
    }
}
