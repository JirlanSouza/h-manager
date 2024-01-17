package com.js.hmanager.inventory.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;

public record RoomBeds(int doubleBed, int singleBed) {
    public RoomBeds(int doubleBed, int singleBed) {
        this.doubleBed = doubleBed;
        this.singleBed = singleBed;

        if (isNotValidBedsQuantity()) {
            throw new InvalidArgumentDomainException(
                    "The number of beds cannot be negative and must have at least one double or one single bed"
            );
        }
    }

    private boolean isNotValidBedsQuantity() {
        boolean sumIsZeroOrLess = (doubleBed + singleBed) <= 0;
        boolean isSomeNegative = (doubleBed < 0) || (singleBed < 0);

        return sumIsZeroOrLess || isSomeNegative;
    }

    int calculateCapacity() {
        return (doubleBed * 2) + singleBed;
    }
}
