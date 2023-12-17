package com.dgmoonlabs.PSYThinktank.global.constant;

import com.dgmoonlabs.psythinktank.global.constant.Rating;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RatingTest {
    public static final double BOARD_STABILITY_A_CASE = 14.0;
    public static final double BOARD_STABILITY_B_CASE = 13.9;
    public static final double HRR_A_CASE = 10.0;
    public static final double HRR_B_CASE = 9.9;
    public static final double SHARE_A_CASE = 10.0;
    public static final double SHARE_B_CASE = 9.9;

    @Test
    void evaluateBoardStability() {
        assertThat(Rating.evaluateBoardStability(BOARD_STABILITY_A_CASE))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void evaluateBoardStability2() {
        assertThat(Rating.evaluateBoardStability(BOARD_STABILITY_B_CASE))
                .isEqualTo(Rating.B.getGrade());
    }

    @Test
    void evaluateGrowthPotential() {
        assertThat(Rating.evaluateGrowthPotential(HRR_A_CASE))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void evaluateGrowthPotential2() {
        assertThat(Rating.evaluateGrowthPotential(HRR_B_CASE))
                .isEqualTo(Rating.B.getGrade());
    }

    @Test
    void evaluateGovernance() {
        assertThat(Rating.evaluateGovernance(SHARE_A_CASE))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void evaluateGovernance2() {
        assertThat(Rating.evaluateGovernance(SHARE_B_CASE))
                .isEqualTo(Rating.B.getGrade());
    }
}