package idv.woody.riskassessment.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "idv.woody")
public class DemoApp {

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(DemoApp.class);
        DemoIllegalAccess bean = appContext.getBean(DemoIllegalAccess.class);
        bean.illegalAccessExample();
    }

}
