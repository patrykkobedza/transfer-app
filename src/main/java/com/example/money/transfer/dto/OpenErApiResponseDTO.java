package com.example.money.transfer.dto;

import java.util.Map;

public class OpenErApiResponseDTO {

    private final String result;
    private final String baseCode;
    private final String errorType;
    private final Map<String, Double> rates;

    public OpenErApiResponseDTO(Map<String, Object> responseMap) {
        this.result = (String) responseMap.get("result");
        this.baseCode = (String) responseMap.get("base_code");
        this.errorType = (String) responseMap.get("error-type");
        this.rates = (Map<String, Double>) responseMap.get("rates");
    }

    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "OpenErApiResponseDTO{" +
                "result=" + result +
                ", baseCode='" + baseCode + '\'' +
                ", errorType='" + errorType + '\'' +
                ", rates=" + rates +
                '}';
    }

}
