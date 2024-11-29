package be.kdg.int5.storefront.adapters.in;


import be.kdg.int5.storefront.port.in.CancelAllOldOrdersUseCase;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CancelOldOrdersSchedule {
    private final CancelAllOldOrdersUseCase cancelAllOldOrdersUseCase;

    public CancelOldOrdersSchedule(CancelAllOldOrdersUseCase cancelAllOldOrdersUseCase) {
        this.cancelAllOldOrdersUseCase = cancelAllOldOrdersUseCase;
    }

    /**
     * This will run every hour on the hour to cancel all orders that are older than 12 hours and are still pending
     */
    @Async
    @Scheduled(cron = "0 0 * * * ?", zone = "Europe/Paris")
    public void cancelAllOldPendingOrders() {
        cancelAllOldOrdersUseCase.cancelAllOldPendingOrders();
    }
}
