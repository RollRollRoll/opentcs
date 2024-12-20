// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi;

import jakarta.inject.Singleton;
import org.opentcs.customizations.kernel.KernelInjectionModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configures the service web API extension.
 */
public class ServiceWebApiModule
    extends
      KernelInjectionModule {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ServiceWebApiModule.class);

  /**
   * Creates a new instance.
   */
  public ServiceWebApiModule() {
  }

  @Override
  protected void configure() {
    ServiceWebApiConfiguration configuration
        = getConfigBindingProvider().get(
            ServiceWebApiConfiguration.PREFIX,
            ServiceWebApiConfiguration.class
        );

    if (!configuration.enable()) {
      LOG.info("Service web API disabled by configuration.");
      return;
    }

    bind(ServiceWebApiConfiguration.class)
        .toInstance(configuration);

    extensionsBinderAllModes().addBinding()
        .to(ServiceWebApi.class)
        .in(Singleton.class);
  }
}
