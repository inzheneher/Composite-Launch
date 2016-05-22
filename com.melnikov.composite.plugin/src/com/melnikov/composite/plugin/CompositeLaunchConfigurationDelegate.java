package com.melnikov.composite.plugin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

public class CompositeLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {

	public static final String TYPE_OF_CONFIGURATIONS = "com.melnikov.composite.plugin";
	public static final String LIST_OF_CONFIGURATIONS = "Configurations";
		
	private List<ILaunchConfiguration> getSupportedConfigs(List<String> configNames, String mode) {

        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
        
        List<ILaunchConfiguration> result = new ArrayList<>();
        for(String configName: configNames) {
            try {
                ILaunchConfiguration config = manager.getLaunchConfiguration(configName);
                if (config != null && config.supportsMode(mode)) {
                    result.add(config);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
	
	@Override
	public void launch(ILaunchConfiguration configuration,
			String mode,
			ILaunch launch, 
			IProgressMonitor monitor) 
			throws CoreException {

		List<String> configNames = configuration.getAttribute(
				CompositeLaunchConfigurationDelegate.LIST_OF_CONFIGURATIONS,
				new ArrayList<String>());
		for (ILaunchConfiguration configToLaunch: getSupportedConfigs(configNames, mode)) {
			if(configToLaunch.equals(configuration)) {
				continue;
			}
			configToLaunch.launch(mode, monitor);
		}

	}

}
