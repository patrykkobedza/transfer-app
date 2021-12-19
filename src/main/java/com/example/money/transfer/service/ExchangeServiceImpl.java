package com.example.money.transfer.service;

import com.example.money.transfer.dto.OpenErApiResponseDTO;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.repository.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    private static final String EXCHANGE_API_URL = "https://open.er-api.com/v6/latest/";

    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeServiceImpl(RestTemplate restTemplate, ExchangeRateRepository exchangeRateRepository) {
        this.restTemplate = restTemplate;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {
        try {
            if (sourceCurrency.equals(targetCurrency)) {
                return BigDecimal.ONE;
            }
            Map<String,Object> responseMap = restTemplate.getForObject(EXCHANGE_API_URL + sourceCurrency, Map.class);
            OpenErApiResponseDTO responseDTO = new OpenErApiResponseDTO(responseMap);
            logger.info("Fetched exchange rates from external api: " + responseDTO.toString());
            return BigDecimal.valueOf(((Number) responseDTO.getRates().get(targetCurrency)).doubleValue());
        } catch (Exception e) {    // In case of failure with geting data from external service will fetch fixed exchange rate from in-memory h2 DB.
            logger.warn("Error while fetching data from external api.. " + e.getMessage());
            logger.info("Will fetch data from internal db");
            return exchangeRateRepository.findBySourceCodeAndTargetCode(sourceCurrency.toUpperCase(), targetCurrency.toUpperCase())
                    .orElseThrow(() -> new NotFoundException("Unsupported Currency"))
                    .getRate();
        }
    }

}
