package com.melnikov.composite.plugin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.swt.graphics.Image;
import org.eclipse.debug.ui.DebugUITools;

class LaunchConfigurationTreeCategory implements ILaunchConfigurationTreeNode {
	private ILaunchConfigurationType type;
	private LaunchConfigurationCollection collection;
	private LaunchConfigurationTreeItem[] items;

	public LaunchConfigurationTreeCategory(LaunchConfigurationCollection collection,
			ILaunchConfigurationType type) {
		this.collection = collection;
		this.type = type;
		try {
			ILaunchConfiguration[] configurations = this.collection.manager.getLaunchConfigurations(type);
			List<LaunchConfigurationTreeItem> items = new ArrayList<LaunchConfigurationTreeItem>();
			for (ILaunchConfiguration configuration: configurations) {
				items.add(new LaunchConfigurationTreeItem(this, configuration));
			}
			this.items = items.toArray(new LaunchConfigurationTreeItem[items.size()]);
		} catch (CoreException e) {
			this.items = new LaunchConfigurationTreeItem[0];
		}

	};

	public List<String> getSelectedLaunchConfigrationIdentifiers() {
		ArrayList<String> identifiers = new ArrayList<String>();
		for (LaunchConfigurationTreeItem item: items) {
			if (item.isChecked()) {
				String configurationID = item.getIdentifier();
				if (configurationID != null) {
					identifiers.add(configurationID);
				}
			}
		}
		return identifiers;
	}

	public void setSelectedLaunchConfigrations(List<String> identifiers) {
		for (LaunchConfigurationTreeItem item: items) {
			item.setSelectedIfListed(identifiers);
		}
	}

	@Override
	public ILaunchConfigurationTreeNode[] getChildren() {
		return items;
	}

	@Override
	public ILaunchConfigurationTreeNode getParent() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return items.length > 0;
	}

	@Override
	public Image getImage() {
		return DebugUITools.getImage(type.getIdentifier());
	}

	@Override
	public String getText() {
		return type.getName();
	}

	@Override
	public boolean isChecked() {
		for (LaunchConfigurationTreeItem item: items) {
			if (item.isChecked()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isGrayed() {
		return true;
	}

	@Override
	public void setCheckState(boolean state) {
		for (LaunchConfigurationTreeItem item: items) {
			item.setCheckState(state);
		}
	}
};