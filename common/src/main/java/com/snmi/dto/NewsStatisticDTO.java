package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.Statistic;
import com.snmi.enums.StatisticAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to create some kind of statistic for specific news
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "News statistic data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsStatisticDTO {

    @ApiModelProperty(
            name = "Statistic news id which will be created for specific news",
            access = "WRITE_ONLY",
            example = "6",
            required = true
    )
    @NotNull(message = "Statistic must be related to some news")
    private Long newsId;

    @ApiModelProperty(name = "Statistic type which you want to create", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Statistic must has a type")
    private Statistic statistic;

    @ApiModelProperty(name = "Statistic action. Create new or delete specific statistic", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Statistic must has a action")
    private StatisticAction statisticAction;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public StatisticAction getStatisticAction() {
        return statisticAction;
    }

    public void setStatisticAction(StatisticAction statisticAction) {
        this.statisticAction = statisticAction;
    }
}
