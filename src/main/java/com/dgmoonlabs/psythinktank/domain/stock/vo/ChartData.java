package com.dgmoonlabs.psythinktank.domain.stock.vo;

import java.util.List;

public record ChartData(
        List<String> labels,
        List<ChartDataset> chartDatasets
) {
    public ChartData(final List<String> labels, final List<ChartDataset> chartDatasets) {
        this.labels = List.copyOf(labels);
        this.chartDatasets = List.copyOf(chartDatasets);
    }
}
