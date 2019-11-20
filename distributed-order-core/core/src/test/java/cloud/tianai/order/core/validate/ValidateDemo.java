package cloud.tianai.order.core.validate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ValidateDemo {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Min(value = 10, message = "age不能小于10")
    @NotNull(message = "age不能为空")
    private Integer age;
}
