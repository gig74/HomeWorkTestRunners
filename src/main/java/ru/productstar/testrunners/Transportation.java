package ru.productstar.testrunners;

import ru.productstar.testrunners.exceptions.MovingFragileFarawayException;

public class Transportation {
    int distance;
    private TransitServiceWorkload transitServiceWorkload;
    private boolean isFragile;
    private boolean isBigSize;

    public int getDistance() {
        return distance;
    }

    public TransitServiceWorkload getTransitServiceWorkload() {
        return transitServiceWorkload;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public boolean isBigSize() {
        return isBigSize;
    }

    public Transportation(int distance, TransitServiceWorkload transitServiceWorkload, boolean isBigSize, boolean isFragile) {
        if (distance < 1) {
            throw new IllegalArgumentException("Расстояние менее 1 км");
        }
        if (isFragile && distance > 30) {
            throw new MovingFragileFarawayException("Хрупкие предметы нельзя перевозить далеко (> 30 км)");
        }
        this.distance = distance;
        this.transitServiceWorkload = transitServiceWorkload;
        this.isBigSize = isBigSize;
        this.isFragile = isFragile;
    }

    public static float getCurrentPrice(Transportation transportation) {
        float currentPrice = getAddPriceDistance(transportation.getDistance())
                + getAddPriceSize(transportation.isBigSize())
                + getAddPriceFragile(transportation.isFragile());
        currentPrice = currentPrice * transportation.getTransitServiceWorkload().getFactor();
        return currentPrice;
    }

    private static float getAddPriceDistance(int distance) {
        if (distance <= 2) {
            return 50;
        } else if (distance <= 10) {
            return 100;
        } else if (distance <= 30) {
            return 200;
        } else {
            return 300;
        }
    }

    private static float getAddPriceSize(boolean isBigSize) {
        if (isBigSize) {
            return 200;
        } else {
            return 100;
        }
    }

    private static float getAddPriceFragile(boolean isFragile) {
        if (isFragile) {
            return 300;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Transportation{" +
                "distance=" + distance +
                ", transitServiceWorkload=" + transitServiceWorkload +
                ", isFragile=" + isFragile +
                ", isBigSize=" + isBigSize +
                '}';
    }
}
