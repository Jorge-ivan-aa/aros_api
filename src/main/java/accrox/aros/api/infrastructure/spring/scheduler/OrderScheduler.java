package accrox.aros.api.infrastructure.spring.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import accrox.aros.api.application.usecases.order.CancelOverdueOrdersUseCase;

@Component
public class OrderScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OrderScheduler.class);

    @Autowired
    private CancelOverdueOrdersUseCase cancelOverdueOrdersUseCase;

    @Scheduled(cron = "0 0 2 * * ?")
    public void runDailyOrderCancellation() {
        logger.info("Running scheduled task: Cancel expired orders...");
        cancelOverdueOrdersUseCase.execute();
    }

}
