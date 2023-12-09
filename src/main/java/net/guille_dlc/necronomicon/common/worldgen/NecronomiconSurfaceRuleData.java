package net.guille_dlc.necronomicon.common.worldgen;

import net.guille_dlc.necronomicon.common.biome.NecronomiconBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class NecronomiconSurfaceRuleData {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource COBBLESTONE = makeStateRule(Blocks.COBBLESTONE);
    private static final SurfaceRules.RuleSource CAVE_AIR = makeStateRule(Blocks.CAVE_AIR);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource BASALT = makeStateRule(Blocks.BASALT);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource dagon = SurfaceRules.isBiome(NecronomiconBiomes.MUDDY_WASTELAND);

        SurfaceRules.RuleSource mud = SurfaceRules.sequence(SurfaceRules.ifTrue(dagon, MUD), GRASS_BLOCK);
        SurfaceRules.RuleSource stone = SurfaceRules.sequence(
                SurfaceRules.ifTrue(dagon, COBBLESTONE));
        SurfaceRules.RuleSource basalt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(dagon, BASALT));
        SurfaceRules.RuleSource air = SurfaceRules.sequence(
                SurfaceRules.ifTrue(dagon, MUD), CAVE_AIR
        );
        SurfaceRules.RuleSource water = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, MUD), WATER
        );

        SurfaceRules.RuleSource surface =
                SurfaceRules.ifTrue(dagon, SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(5, false, CaveSurface.FLOOR), MUD));

        SurfaceRules.RuleSource highlandsNoise = SurfaceRules.sequence(
                //check if we're in the highlands
                SurfaceRules.ifTrue(
                        dagon,
                        SurfaceRules.ifTrue(
                                //check if we're on the surface
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        //mix coarse dirt and podzol like we used to
                                        SurfaceRules.ifTrue(surfaceNoiseAbove(0D), COARSE_DIRT),
                                        SurfaceRules.ifTrue(surfaceNoiseAbove(-1D), PODZOL)))));


        return SurfaceRules.sequence(surface, basalt);
        //return SurfaceRules.sequence(surface, stone);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double noise) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, noise / 8.25D, Double.MAX_VALUE);
    }
}
