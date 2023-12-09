package com.dgmoonlabs.psythinktank.domain.stock.vo;

import java.util.List;

public record ChartDataset(
        String holderName,
        List<Double> values
) {
    public ChartDataset(final String holderName, final List<Double> values) {
        this.holderName = holderName;
        this.values = List.copyOf(values);
    }
}
