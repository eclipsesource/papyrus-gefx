/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gefdiag.common.palette;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fx.core.Subscription;
import org.eclipse.fx.core.command.Command;
import org.eclipse.fx.core.observable.FXObservableUtil;
import org.eclipse.papyrus.gef4.palette.PaletteDescriptor;
import org.eclipse.papyrus.gef4.tools.Tool;
import org.eclipse.papyrus.gef4.tools.ToolManager;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ChildConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.SeparatorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.StackConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.util.PaletteconfigurationSwitch;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

import com.google.inject.Injector;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A {@link PaletteDescriptor} based on the Papyrus Palette Definition model
 */
public class PapyrusPaletteDescriptor implements PaletteDescriptor {

	private final String id;

	private final String label;

	private Collection<PaletteConfiguration> paletteConfigurations;

	@Inject
	private ToolManager toolManager;

	@Inject
	private Injector injector;

	public PapyrusPaletteDescriptor(String id, String label) {
		this.id = id;
		this.label = label;
	}

	@Inject
	public void setPaletteConfigurations(Collection<PaletteConfiguration> paletteConfigurations) {
		this.paletteConfigurations = paletteConfigurations;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * @see org.eclipse.papyrus.gef4.palette.PaletteDescriptor#getDrawers()
	 *
	 * @return
	 */
	@Override
	public ObservableList<Drawer> getDrawers() {
		ObservableList<Drawer> allDrawers = FXCollections.observableArrayList();
		for (PaletteConfiguration configuration : paletteConfigurations) {
			configuration.getDrawerConfigurations().stream().map(this::toDrawer).forEach(allDrawers::add);
		}
		return allDrawers;
	}

	public Drawer toDrawer(DrawerConfiguration drawerConfig) {
		return new Drawer() {

			@Override
			public ObjectExpression<CharSequence> getName() {
				return new SimpleObjectProperty<>(drawerConfig.getLabel());
			}

			@Override
			public ObservableList<ChildElement> getChildren() {
				ObservableList<ChildElement> children = FXCollections.observableArrayList();
				drawerConfig.getOwnedConfigurations().stream().map(PapyrusPaletteDescriptor.this::toChildElement).forEach(children::add);
				return children;
			}
		};
	}

	public ChildElement toChildElement(ChildConfiguration childConfig) {
		return new PaletteconfigurationSwitch<ChildElement>() {
			@Override
			public ChildElement caseSeparatorConfiguration(SeparatorConfiguration object) {
				return new SeparatorImpl();
			}

			@Override
			public ChildElement caseToolConfiguration(ToolConfiguration object) {
				URL iconURL = getIconURL(object);
				Set<String> elementTypeIds = object.getElementDescriptors().stream().map(ElementDescriptor::getElementType).map(ElementTypeConfiguration::getIdentifier).collect(Collectors.toSet());
				switch (object.getKind()) {
				case CONNECTION_TOOL:
					return new EdgeToolEntry(elementTypeIds, object.getLabel(), iconURL, object.getDescription());
				case CREATION_TOOL:
					return new NodeToolEntry(elementTypeIds, object.getLabel(), iconURL, object.getDescription());
				}

				return null;
			}

			private URL getIconURL(ToolConfiguration tool) {
				try {
					String iconPath = tool.getIcon().getIconPath();
					if (iconPath == null || iconPath.isEmpty()) {
						return null;
					}

					URI iconURI = URI.createPlatformPluginURI(tool.getIcon().getPluginID() + "/" + tool.getIcon().getIconPath(), true);
					return new URL(iconURI.toString());
				} catch (IOException ex) {
					return null;
				}
			}

			@Override
			public ChildElement caseStackConfiguration(StackConfiguration object) {
				return toStack(object);
			}
		}.doSwitch(childConfig);
	}

	public Stack toStack(StackConfiguration object) {
		return () -> {
			ObservableList<LeafElement> children = FXCollections.observableArrayList();
			object.getOwnedConfigurations().stream().map(this::toChildElement).map(LeafElement.class::cast).forEach(children::add);
			return children;
		};
	}

	private abstract class AbstractToolEntry implements ToolEntry {
		protected String label;
		protected URL icon;
		protected String tooltip;
		private Subscription toolEntryActiveStateSub;

		protected final SimpleBooleanProperty activeProperty = new SimpleBooleanProperty(false);

		protected AbstractToolEntry(String label, URL icon, String tooltip) {
			this.label = label;
			this.icon = icon;
			this.tooltip = tooltip;
		}

		@Override
		public ObjectExpression<CharSequence> getName() {
			return new SimpleObjectProperty<>(this.label);
		}

		@Override
		public ObjectExpression<CharSequence> getTooltip() {
			return new SimpleObjectProperty<>(this.tooltip);
		}

		@Override
		public ObjectExpression<URL> getIconURL() {
			return icon == null ? null : new SimpleObjectProperty<>(icon);
		}

		protected void activatePaletteTool(Tool tool) {
			Subscription.disposeIfExists(toolEntryActiveStateSub);

			// TODO properly inject the tool, e.g. via a Provider (Or assisted inject from a ToolID...)
			injector.injectMembers(tool);

			final AtomicReference<Subscription> currentSubscription = new AtomicReference<>();
			Subscription sub = FXObservableUtil.onChange(tool.activeProperty(), activeState -> {
				AbstractToolEntry.this.activeProperty.set(activeState);
				if (!activeState) {
					// Automatically dispose this listener when the tool is deactivated; we won't reuse it
					Subscription.disposeIfExists(currentSubscription.getAndSet(null));
				}
			});

			toolEntryActiveStateSub = FXObservableUtil.onChange(this.activeProperty, activeState -> {
				if (!activeState) {
					Subscription.disposeIfExists(toolEntryActiveStateSub);
					tool.deactivate();
				}
			});

			currentSubscription.set(sub);

			toolManager.setActiveTool(tool);
		}

		@Override
		public final BooleanProperty activeProperty() {
			return activeProperty;
		}
	}

	private class NodeToolEntry extends AbstractToolEntry {

		private Collection<String> elementTypeIds;

		public NodeToolEntry(Collection<String> elementTypeIds, String label, URL icon, String tooltip) {
			super(label, icon, tooltip);
			this.elementTypeIds = elementTypeIds;
		}

		@Override
		public Command<Void> getCommand() {
			return Command.createCommand(() -> {
				activatePaletteTool(new CreateNodeTool(elementTypeIds));
			});
		}
	}

	private class SeparatorImpl implements Separator {
		// Nothing
	}

	private class EdgeToolEntry extends AbstractToolEntry {

		private Collection<String> elementTypeIds;

		public EdgeToolEntry(Collection<String> elementTypeIds, String label, URL icon, String tooltip) {
			super(label, icon, tooltip);
			this.elementTypeIds = elementTypeIds;
		}

		@Override
		public Command<Void> getCommand() {
			return Command.createCommand(() -> {
				activatePaletteTool(new CreateConnectionTool(elementTypeIds));
			});
		}
	}

}
