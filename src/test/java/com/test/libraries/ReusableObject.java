package com.test.libraries;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/*@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})*/

@ContextConfiguration("classpath:cucumber.xml")
@TestExecutionListeners(value = {MyListener.class, DependencyInjectionTestExecutionListener.class})
public abstract class ReusableObject {

   // private static final Logger LOGGER = LoggerFactory.getLogger(ReusableObject.class);

    @Autowired
    public Context initBrowser;

}
