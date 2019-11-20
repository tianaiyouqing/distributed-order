package cloud.tianai.order.core.validate;

import cloud.tianai.order.common.validate.ValidateUtils;
import cloud.tianai.order.core.OrderCoreApplicationTests;
import org.junit.Test;
import org.springframework.validation.BindingResult;

public class ValidateTest extends OrderCoreApplicationTests {

    @Test
    public void testValidate() {
        ValidateDemo validateDemo = new ValidateDemo();
        validate(validateDemo);

    }


    public static  <T> void validate(T obj) {
        BindingResult bindingResult = ValidateUtils.validate(obj);
        System.out.println(bindingResult.getFieldError().getDefaultMessage());
    }
}
