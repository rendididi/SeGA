package org.sega.viewer.config.resolvers;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * TODO
 * Jade view resolver
 *
 * @author: Raysmond
 */

public class JadeViewResolver extends de.neuland.jade4j.spring.view.JadeViewResolver{

    public JadeViewResolver(){
        super();
    }

    public SpringTemplateLoader jadeTemplateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setBasePath("/WEB-INF/views/");
        templateLoader.setEncoding("UTF-8");
        templateLoader.setSuffix(".jade");
        return templateLoader;
    }

    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration configuration = new JadeConfiguration();
        configuration.setCaching(false);
        configuration.setTemplateLoader(jadeTemplateLoader());
        return configuration;
    }
}
