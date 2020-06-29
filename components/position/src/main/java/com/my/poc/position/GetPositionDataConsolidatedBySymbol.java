package com.my.poc.position;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@RequiredArgsConstructor
public class GetPositionDataConsolidatedBySymbol {

    private final PositionStore positionStore;

    public Flux<PositionSymbolData> execute(List<String> accountIds) {
        return Flux.fromIterable(accountIds)
                .flatMap(accountId -> positionStore.getPositions(accountId)
                .subscribeOn(Schedulers.elastic())
                .map(position -> mapToPositionSymbolData(position, accountId))
                ).collect(Collectors.groupingBy(positionSymbolData ->
                        Pair.of(positionSymbolData.getInstrument().getSymbol() != null ?
                                positionSymbolData.getInstrument().getSymbol() : positionSymbolData.getInstrument().getCusip(),
                                positionSymbolData.getQuantity().compareTo(BigDecimal.ZERO)),
                        Collectors.reducing(this::aggregatePositionSymbolData)))
                .flatMapIterable(Map::values)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(positionSymbolData -> positionSymbolData.toBuilder()
                    .purchasePrice(calculatePurchasePrice(positionSymbolData))
                    .gainLossPercent(calculateGainLossPercent(positionSymbolData))
                    .build());
    }

    private BigDecimal calculatePurchasePrice(PositionSymbolData positionSymbolData) {
        try {
            return positionSymbolData.getCostBasis().divide(positionSymbolData.getQuantity(), 2, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal calculateGainLossPercent(PositionSymbolData positionSymbolData) {
        try {
            return (positionSymbolData.getMarketValue().subtract(positionSymbolData.getCostBasis())).divide(positionSymbolData.getCostBasis(), 2, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }

    private PositionSymbolData aggregatePositionSymbolData(PositionSymbolData positionSymbolData1,
                                                           PositionSymbolData positionSymbolData2) {
        return PositionSymbolData.builder()
                .instrument(positionSymbolData1.getInstrument())
                .quantity(positionSymbolData1.getQuantity().add(positionSymbolData2.getQuantity()))
                .marketValue(positionSymbolData1.getMarketValue().add(positionSymbolData2.getMarketValue()))
                .price(positionSymbolData1.getPrice())
                .costBasis(positionSymbolData1.getCostBasis().add(positionSymbolData2.getCostBasis()))
                .gainLoss(positionSymbolData1.getGainLoss().add(positionSymbolData2.getGainLoss()))
                .accountPositions(concatenateAccountPositions(positionSymbolData1.getAccountPositions(), positionSymbolData2.getAccountPositions()))
                .build();
    }

    private List<AccountPosition> concatenateAccountPositions(List<AccountPosition> accountPositionList1, List<AccountPosition> accountPositionList2) {
        List concatenatedAccountPositionList = new ArrayList();
        concatenatedAccountPositionList.addAll(accountPositionList1);
        concatenatedAccountPositionList.addAll(accountPositionList2);
        return concatenatedAccountPositionList;
    }

    private PositionSymbolData mapToPositionSymbolData(Position position, String accountId) {
        return PositionSymbolData.builder()
                .instrument(position.getInstrument())
                .quantity(position.getQuantity())
                .marketValue(position.getMarketValue())
                .price(position.getPrice())
                .purchasePrice(position.getPurchasePrice())
                .costBasis(position.getCostBasis())
                .gainLoss(position.getGainLoss())
                .gainLossPercent(position.getGainLossPercent())
                .accountPositions(singletonList(AccountPosition.builder()
                        .accountId(accountId)
                        .position(position)
                        .build()))
                .build();
    }
}
