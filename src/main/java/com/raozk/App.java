package com.raozk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class App 
{
    private static Logger logger = LoggerFactory.getLogger("App");

    public static void main( String[] args ) throws Exception {
        ApplicationContext ap = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
