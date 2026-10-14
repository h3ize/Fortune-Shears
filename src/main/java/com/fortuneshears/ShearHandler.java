package com.fortuneshears;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class ShearHandler {
    private static final Map<DyeColor, Item> WOOL = new EnumMap<>(DyeColor.class);

    static {
        WOOL.put(DyeColor.WHITE, Items.WHITE_WOOL);
        WOOL.put(DyeColor.ORANGE, Items.ORANGE_WOOL);
        WOOL.put(DyeColor.MAGENTA, Items.MAGENTA_WOOL);
        WOOL.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_WOOL);
        WOOL.put(DyeColor.YELLOW, Items.YELLOW_WOOL);
        WOOL.put(DyeColor.LIME, Items.LIME_WOOL);
        WOOL.put(DyeColor.PINK, Items.PINK_WOOL);
        WOOL.put(DyeColor.GRAY, Items.GRAY_WOOL);
        WOOL.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_WOOL);
        WOOL.put(DyeColor.CYAN, Items.CYAN_WOOL);
        WOOL.put(DyeColor.PURPLE, Items.PURPLE_WOOL);
        WOOL.put(DyeColor.BLUE, Items.BLUE_WOOL);
        WOOL.put(DyeColor.BROWN, Items.BROWN_WOOL);
        WOOL.put(DyeColor.GREEN, Items.GREEN_WOOL);
        WOOL.put(DyeColor.RED, Items.RED_WOOL);
        WOOL.put(DyeColor.BLACK, Items.BLACK_WOOL);
    }

    private ShearHandler() {
    }

    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Sheep sheep)) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof ShearsItem)) {
            return;
        }
        Level level = event.getLevel();
        int fortune = fortuneLevel(level, stack);
        if (fortune <= 0 || !sheep.readyForShearing()) {
            return;
        }

        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        event.setCanceled(true);
        if (level.isClientSide) {
            return;
        }

        Player player = event.getEntity();
        sheep.shear(SoundSource.PLAYERS);
        sheep.gameEvent(GameEvent.SHEAR, player);

        int extra = Config.WOOL_PER_LEVEL.get() * (fortune + level.random.nextInt(fortune + 1));
        Item wool = WOOL.getOrDefault(sheep.getColor(), Items.WHITE_WOOL);
        while (extra > 0) {
            int count = Math.min(extra, 64);
            sheep.spawnAtLocation(new ItemStack(wool, count), 1.0F);
            extra -= count;
        }

        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));
    }

    private static int fortuneLevel(Level level, ItemStack stack) {
        Holder<Enchantment> fortune = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.FORTUNE);
        return EnchantmentHelper.getItemEnchantmentLevel(fortune, stack);
    }
}
