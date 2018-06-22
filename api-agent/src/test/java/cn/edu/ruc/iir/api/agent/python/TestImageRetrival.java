package cn.edu.ruc.iir.api.agent.python;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = ImageRetrival.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestImageRetrival {

    @Value("${featureURL}")
    private String featureURL;

    @Test
    public void testGetHotStar() throws Exception {
    }


}
