package test;

import com.themall.thirdparty.ThemallThirdPartyApplication;
import com.themall.thirdparty.component.SmsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThemallThirdPartyApplication.class)
public class test {

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void sent() {
        smsComponent.sendSmsLundroid("17322969809", "2134");
    }
}
