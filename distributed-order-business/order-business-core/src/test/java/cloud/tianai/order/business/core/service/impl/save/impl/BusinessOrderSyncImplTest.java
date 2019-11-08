package cloud.tianai.order.business.core.service.impl.save.impl;

import cloud.tianai.order.business.core.OrderBusinessApplicationTests;
import cloud.tianai.order.business.api.sync.BusinessOrderSync;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessOrderSyncImplTest extends OrderBusinessApplicationTests {

    @Autowired
    private BusinessOrderSync businessOrderSync;

    @Test
    public void sync() {
        businessOrderSync.sync("1118739843765453209793940");
    }
}