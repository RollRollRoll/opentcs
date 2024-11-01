// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernelcontrolcenter.vehicles;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.opentcs.access.KernelServicePortal;
import org.opentcs.components.Lifecycle;
import org.opentcs.customizations.ApplicationEventBus;
import org.opentcs.customizations.ServiceCallWrapper;
import org.opentcs.data.model.Vehicle;
import org.opentcs.drivers.vehicle.management.ProcessModelEvent;
import org.opentcs.drivers.vehicle.management.VehicleAttachmentEvent;
import org.opentcs.drivers.vehicle.management.VehicleAttachmentInformation;
import org.opentcs.drivers.vehicle.management.VehicleProcessModelTO;
import org.opentcs.util.CallWrapper;
import org.opentcs.util.event.EventHandler;
import org.opentcs.util.event.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a pool of {@link LocalVehicleEntry}s with an entry for every {@link Vehicle} object in
 * the kernel.
 */
public class LocalVehicleEntryPool
    implements
      EventHandler,
      Lifecycle {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(LocalVehicleEntryPool.class);
  /**
   * The service portal to use for kernel interactions.
   */
  private final KernelServicePortal servicePortal;
  /**
   * The call wrapper to use for service calls.
   */
  private final CallWrapper callWrapper;
  /**
   * Where this instance registers for application events.
   */
  private final EventSource eventSource;
  /**
   * The entries of this pool.
   */
  private final Map<String, LocalVehicleEntry> entries = new TreeMap<>();
  /**
   * Whether the pool is initialized or not.
   */
  private boolean initialized;

  /**
   * Creates a new instance.
   *
   * @param servicePortal The service portal to use for kernel interactions.
   * @param callWrapper The call wrapper to use for service calls.
   * @param eventSource Where this instance registers for application events.
   */
  @Inject
  public LocalVehicleEntryPool(
      KernelServicePortal servicePortal,
      @ServiceCallWrapper
      CallWrapper callWrapper,
      @ApplicationEventBus
      EventSource eventSource
  ) {
    this.servicePortal = requireNonNull(servicePortal, "servicePortal");
    this.callWrapper = requireNonNull(callWrapper, "callWrapper");
    this.eventSource = requireNonNull(eventSource, "eventSource");
  }

  @Override
  public void initialize() {
    if (isInitialized()) {
      LOG.debug("Already initialized.");
      return;
    }

    eventSource.subscribe(this);

    try {

      Set<Vehicle> vehicles
          = callWrapper.call(() -> servicePortal.getVehicleService().fetchObjects(Vehicle.class));
      for (Vehicle vehicle : vehicles) {
        VehicleAttachmentInformation ai = callWrapper.call(() -> {
          return servicePortal.getVehicleService()
              .fetchAttachmentInformation(vehicle.getReference());
        });
        VehicleProcessModelTO processModel = callWrapper.call(() -> {
          return servicePortal.getVehicleService().fetchProcessModel(vehicle.getReference());
        });
        LocalVehicleEntry entry = new LocalVehicleEntry(ai, processModel);
        entries.put(vehicle.getName(), entry);
      }
    }
    catch (Exception ex) {
      LOG.warn("Error initializing local vehicle entry pool", ex);
      entries.clear();
      return;
    }

    LOG.debug("Initialized vehicle entry pool: {}", entries);
    initialized = true;
  }

  @Override
  public boolean isInitialized() {
    return initialized;
  }

  @Override
  public void terminate() {
    if (!isInitialized()) {
      LOG.debug("Not initialized.");
      return;
    }

    eventSource.unsubscribe(this);

    entries.clear();
    initialized = false;
  }

  @Override
  public void onEvent(Object event) {
    if (event instanceof ProcessModelEvent) {
      ProcessModelEvent e = (ProcessModelEvent) event;
      LocalVehicleEntry entry = getEntryFor(e.getUpdatedProcessModel().getName());
      if (entry == null) {
        return;
      }
      entry.setProcessModel(e.getUpdatedProcessModel());
    }
    else if (event instanceof VehicleAttachmentEvent) {
      VehicleAttachmentEvent e = (VehicleAttachmentEvent) event;
      LocalVehicleEntry entry = getEntryFor(e.getVehicleName());
      if (entry == null) {
        return;
      }
      entry.setAttachmentInformation(e.getAttachmentInformation());
    }
  }

  @Nonnull
  public Map<String, LocalVehicleEntry> getEntries() {
    return entries;
  }

  @Nullable
  public LocalVehicleEntry getEntryFor(String vehicleName) {
    return vehicleName == null ? null : entries.get(vehicleName);
  }
}
