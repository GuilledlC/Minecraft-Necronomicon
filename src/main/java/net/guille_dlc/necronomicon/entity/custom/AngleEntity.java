package net.guille_dlc.necronomicon.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

public class AngleEntity extends Vindicator {
    private static final String TAG_JOHNNY = "Johnny";
    static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_34082_) -> {
        return p_34082_ == Difficulty.NORMAL || p_34082_ == Difficulty.HARD;
    };
    boolean isJohnny;

    public AngleEntity(EntityType<? extends Vindicator> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.35F).add(Attributes.FOLLOW_RANGE, 12.0D).add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AngleEntity.AngleBreakDoorGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new AngleEntity.AngleMeleeAttackGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new AngleEntity.AngleJohnnyAttackGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        /*if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        }*/
        Random r = new Random();
        int p = Math.abs(r.nextInt()%6);
        if(p > 4)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        else if(p > 3)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        else if(p > 2)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        else if(p > 1)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        else if(p > 0)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        System.out.println(p);
    }

    static class AngleBreakDoorGoal extends BreakDoorGoal {
        public AngleBreakDoorGoal(Mob p_34112_) {
            super(p_34112_, 6, AngleEntity.DOOR_BREAKING_PREDICATE);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            AngleEntity angle = (AngleEntity) this.mob;
            return angle.hasActiveRaid() && super.canContinueToUse();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            AngleEntity angle = (AngleEntity) this.mob;
            return angle.hasActiveRaid() && angle.random.nextInt(reducedTickDelay(10)) == 0 && super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }

    static class AngleJohnnyAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public AngleJohnnyAttackGoal(AngleEntity p_34117_) {
            super(p_34117_, LivingEntity.class, 0, true, true, LivingEntity::attackable);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return ((AngleEntity)this.mob).isJohnny && super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }

    class AngleMeleeAttackGoal extends MeleeAttackGoal {
        public AngleMeleeAttackGoal(AngleEntity p_34123_) {
            super(p_34123_, 1.0D, false);
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            if (this.mob.getVehicle() instanceof Ravager) {
                float f = this.mob.getVehicle().getBbWidth() - 0.1F;
                return (double)(f * 2.0F * f * 2.0F + pAttackTarget.getBbWidth());
            } else {
                return super.getAttackReachSqr(pAttackTarget);
            }
        }
    }
}
