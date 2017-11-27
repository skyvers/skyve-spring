package org.skyve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private SkyveConfiguration skyve;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		System.out.println(String.format("Mapping view controller home.jsp to %s", skyve.getConfig().getUrl().getHome()));
		registry.addViewController(skyve.getConfig().getUrl().getHome()).setViewName("forward:/home");
		// registry.addViewController("/").setViewName("forward:home");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

}
