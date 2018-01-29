package com.goldmsg.gmvcs.syn.core.BootListener;

import com.goldmsg.gmvcs.syn.quartz.JobContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class MyServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
      JobContext.getInstance().setContext(sce.getServletContext());
      System.out.println("-------contextInitialized-----------");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("------------contextDestroyed-------------");
  }
}
