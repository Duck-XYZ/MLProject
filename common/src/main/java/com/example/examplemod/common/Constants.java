package com.example.examplemod.common;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "examplemod";
	public static final String MOD_NAME = "ExampleMod";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation resLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}