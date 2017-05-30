/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.util.ErrorHandler;

public class JmsErrorHandler implements ErrorHandler {

    private static final Logger log = LoggerFactory
            .getLogger(JmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {

        if (t.getCause() instanceof MethodArgumentNotValidException) {
            // Validation Error

            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) t
                    .getCause();
            System.out.println(ex.getFailedMessage().getPayload());

            log.error("Validation Error!");

        } else if (t.getCause() instanceof ArithmeticException) {
            // ArithmeticException

            log.error("ArithmeticException Error!");

        } else {
            // Other Error

            log.error("Other Error!");

        }
        t.printStackTrace();
    }

}
