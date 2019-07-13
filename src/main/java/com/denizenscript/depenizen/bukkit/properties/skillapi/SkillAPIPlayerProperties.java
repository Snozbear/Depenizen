package com.denizenscript.depenizen.bukkit.properties.skillapi;

import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.depenizen.bukkit.objects.skillapi.SkillAPIClass;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import com.denizenscript.denizen.objects.dPlayer;
import com.denizenscript.denizencore.objects.DurationTag;
import com.denizenscript.denizencore.objects.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizen.utilities.blocks.OldMaterialsHelper;

public class SkillAPIPlayerProperties implements Property {

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "SkillAPIPlayer";
    }

    @Override
    public void adjust(Mechanism mechanism) {
        // None
    }

    public static boolean describes(ObjectTag pl) {
        return pl instanceof dPlayer;
    }

    public static SkillAPIPlayerProperties getFrom(ObjectTag pl) {
        if (!describes(pl)) {
            return null;
        }
        else {
            return new SkillAPIPlayerProperties((dPlayer) pl);
        }
    }

    public static final String[] handledTags = new String[] {
            "skillapi"
    };

    public static final String[] handledMechs = new String[] {
    }; // None

    public SkillAPIPlayerProperties(dPlayer player) {
        this.player = player;
    }

    dPlayer player;

    @Override
    public String getAttribute(Attribute attribute) {

        if (attribute.startsWith("skillapi")) {

            PlayerData data = SkillAPI.getPlayerData(player.getOfflinePlayer());
            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <p@player.skillapi.main_class>
            // @returns SkillAPIClass
            // @description
            // Returns the player's main SkillAPI class.
            // @Plugin Depenizen, SkillAPI
            // -->
            if (attribute.startsWith("main_class")) {
                if (data == null || data.getMainClass() == null) {
                    return null;
                }
                return new SkillAPIClass(data.getMainClass().getData()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.skillapi.in_class[<class>]>
            // @returns ElementTag(Boolean)
            // @description
            // Returns whether the player professes in the specified class. If none is specified, returns
            // whether the player professes in any class.
            // @Plugin Depenizen, SkillAPI
            // -->
            if (attribute.startsWith("in_class")) {
                if (attribute.hasContext(1)) {
                    SkillAPIClass testClass = SkillAPIClass.valueOf(attribute.getContext(1));
                    if (testClass == null) {
                        return null;
                    }
                    return new ElementTag(data.isExactClass(testClass.getRPGClass())).getAttribute(attribute.fulfill(1));
                }
                return new ElementTag(data.hasClass()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.skillapi.has_skill[<skill>]>
            // @returns ElementTag(Boolean)
            // @description
            // Returns whether the player has the specified skill.
            // @Plugin Depenizen, SkillAPI
            // -->
            if (attribute.startsWith("has_skill") && attribute.hasContext(1)) {
                return new ElementTag(data.hasSkill(attribute.getContext(1))).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.skillapi.mana>
            // @returns ElementTag(Decimal)
            // @description
            // Returns the player's current amount of mana.
            // @Plugin Depenizen, SkillAPI
            // -->
            if (attribute.startsWith("mana")) {
                return new ElementTag(data.getMana()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.skillapi.max_mana>
            // @returns ElementTag(Decimal)
            // @description
            // Returns the player's maximum amount of mana.
            // @Plugin Depenizen, SkillAPI
            // -->
            if (attribute.startsWith("max_mana")) {
                return new ElementTag(data.getMaxMana()).getAttribute(attribute.fulfill(1));
            }

            if (attribute.startsWith("class_") && attribute.hasContext(1)) {

                PlayerClass playerClass = null;
                SkillAPIClass skillAPIClass = SkillAPIClass.valueOf(attribute.getContext(1));
                if (skillAPIClass != null) {
                    String name = skillAPIClass.getRPGClass().getName();
                    for (PlayerClass plClass : data.getClasses()) {
                        if (plClass.getData().getName().equals(name)) {
                            playerClass = plClass;
                            break;
                        }
                    }
                }
                if (playerClass == null) {
                    return null;
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @description
                // Returns the amount of experience the player has toward the next level in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_exp")) {
                    return new ElementTag(playerClass.getExp()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_required_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @description
                // Returns the amount of experience the player must receive to get to the next level
                // in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_required_exp")) {
                    return new ElementTag(playerClass.getRequiredExp()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_total_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @description
                // Returns the total amount of experience the player has in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_total_exp")) {
                    return new ElementTag(playerClass.getTotalExp()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_level[<class>]>
                // @returns ElementTag(Number)
                // @description
                // Returns the level the player is in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_level")) {
                    return new ElementTag(playerClass.getLevel()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_points[<class>]>
                // @returns ElementTag(Number)
                // @description
                // Returns the number of skill points the player has in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_points")) {
                    return new ElementTag(playerClass.getPoints()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_maxed[<class>]>
                // @returns ElementTag(Boolean)
                // @description
                // Returns whether the player has hit maximum level in the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_maxed")) {
                    return new ElementTag(playerClass.isLevelMaxed()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_health[<class>]>
                // @returns ElementTag(Decimal)
                // @description
                // Returns the amount of health the player gets from the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_health")) {
                    return new ElementTag(playerClass.getHealth()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.class_mana[<class>]>
                // @returns ElementTag(Decimal)
                // @description
                // Returns the amount of mana the player gets from the specified class.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("class_mana")) {
                    return new ElementTag(playerClass.getMana()).getAttribute(attribute.fulfill(1));
                }
            }

            if (attribute.startsWith("skill_") && attribute.hasContext(1)) {

                PlayerSkill playerSkill = data.getSkill(attribute.getContext(1)); // TODO: SkillAPISkill object?
                if (playerSkill == null) {
                    return null;
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_bind[<skill>]>
                // @returns dMaterial
                // @description
                // Returns the material this skill is currently bound to.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_bind")) {
                    return OldMaterialsHelper.getMaterialFrom(playerSkill.getBind()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_level_requirement[<skill>]>
                // @returns ElementTag(Number)
                // @description
                // Returns the level the player must be to level up the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_level_req")) {
                    return new ElementTag(playerSkill.getLevelReq()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_level[<skill>]>
                // @returns ElementTag(Number)
                // @description
                // Returns the level the player is in the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_level")) {
                    return new ElementTag(playerSkill.getLevel()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_points[<skill>]>
                // @returns ElementTag(Number)
                // @description
                // Returns how many skill points the player has invested in the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_points")) {
                    return new ElementTag(playerSkill.getPoints()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_cost[<skill>]>
                // @returns ElementTag(Number)
                // @description
                // Returns the cost the for the player to level up the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_cost")) {
                    return new ElementTag(playerSkill.getCost()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_on_cooldown[<skill>]>
                // @returns ElementTag(Boolean)
                // @description
                // Returns whether the specified skill is currently on cooldown for the player.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_on_cooldown")) {
                    return new ElementTag(playerSkill.getLevel()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_cooldown[<skill>]>
                // @returns DurationTag
                // @description
                // Returns the remaining cooldown the player has in the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_cooldown")) {
                    return new DurationTag(playerSkill.getCooldown()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_maxed[<skill>]>
                // @returns ElementTag(Boolean)
                // @description
                // Returns whether the player has reached max level in the specified skill.
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_maxed")) {
                    return new ElementTag(playerSkill.isMaxed()).getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.skillapi.skill_status[<skill>]>
                // @returns ElementTag
                // @description
                // Returns the player's current status for the specified skill.
                // Can be: ON_COOLDOWN, MISSING_MANA, or READY
                // @Plugin Depenizen, SkillAPI
                // -->
                if (attribute.startsWith("skill_status")) {
                    return new ElementTag(playerSkill.getStatus().name()).getAttribute(attribute.fulfill(1));
                }
            }

        }

        return null;

    }
}
