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

import org.bukkit.entity.EntityType;

import com.goncalomb.bukkit.mylib.namemaps.EntityTypeMap;
import com.goncalomb.bukkit.mylib.reflect.NBTTagCompound;
import com.goncalomb.bukkit.nbteditor.nbt.variable.BlockVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.BooleanVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.ByteVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.DoubleVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.FloatVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.IntegerVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.NBTGenericVariableContainer;
import com.goncalomb.bukkit.nbteditor.nbt.variable.RotationVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.ShortVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.StringVariable;
import com.goncalomb.bukkit.nbteditor.nbt.variable.VectorVariable;

public class EntityNBT extends EntityNBTBase {
	
	static {
		registerEntity(EntityType.PIG, BreedNBT.class);
		registerEntity(EntityType.SHEEP, BreedNBT.class);
		registerEntity(EntityType.COW, BreedNBT.class);
		registerEntity(EntityType.CHICKEN, BreedNBT.class);
		registerEntity(EntityType.MUSHROOM_COW, BreedNBT.class);
		registerEntity(EntityType.SQUID, MobNBT.class);
		
		registerEntity(EntityType.WOLF, TamedNBT.class);
		registerEntity(EntityType.OCELOT, TamedNBT.class);
		registerEntity(EntityType.HORSE, HorseNBT.class);
		
		registerEntity(EntityType.VILLAGER, VillagerNBT.class);
		registerEntity(EntityType.IRON_GOLEM, MobNBT.class);
		registerEntity(EntityType.SNOWMAN, MobNBT.class);
		
		registerEntity(EntityType.ZOMBIE, ZombieNBT.class);
		registerEntity(EntityType.PIG_ZOMBIE, ZombieNBT.class);
		registerEntity(EntityType.SLIME, SlimeNBT.class);
		registerEntity(EntityType.MAGMA_CUBE, SlimeNBT.class);
		registerEntity(EntityType.GHAST, MobNBT.class);
		registerEntity(EntityType.SKELETON, MobNBT.class);
		registerEntity(EntityType.CREEPER, MobNBT.class);
		registerEntity(EntityType.BAT, MobNBT.class);
		registerEntity(EntityType.BLAZE, MobNBT.class);
		registerEntity(EntityType.SPIDER, MobNBT.class);
		registerEntity(EntityType.CAVE_SPIDER, MobNBT.class);
		registerEntity(EntityType.GIANT, MobNBT.class);
		registerEntity(EntityType.ENDERMAN, MobNBT.class);
		registerEntity(EntityType.SILVERFISH, MobNBT.class);
		registerEntity(EntityType.WITCH, MobNBT.class);
		registerEntity(EntityType.GUARDIAN, MobNBT.class);
		registerEntity(EntityType.ENDERMITE, MobNBT.class);
		registerEntity(EntityType.RABBIT, MobNBT.class);

		registerEntity(EntityType.ENDER_DRAGON, MobNBT.class);
		registerEntity(EntityType.WITHER, MobNBT.class);
		
		registerEntity(EntityType.PRIMED_TNT, EntityNBT.class);
		registerEntity(EntityType.FALLING_BLOCK, FallingBlockNBT.class);
		registerEntity(EntityType.DROPPED_ITEM, DroppedItemNBT.class);
		registerEntity(EntityType.EXPERIENCE_ORB, XPOrbNBT.class);
		registerEntity(EntityType.ENDER_CRYSTAL, EntityNBT.class);
		registerEntity(EntityType.FIREWORK, FireworkNBT.class);

		registerEntity(EntityType.ARROW, EntityNBT.class);
		registerEntity(EntityType.ENDER_PEARL, EntityNBT.class);
		registerEntity(EntityType.THROWN_EXP_BOTTLE, EntityNBT.class);
		registerEntity(EntityType.SNOWBALL, EntityNBT.class);
		registerEntity(EntityType.EGG, EntityNBT.class);
		registerEntity(EntityType.SPLASH_POTION, ThrownPotionNBT.class);
		registerEntity(EntityType.FIREBALL, FireballNBT.class);
		registerEntity(EntityType.SMALL_FIREBALL, FireballNBT.class);
		registerEntity(EntityType.WITHER_SKULL, FireballNBT.class);
		
		registerEntity(EntityType.BOAT, EntityNBT.class);
		
		registerEntity(EntityType.MINECART, MinecartNBT.class);
		registerEntity(EntityType.MINECART_CHEST, MinecartChestNBT.class);
		registerEntity(EntityType.MINECART_FURNACE, MinecartNBT.class);
		registerEntity(EntityType.MINECART_HOPPER, MinecartHopperNBT.class);
		registerEntity(EntityType.MINECART_MOB_SPAWNER, MinecartSpawnerNBT.class);
		registerEntity(EntityType.MINECART_TNT, MinecartNBT.class);
		registerEntity(EntityType.MINECART_COMMAND, MinecartCommandNBT.class);
		
		
		NBTGenericVariableContainer variables = null;
		
		variables = new NBTGenericVariableContainer("Entity");
		variables.add("pos", new VectorVariable("Pos"));
		variables.add("vel", new VectorVariable("Motion"));
		variables.add("rot", new RotationVariable("Rotation"));
		variables.add("fall-distance", new FloatVariable("FallDistance", 0.0f));
		variables.add("fire", new ShortVariable("Fire"));
		variables.add("air", new ShortVariable("Air", (short) 0, (short) 200));
		variables.add("invulnerable", new BooleanVariable("Invulnerable"));
		registerVariables(EntityNBT.class, variables);
		
		variables = new NBTGenericVariableContainer("Pig");
		variables.add("saddle", new BooleanVariable("Saddle"));
		registerVariables(EntityType.PIG, variables);
		
		variables = new NBTGenericVariableContainer("Sheep");
		variables.add("sheared", new BooleanVariable("Saddle"));
		variables.add("color", new ByteVariable("Color", (byte) 0, (byte) 15));
		registerVariables(EntityType.SHEEP, variables);
		
		variables = new NBTGenericVariableContainer("Wolf");
		variables.add("angry", new BooleanVariable("Angry"));
		variables.add("collar-color", new ByteVariable("CollarColor", (byte) 0, (byte) 15));
		registerVariables(EntityType.WOLF, variables);
		
		variables = new NBTGenericVariableContainer("Ocelot");
		variables.add("cat-type", new IntegerVariable("CatType", 0, 3));
		registerVariables(EntityType.OCELOT, variables);
		
		
		variables = new NBTGenericVariableContainer("IronGolem");
		variables.add("player-created", new BooleanVariable("PlayerCreated"));
		registerVariables(EntityType.IRON_GOLEM, variables);
		
		
		variables = new NBTGenericVariableContainer("PigZombie");
		variables.add("anger", new ShortVariable("Anger"));
		registerVariables(EntityType.PIG_ZOMBIE, variables);
		
		variables = new NBTGenericVariableContainer("Ghast");
		variables.add("explosion-power", new IntegerVariable("ExplosionPower", 0, 25)); // Limited to 25
		registerVariables(EntityType.GHAST, variables);
		
		variables = new NBTGenericVariableContainer("Skeleton");
		variables.add("is-wither", new BooleanVariable("SkeletonType"));
		registerVariables(EntityType.SKELETON, variables);
		
		variables = new NBTGenericVariableContainer("Creeper");
		variables.add("powered", new BooleanVariable("powered"));
		variables.add("explosion-radius", new ByteVariable("ExplosionRadius", (byte) 0, (byte) 25)); // Limited to 25
		variables.add("fuse", new ShortVariable("Fuse", (short) 0));
		registerVariables(EntityType.CREEPER, variables);
		
		variables = new NBTGenericVariableContainer("Enderman");
		variables.add("block", new BlockVariable("carried", "carriedData", true));
		registerVariables(EntityType.ENDERMAN, variables);
		
		variables = new NBTGenericVariableContainer("Guardian");
		variables.add("elder", new BooleanVariable("Elder"));
		registerVariables(EntityType.GUARDIAN, variables);
		
		variables = new NBTGenericVariableContainer("Endermite");
		variables.add("lifetime", new IntegerVariable("Lifetime"));
		variables.add("player-spawned", new BooleanVariable("PlayerSpawned"));
		registerVariables(EntityType.ENDERMITE, variables);
		
		variables = new NBTGenericVariableContainer("Rabbit");
		variables.add("type", new IntegerVariable("RabbitType", 0, 99));
		registerVariables(EntityType.RABBIT, variables);
		
		
		variables = new NBTGenericVariableContainer("Wither");
		variables.add("invul-time", new IntegerVariable("Invul", 0));
		registerVariables(EntityType.WITHER, variables);
		
		
		variables = new NBTGenericVariableContainer("PrimedTNT");
		variables.add("fuse", new ByteVariable("Fuse", (byte) 0));
		registerVariables(EntityType.PRIMED_TNT, variables);
		
		
		variables = new NBTGenericVariableContainer("Arrow");
		variables.add("pickup", new ByteVariable("pickup", (byte) 0, (byte) 2));
		variables.add("player", new BooleanVariable("player"));
		variables.add("damage", new DoubleVariable("damage"));
		registerVariables(EntityType.ARROW, variables);
		
		variables = new NBTGenericVariableContainer("Enderpearl");
		variables.add("owner", new StringVariable("ownerName"));
		registerVariables(EntityType.ENDER_PEARL, variables);
		
		variables = new NBTGenericVariableContainer("LargeFireball");
		variables.add("explosion-power", new IntegerVariable("ExplosionPower", 0, 25)); // Limited to 25
		registerVariables(EntityType.FIREBALL, variables);
		
	}
	
	protected EntityNBT() {
		this(null);
	}
	
	protected EntityNBT(EntityType entityType) {
		super(entityType);
	}
	
	public void setPos(double x, double y, double z) {
		_data.setList("Pos", x, y, z);
	}
	
	public void removePos() {
		_data.remove("Pos");
	}
	
	public void setMotion(double x, double y, double z) {
		_data.setList("Motion", x, y, z);
	}
	
	public void removeMotion() {
		_data.remove("Motion");
	}
	
	public EntityNBT getRiding() {
		NBTTagCompound ridingData = _data.getCompound("Riding");
		if (ridingData != null) {
			return fromEntityType(EntityTypeMap.getByName(ridingData.getString("id")), ridingData);
		}
		return null;
	}
	
	public void setRiding(EntityNBT... riding) {
		if (riding == null || riding.length == 0) {
			_data.remove("Riding");
			return;
		}
		NBTTagCompound rider = _data;
		for (EntityNBT ride : riding) {
			NBTTagCompound rideData = ride._data.clone();
			rider.setCompound("Riding", rideData);
			rider = rideData;
		}
	}
	
}
