package com.fc.final7.global.exception;

public class NoSearchReservationException extends BusinessException{

    public NoSearchReservationException() {
        super("존재하지 않는 예약 내역입니다.", ErrorCode.RESERVATION_NOT_FOUND);
    }
}
