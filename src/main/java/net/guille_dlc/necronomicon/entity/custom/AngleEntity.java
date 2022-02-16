package net.guille_dlc.necronomicon.entity.custom;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.item.ModItems;
import net.guille_dlc.necronomicon.item.NecronomiconBookItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.JumpOnBed;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class AngleEntity extends Vindicator {
    static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_28528_) -> {
        return !p_28528_.hasPickUpDelay() && p_28528_.isAlive() && p_28528_.getItem().getItem() instanceof NecronomiconBookItem;
    };
    private static final String TAG_JOHNNY = "Johnny";
    static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_34082_) -> {
        return p_34082_ == Difficulty.NORMAL || p_34082_ == Difficulty.HARD;
    };
    boolean isJohnny;

    public AngleEntity(EntityType<? extends Vindicator> entityType, Level pLevel) {
        super(entityType, pLevel);
        this.setCanPickUpLoot(true);
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
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new AngleEntity.AngleJohnnyAttackGoal(this));
        /*this.goalSelector.addGoal(0, new UseItemGoal<>(this,
                new NecronomiconBookItem(new Item.Properties()).getDefaultInstance(),
                SoundEvents.BOOK_PAGE_TURN, (arg) -> {
            return true;
        }));*/
        this.goalSelector.addGoal(1, new AngleSearchForItemsGoal());
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 0.5F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F, 0.25F));
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        //this.setItemInHand(InteractionHand.MAIN_HAND, new NecronomiconBookItem(new Item.Properties()).getDefaultInstance());
        /*Random r = new Random();
        int p = Math.abs(r.nextInt()%56);
        if(p > 54)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        else if(p > 53)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        else if(p > 52)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        else if(p > 51)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        else if(p > 50)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        else if(p > 40)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        else if(p > 30)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        else if(p > 20)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        else if(p > 10)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        else if(p > 0)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));*/
    }

    /**Method stolen from the Fox class**/
    protected void pickUpItem(ItemEntity pEntity) {
        ItemStack itemstack = pEntity.getItem();
        if (this.canHoldItem(itemstack) && AngleEntity.ALLOWED_ITEMS.test(pEntity)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.dropItemStack(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(pEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(pEntity, itemstack.getCount());
            pEntity.discard();
        }
    }

    /**Method stolen from the Fox class**/
    private void dropItemStack(ItemStack pStack) {
        ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), pStack);
        this.level.addFreshEntity(itementity);
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

    /**Class stolen from the Fox class**/
    class AngleSearchForItemsGoal extends Goal {
        public AngleSearchForItemsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (!AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (AngleEntity.this.getTarget() == null && AngleEntity.this.getLastHurtByMob() == null) {
                if (AngleEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = AngleEntity.this.level.getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
                    return !list.isEmpty() && AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            List<ItemEntity> list = AngleEntity.this.level.getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
            ItemStack itemstack = AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                AngleEntity.this.getNavigation().moveTo(list.get(0), (double)1.05F);
            }

        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            List<ItemEntity> list = AngleEntity.this.level.getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
            if (!list.isEmpty()) {
                AngleEntity.this.getNavigation().moveTo(list.get(0), (double)1.1F);
            }

        }
    }
}
