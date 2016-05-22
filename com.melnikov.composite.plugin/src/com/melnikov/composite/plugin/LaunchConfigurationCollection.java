package com.melnikov.composite.plugin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;

class LaunchConfigurationCollection {
	final ILaunchManager manager;
	private LaunchConfigurationTreeCategory[] categories;

	public LaunchConfigurationCollection(ILaunchManager manager) {
		this.manager = manager;

		List<LaunchConfigurationTreeCategory> categories = 
				new ArrayList<LaunchConfigurationTreeCategory>();
		ILaunchConfigurationType[] types = manager.getLaunchConfigurationTypes();
		
		for (int i = types.length - 1; i >= 0; --i) {
			if  (types[i].isPublic() && !types[i].getIdentifier().
					equals(CompositeLaunchConfigurationDelegate.TYPE_OF_CONFIGURATIONS)) {
				categories.add(new LaunchConfigurationTreeCategory(this, types[i]));
			}
		}
		this.categories = categories.toArray(
				new LaunchConfigurationTreeCategory[categories.size()]);
	}


	public ILaunchConfigurationTreeNode[] getChildren() {
		List<LaunchConfigurationTreeCategory> nonEmptyCategories = 
				new ArrayList<LaunchConfigurationTreeCategory>();
		for (LaunchConfigurationTreeCategory category: categories) {
			if(category.hasChildren()) {
				nonEmptyCategories.add(category);
			}
		}
		
		return nonEmptyCategories.toArray(
				new LaunchConfigurationTreeCategory[nonEmptyCategories.size()]);
	}

	public List<String> getSelectedLaunchConfigrationIdentifiers() {
		ArrayList<String> identifiers = new ArrayList<String>();
		for (LaunchConfigurationTreeCategory category: categories) {
			identifiers.addAll(category.getSelectedLaunchConfigrationIdentifiers());
		};
		return identifiers;
	}

	public void setSelectedLaunchConfigrations(List<String> identifiers) {
		for (LaunchConfigurationTreeCategory category: categories) {
			category.setSelectedLaunchConfigrations(identifiers);
		}
	}
}

