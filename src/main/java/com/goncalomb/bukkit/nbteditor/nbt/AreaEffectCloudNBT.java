/*
 * Copyright (C) 2013-2016 Gonçalo Baltazar <me@goncalomb.com>
 *
 * This file is part of NBTEditor.
 *
 * NBTEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NBTEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NBTEditor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.goncalomb.bukkit.nbteditor.nbt;

import org.bukkit.inventory.ItemStack;

import com.goncalomb.bukkit.mylib.reflect.NBTTagList;
import com.goncalomb.bukkit.mylib.reflect.NBTUtils;
import com.goncalomb.bukkit.nbteditor.nbt.variable.ColorVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.FloatVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.IntegerVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.NBTGenericVariableContainer;
import com.goncalomb.bukkit.nbteditor.nbt.variable.ParticleVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.StringVariable;

public class AreaEffectCloudNBT extends ThrownPotionNBT {

	static {
		NBTGenericVariableContainer variables = new NBTGenericVariableContainer("AreaEffectCloud");
		variables.add("Age", new IntegerVariable("Age", 0));
		variables.add("Color", new ColorVariable("Color"));
		variables.add("Duration", new IntegerVariable("Duration", 0));
		variables.add("ReapplicationDelay", new IntegerVariable("ReapplicationDelay", 0));
		variables.add("WaitTime", new IntegerVariable("WaitTime", 0));
		variables.add("Radius", new FloatVariable("Radius", 0f));
		variables.add("RadiusOnUse", new FloatVariable("RadiusOnUse"));
		variables.add("RadiusPerTick", new FloatVariable("RadiusPerTick"));
		variables.add("Particle", new ParticleVariable("Particle"));
		variables.add("ParticleParam1", new IntegerVariable("ParticleParam1"));
		variables.add("ParticleParam2", new IntegerVariable("ParticleParam2"));
		variables.add("Potion", new StringVariable("Potion"));
		registerVariables(AreaEffectCloudNBT.class, variables);
	}

	public AreaEffectCloudNBT() {
		_data.setInt("Duration", 500);
		_data.setInt("ReapplicationDelay", 10);
		_data.setInt("Radius", 3);
	}

	public void setItem(ItemStack potion) {
		if (potion == null) {
			_data.remove("Effects");
		} else {
			NBTTagList effects = NBTUtils.potionToNBTEffectsList(potion);
			if (effects != null) {
				_data.setList("Effects", effects);
				return;
			}
		}
	}

	public ItemStack getItem() {
		if (_data.hasKey("Effects")) {
			return NBTUtils.potionFromNBTEffectsList(_data.getList("Effects"));
		}
		return null;
	}

	public boolean isSet() {
		return _data.hasKey("Effects");
	}

}
