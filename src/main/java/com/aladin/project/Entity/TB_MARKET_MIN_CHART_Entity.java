package com.aladin.project.Entity;

import com.aladin.project.DTO.TB_MARKET_MIN_CHART_DTO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Table(name="TB_MARKET_MIN_CHART")
@Builder(builderMethodName = "TB_MARKET_MIN_CHART_EntityBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class TB_MARKET_MIN_CHART_Entity {
    @Id
    @Column(name = "market")
    private String market;

    @Column(name = "opening_price")
    private String opening_price;

    @Column(name = "rgstrn_dtm")
    @CreatedDate
    private Date rgstrn_dtm;

    public static TB_MARKET_MIN_CHART_EntityBuilder builder(TB_MARKET_MIN_CHART_DTO minChartDto) {
        return TB_MARKET_MIN_CHART_EntityBuilder()
                .market(minChartDto.getMarket())
                .opening_price(minChartDto.getOpening_price())
                .rgstrn_dtm(minChartDto.getRgstrn_dtm());
    }
}
