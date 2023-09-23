package net.darthun.scotchmod.utils;

import net.darthun.scotchmod.ScotchMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items{
        //Dont forget to add json as well in resources/scotchmod/tags/items
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(ScotchMod.MOD_ID,name));
        }
        private static TagKey<Item> forgetag(String name){
            return ItemTags.create(new ResourceLocation("forge",name));
        }
    }
    public static class Blocks{
        //Dont forget to add json as well in resources/scotchmod/tags/blocks
        public static final TagKey<Block> PEATY_BLOCKS = tag("peaty_blocks");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(ScotchMod.MOD_ID,name));
        }
        private static TagKey<Block> forgetag(String name){
            return BlockTags.create(new ResourceLocation("forge",name));
        }

    }
}
