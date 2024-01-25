package pe.gob.pj.pide.dao.utils;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ProjectProperties implements Serializable, ApplicationContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ApplicationContext applicationContext;

    public static PropertyConfig getInstance() {
        return applicationContext.getBean(PropertyConfig.class);
    }

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
		
	}

}
