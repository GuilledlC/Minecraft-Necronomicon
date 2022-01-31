package net.guille_dlc.necronomicon.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class ModTeleporters implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModTeleporters(BlockPos pos, boolean insideDimension) {
        thisPos = pos;
        this.insideDimension = insideDimension;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld,
                              ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        double y = 61;

        if(!insideDimension)
            y = thisPos.getY();

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        int tries = 0;
        while(destWorld.getBlockState(destinationPos).getMaterial() != Material.AIR &&
                !destWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                destWorld.getBlockState(destinationPos.above()).getMaterial() != Material.AIR &&
                !destWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && tries < 25) {
            destinationPos = destinationPos.above(2);
            tries++;
        }

        entity.teleportTo(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        return entity;
    }
}
