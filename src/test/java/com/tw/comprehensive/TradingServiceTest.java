package com.tw.comprehensive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TradingServiceTest {

    private AuditService auditService;
    private TradeRepository tradeRepository;
    private TradingService tradingService;
    private Trade trade;

    @BeforeEach
    public void init() {
        auditService = Mockito.mock(AuditService.class);
        tradeRepository = Mockito.mock(TradeRepository.class);
        tradingService = new TradingService(tradeRepository, auditService);
        trade = new Trade("reference", "description");
    }

    @Test
    public void should_log_new_trace_when_create_trade() {
        when(tradeRepository.createTrade(trade)).thenReturn(1L);

        tradingService.createTrade(trade);

        verify(auditService, times(1)).logNewTrade(trade);
    }

    @Test
    public void should_trade_equals_when_find_by_id_and_find_trade_given_id_1() {
        Long tradeId = 1L;

        when(tradeRepository.findById(tradeId)).thenReturn(trade);

        Trade actualTrade = tradingService.findTrade(1L);

        assertEquals(trade, actualTrade);
    }

    @Test
    public void should_id_equals_when_create_trade_in_trade_repository_and_trade_service() {

        Long exceptedTradeId = 1L;

        when(tradeRepository.createTrade(trade)).thenReturn(exceptedTradeId);

        Long actualTradeId = tradingService.createTrade(trade);

        verify(tradeRepository, times(1)).createTrade(trade);
        assertEquals(exceptedTradeId, actualTradeId);
    }

}
