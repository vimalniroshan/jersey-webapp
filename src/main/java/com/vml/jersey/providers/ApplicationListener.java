package com.vml.jersey.providers;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ApplicationListener implements ApplicationEventListener {

    @Inject
    private ServiceLocator serviceLocator;

    @Override
    public void onEvent(final ApplicationEvent event) {
        if(event.getType() == ApplicationEvent.Type.INITIALIZATION_APP_FINISHED) {
            populateServiceLocator();
        }
    }

    @Override
    public RequestEventListener onRequest(final RequestEvent requestEvent) {
        return null;
    }

    private void populateServiceLocator() {
        try {
            serviceLocator.getService(DynamicConfigurationService.class)
                    .getPopulator().populate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
