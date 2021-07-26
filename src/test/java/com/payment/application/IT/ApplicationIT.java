package com.payment.application.IT;

import com.payment.application.app.Application;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Application.class)
public class ApplicationIT {
    @Test
    public void main() {
        Application.main(new String[]{});
    }
}

