package cloud.tianai.order.core.business.service.impl.save.impl;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.business.service.impl.save.BusinessOrderSync;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class BusinessOrderSyncImplTest extends ApplicationTests {

    @Autowired
    private BusinessOrderSync businessOrderSync;

    @Test
    public void sync() {
        businessOrderSync.sync("1118739843765453209793940");
    }
}