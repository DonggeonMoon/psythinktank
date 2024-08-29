package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Rating {
    A("A", 14, 10, 10),
    B("B", 9, 5, 5),
    C("C", 4, 0, 0),
    D("D", null, null, null);
    private final String grade;
    private final Integer boardStabilityCriteria;
    private final Integer growthPotentialCriteria;
    private final Integer governanceCriteria;

    public static String evaluateBoardStability(final Double boardStability) {
        if (boardStability == null) {
            return "-";
        }
        Figure figure = Figure.from(boardStability);
        if (figure.isGreaterOrEquals(A.boardStabilityCriteria)) {
            return A.grade;
        }
        if (Figure.from(boardStability).isGreaterOrEquals(B.boardStabilityCriteria)) {
            return B.grade;
        }
        if (Figure.from(boardStability).isGreaterOrEquals(C.boardStabilityCriteria)) {
            return C.grade;
        }
        return D.grade;
    }

    public static String evaluateGrowthPotential(final Double hrr) {
        if (hrr == null) {
            return "-";
        }
        Figure figure = Figure.from(hrr);
        if (figure.isGreaterOrEquals(A.governanceCriteria)) {
            return A.grade;
        }
        if (Figure.from(hrr).isGreaterOrEquals(B.growthPotentialCriteria)) {
            return B.grade;
        }
        if (Figure.from(hrr).isGreaterOrEquals(C.growthPotentialCriteria)) {
            return C.grade;
        }
        return D.grade;
    }

    public static String evaluateGovernance(final Double currentShare) {
        if (currentShare == null) {
            return "-";
        }
        Figure figure = Figure.from(currentShare);
        if (figure.isGreaterOrEquals(A.governanceCriteria)) {
            return A.grade;
        }
        if (Figure.from(currentShare).isGreaterOrEquals(B.governanceCriteria)) {
            return B.grade;
        }
        if (Figure.from(currentShare).isGreaterOrEquals(C.governanceCriteria)) {
            return C.grade;
        }
        return D.grade;
    }

    static class Figure {
        private final Double value;

        Figure(final Double value) {
            this.value = value;
        }

        public static Figure from(double value) {
            return new Figure(value);
        }

        public boolean isGreaterOrEquals(int number) {
            return value >= number;
        }
    }
}
