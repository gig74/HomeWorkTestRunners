package ru.productstar.testrunners;

public enum TransitServiceWorkload { VERYHIGH(1.6f), MEDIUMHIGH(1.4f), LITTLEHIGH(1.2f), NORMAL(1.0f);
    private float factor ;
    public float getFactor() {
        return factor;
    }
    private TransitServiceWorkload(float factor) {
        this.factor = factor;
    }
}
