package com.my.poc.springwebfluxreactiveprog;

import com.my.poc.position.GetConsolidatedSymbols;
import com.my.poc.position.GetPositionDataConsolidatedBySymbol;
import com.my.poc.position.PositionStore;
import com.my.poc.positionstoreimpl.PositionStoreImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    PositionStore positionStore() {
        return new PositionStoreImpl();
    }

    @Bean
    GetConsolidatedSymbols getConsolidatedSymbols(
            PositionStore positionStore
    ) {
        return new GetConsolidatedSymbols(positionStore);
    }

    @Bean
    GetPositionDataConsolidatedBySymbol getPositionDataConsolidatedBySymbol(
            PositionStore positionStore
    ) {
        return new GetPositionDataConsolidatedBySymbol(positionStore);
    }
}
