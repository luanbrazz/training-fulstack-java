package jdev.mentoria.lojavirtual;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// Esta classe permite acessar o ApplicationContext de forma estática em qualquer parte do código,
// especialmente útil para obter beans em classes não gerenciadas pelo Spring.
@Component
public class ApplicationContextLoad implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextLoad.applicationContext = applicationContext;
    }
}
