package com.casavo.notification;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({CommonTestConfiguration.GlobalTestConfiguration.class})
public abstract class CommonTestConfiguration {

    public CommonTestConfiguration() {

    }

    @Configuration
    public static class GlobalTestConfiguration {


    }
}
