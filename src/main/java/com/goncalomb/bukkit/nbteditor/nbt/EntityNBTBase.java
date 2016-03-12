/*
 * Copyright (C) 2013-2015 Gonçalo Baltazar <me@goncalomb.com>
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.goncalomb.bukkit.mylib.namemaps.EntityTypeMap;
import com.goncalomb.bukkit.mylib.reflect.NBTTagCompound;
import com.goncalomb.bukkit.mylib.reflect.NBTUtils;
import com.goncalomb.bukkit.nbteditor.nbt.variable.NBTGenericVariableContainer;
import com.goncalomb.bukkit.nbteditor.nbt.variable.NBTVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.NBTVariableContainer;

import net.iharder.Base64;

abstract class EntityNBTBase {

	private static HashMap<EntityType, Class<? extends EntityNBT>> _entityClasses;
	private static HashMap<Class<? extends EntityNBT>, NBTGenericVariableContainer> _entityVariables;
	private static HashMap<EntityType, NBTGenericVariableContainer> _entityVariablesByType;

	static {
		_entityClasses = new HashMap<EntityType, Class<? extends EntityNBT>>();
		_entityVariables = new HashMap<Class<? extends EntityNBT>, NBTGenericVariableContainer>();
		_entityVariablesByType = new HashMap<EntityType, NBTGenericVariableContainer>();
		// Force static initialization of the EntityNBT class.
		try {
			Class.forName(EntityNBT.class.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	static void registerVariables(Class<? extends EntityNBT> entityClass, NBTGenericVariableContainer variables) {
		_entityVariables.put(entityClass, variables);
	}

	static void registerVariables(EntityType entityType, NBTGenericVariableContainer variables) {
		_entityVariablesByType.put(entityType, variables);
	}

	static void registerEntity(EntityType entityType, Class<? extends EntityNBT> entityClass) {
		_entityClasses.put(entityType, entityClass);
	}

	public static boolean isValidType(EntityType entityType) {
		return _entityClasses.containsKey(entityType);
	}

	public static Collection<EntityType> getValidEntityTypes() {
		return _entityClasses.keySet();
	}

	private static EntityNBT newInstance(EntityType entityType, NBTTagCompound data) {
		Class<? extends EntityNBT> entityClass = _entityClasses.get(entityType);
		EntityNBTBase instance;
		try {
			instance = entityClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error when instantiating " + entityClass.getName() + ".", e);
		}
		instance._entityType = entityType;
		if (data != null) {
			instance._data = data;
		}
		instance._data.setString("id", EntityTypeMap.getName(entityType));
		return (EntityNBT) instance;
	}

	public static EntityNBT fromEntityType(EntityType entityType) {
		if (_entityClasses.containsKey(entityType)) {
			return newInstance(entityType, null);
		}
		return null;
	}

	static EntityNBT fromEntityType(EntityType entityType, NBTTagCompound data) {
		if (_entityClasses.containsKey(entityType)) {
			EntityNBT entityNbt = newInstance(entityType, data);
			return entityNbt;
		} else {
			EntityNBT entityNbt = new EntityNBT(entityType);
			entityNbt._data = data;
			return entityNbt;
		}
	}

	public static EntityNBT fromEntity(Entity entity) {
		EntityNBT entityNbt = fromEntityType(entity.getType(), NBTUtils.getEntityNBTData(entity));
		// When cloning, remove the UUID to force all entities to have a unique one.
		entityNbt._data.remove("UUIDMost");
		entityNbt._data.remove("UUIDLeast");
		return entityNbt;
	}

	public static EntityNBT unserialize(String serializedData) {
		try {
			NBTTagCompound data = NBTTagCompound.unserialize(Base64.decode(serializedData));
			return fromEntityType(EntityTypeMap.getByName(data.getString("id")), data);
		} catch (Throwable e) {
			throw new RuntimeException("Error unserializing EntityNBT.", e);
		}
	}

	private EntityType _entityType;
	protected NBTTagCompound _data;

	protected EntityNBTBase(EntityType entityType) {
		_entityType = entityType;
		_data = new NBTTagCompound();
		if (entityType != null) {
			_data.setString("id", EntityTypeMap.getName(entityType));
		}
	}

	public EntityType getEntityType() {
		return _entityType;
	}

	public NBTVariableContainer[] getAllVariables() {
		NBTGenericVariableContainer aux;
		ArrayList<NBTVariableContainer> list = new ArrayList<NBTVariableContainer>(3);

		if ((aux = _entityVariablesByType.get(_entityType)) != null) {
			list.add(aux.boundToData(_data));
		}

		for (Class<?> clazz = this.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			if ((aux = _entityVariables.get(clazz)) != null) {
				list.add(aux.boundToData(_data));
			}
		}
		return list.toArray(new NBTVariableContainer[0]);
	}

	public NBTVariable getVariable(String name) {
		NBTGenericVariableContainer aux;
		name = name.toLowerCase();

		if ((aux = _entityVariablesByType.get(_entityType)) != null) {
			if (aux.hasVariable(name)) {
				return aux.getVariable(name, _data);
			}
		}

		for (Class<?> clazz = this.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			if ((aux = _entityVariables.get(clazz)) != null) {
				if (aux.hasVariable(name)) {
					return aux.getVariable(name, _data);
				}
			}
		}
		return null;
	}

	public Entity spawn(Location location) {
		return NBTUtils.spawnEntity(_data, location);
	}

	public String serialize() {
		try {
			return Base64.encodeBytes(_data.serialize(), Base64.GZIP);
		} catch (Throwable e) {
			throw new RuntimeException("Error serializing EntityNBT.", e);
		}
	}

	public EntityNBT clone() {
		return fromEntityType(_entityType, _data.clone());
	}

	public String getMetadataString() {
		NBTTagCompound data = _data.clone();
		data.remove("id");
		return data.toString();
	}

}
