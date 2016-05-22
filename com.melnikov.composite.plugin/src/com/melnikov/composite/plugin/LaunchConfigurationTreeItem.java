package com.melnikov.composite.plugin;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.graphics.Image;

class LaunchConfigurationTreeItem implements ILaunchConfigurationTreeNode {
	private boolean checked;

	private ILaunchConfiguration configuration;
	private LaunchConfigurationTreeCategory category;
	
	public LaunchConfigurationTreeItem(LaunchConfigurationTreeCategory category,
			ILaunchConfiguration configuration) {
		this.category = category;
		this.configuration = configuration;
	}

	public String getIdentifier() {
		try {
			return configuration.getMemento();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public void setSelectedIfListed(List<String> identifiers) {
		String currentIdentifier = getIdentifier();
		if (currentIdentifier != null) {
			for (String identifier: identifiers) {
				if (currentIdentifier.equals(identifier)) {
					checked = true;
					return;
				}
			}
			checked = false;
		}
	}

	@Override
	public ILaunchConfigurationTreeNode[] getChildren() {
		return null;
	}

	@Override
	public ILaunchConfigurationTreeNode getParent() {
		return category;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public Image getImage() {
		return category.getImage();
	}

	@Override
	public String getText() {
		return configuration.getName();
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public boolean isGrayed() {
		return false;
	}

	@Override
	public void setCheckState(boolean state) {
		checked = state;
	}
};
