/*
 * Copyright (c) 2014-2026 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.addon;

import java.util.ServiceLoader;

import net.wurstclient.WurstClient;

public final class AddonLoader
{
	private AddonLoader()
	{
		// Prevent instantiation
	}
	
	/**
	 * Loads all addons using Java's ServiceLoader. Call this during
	 * WurstClient initialization.
	 */
	public static void loadAddons()
	{
		ServiceLoader<Addon> loader = ServiceLoader.load(Addon.class);
		
		for(Addon addon : loader)
		{
			loadAddon(addon);
		}
	}
	
	private static void loadAddon(Addon addon)
	{
		try
		{
			WurstClient wurst = WurstClient.INSTANCE;
			
			wurst.getHax().registerHackAddon(addon);
			wurst.getCmds().registerCommandAddon(addon);
			
			System.out.println("[Wurst] Loaded addon: " + addon.getAddonName()
				+ " (" + addon.getHacks().length + " hacks, "
				+ addon.getCommands().length + " commands)");
			
		}catch(Exception e)
		{
			System.err.println("[Wurst] Failed to load addon "
				+ addon.getAddonName() + ": " + e);
			e.printStackTrace();
		}
	}
}
