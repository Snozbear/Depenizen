package com.denizenscript.depenizen.bukkit.properties.fabled;

import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizen.objects.MaterialTag;
import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.depenizen.bukkit.objects.fabled.FabledClassTag;
import studio.magemonkey.fabled.api.player.PlayerData;
import studio.magemonkey.fabled.api.player.PlayerClass;
import studio.magemonkey.fabled.Fabled;
import studio.magemonkey.fabled.api.player.PlayerSkill;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.core.DurationTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;

public class FabledPlayerProperties implements Property {

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "FabledPlayer";
    }

    @Override
    public void adjust(Mechanism mechanism) {
        // None
    }

    public static boolean describes(ObjectTag pl) {
        return pl instanceof PlayerTag;
    }

    public static FabledPlayerProperties getFrom(ObjectTag pl) {
        if (!describes(pl)) {
            return null;
        }
        else {
            return new FabledPlayerProperties((PlayerTag) pl);
        }
    }

    public static final String[] handledTags = new String[] {
            "fabled"
    };

    public static final String[] handledMechs = new String[] {
    }; // None

    public FabledPlayerProperties(PlayerTag player) {
        this.player = player;
    }

    PlayerTag player;

    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {

        if (attribute.startsWith("fabled")) {

            PlayerData data = Fabled.getData(player.getOfflinePlayer());
            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <PlayerTag.fabled.main_class>
            // @returns FabledClassTag
            // @plugin Depenizen, Fabled
            // @description
            // Returns the player's main Fabled class.
            // -->
            if (attribute.startsWith("main_class")) {
                if (data == null || data.getMainClass() == null) {
                    return null;
                }
                return new FabledClassTag(data.getMainClass().getData()).getObjectAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <PlayerTag.fabled.in_class[<class>]>
            // @returns ElementTag(Boolean)
            // @plugin Depenizen, Fabled
            // @description
            // Returns whether the player professes in the specified class. If none is specified, returns
            // whether the player professes in any class.
            // -->
            if (attribute.startsWith("in_class")) {
                if (attribute.hasParam()) {
                    FabledClassTag testClass = attribute.paramAsType(FabledClassTag.class);
                    if (testClass == null) {
                        return null;
                    }
                    return new ElementTag(data.isExactClass(testClass.getFabledClass())).getObjectAttribute(attribute.fulfill(1));
                }
                return new ElementTag(data.hasClass()).getObjectAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <PlayerTag.fabled.has_skill[<skill>]>
            // @returns ElementTag(Boolean)
            // @plugin Depenizen, Fabled
            // @description
            // Returns whether the player has the specified skill.
            // -->
            if (attribute.startsWith("has_skill") && attribute.hasParam()) {
                return new ElementTag(data.hasSkill(attribute.getParam())).getObjectAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <PlayerTag.fabled.mana>
            // @returns ElementTag(Decimal)
            // @plugin Depenizen, Fabled
            // @description
            // Returns the player's current amount of mana.
            // -->
            if (attribute.startsWith("mana")) {
                return new ElementTag(data.getMana()).getObjectAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <PlayerTag.fabled.max_mana>
            // @returns ElementTag(Decimal)
            // @plugin Depenizen, Fabled
            // @description
            // Returns the player's maximum amount of mana.
            // -->
            if (attribute.startsWith("max_mana")) {
                return new ElementTag(data.getMaxMana()).getObjectAttribute(attribute.fulfill(1));
            }

            if (attribute.getAttribute(1).startsWith("class_") && attribute.hasParam()) {

                PlayerClass playerClass = null;
                FabledClassTag skillAPIClass = attribute.paramAsType(FabledClassTag.class);
                if (skillAPIClass != null) {
                    String name = skillAPIClass.getFabledClass().getName();
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
                // @attribute <PlayerTag.fabled.class_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the amount of experience the player has toward the next level in the specified class.
                // -->
                if (attribute.startsWith("class_exp")) {
                    return new ElementTag(playerClass.getExp()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_required_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the amount of experience the player must receive to get to the next level
                // in the specified class.
                // -->
                if (attribute.startsWith("class_required_exp")) {
                    return new ElementTag(playerClass.getRequiredExp()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_total_exp[<class>]>
                // @returns ElementTag(Decimal)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the total amount of experience the player has in the specified class.
                // -->
                if (attribute.startsWith("class_total_exp")) {
                    return new ElementTag(playerClass.getTotalExp()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_level[<class>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the level the player is in the specified class.
                // -->
                if (attribute.startsWith("class_level")) {
                    return new ElementTag(playerClass.getLevel()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_points[<class>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the number of skill points the player has in the specified class.
                // -->
                if (attribute.startsWith("class_points")) {
                    return new ElementTag(playerClass.getPoints()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_maxed[<class>]>
                // @returns ElementTag(Boolean)
                // @plugin Depenizen, Fabled
                // @description
                // Returns whether the player has hit maximum level in the specified class.
                // -->
                if (attribute.startsWith("class_maxed")) {
                    return new ElementTag(playerClass.isLevelMaxed()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_health[<class>]>
                // @returns ElementTag(Decimal)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the amount of health the player gets from the specified class.
                // -->
                if (attribute.startsWith("class_health")) {
                    return new ElementTag(playerClass.getHealth()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.class_mana[<class>]>
                // @returns ElementTag(Decimal)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the amount of mana the player gets from the specified class.
                // -->
                if (attribute.startsWith("class_mana")) {
                    return new ElementTag(playerClass.getMana()).getObjectAttribute(attribute.fulfill(1));
                }
            }

            if (attribute.getAttribute(1).startsWith("skill_") && attribute.hasParam()) {

                PlayerSkill playerSkill = data.getSkill(attribute.getParam()); // TODO: FabledSkill object?
                if (playerSkill == null) {
                    return null;
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_indicator[<skill>]>
                // @returns ItemTag
                // @plugin Depenizen, Fabled
                // @description
                // Returns the indicator item for the skill.
                // -->
                if (attribute.startsWith("skill_indicator")) {
                    return new ItemTag(playerSkill.getData().getIndicator()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_bind[<skill>]>
                // @returns MaterialTag
                // @plugin Depenizen, Fabled
                // @description
                // Returns the material this skill is currently bound to.
                // -->
                if (attribute.startsWith("skill_bind")) {
                    return new MaterialTag(playerSkill.getBind()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_level_requirement[<skill>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the level the player must be to level up the specified skill.
                // -->
                if (attribute.startsWith("skill_level_req")) {
                    return new ElementTag(playerSkill.getLevelReq()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_level[<skill>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the level the player is in the specified skill.
                // -->
                if (attribute.startsWith("skill_level")) {
                    return new ElementTag(playerSkill.getLevel()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_points[<skill>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns how many skill points the player has invested in the specified skill.
                // -->
                if (attribute.startsWith("skill_points")) {
                    return new ElementTag(playerSkill.getInvestedCost()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_cost[<skill>]>
                // @returns ElementTag(Number)
                // @plugin Depenizen, Fabled
                // @description
                // Returns the cost the for the player to level up the specified skill.
                // -->
                if (attribute.startsWith("skill_cost")) {
                    return new ElementTag(playerSkill.getCost()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_on_cooldown[<skill>]>
                // @returns ElementTag(Boolean)
                // @plugin Depenizen, Fabled
                // @description
                // Returns whether the specified skill is currently on cooldown for the player.
                // -->
                if (attribute.startsWith("skill_on_cooldown")) {
                    return new ElementTag(playerSkill.getLevel()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_cooldown[<skill>]>
                // @returns DurationTag
                // @plugin Depenizen, Fabled
                // @description
                // Returns the remaining cooldown the player has in the specified skill.
                // -->
                if (attribute.startsWith("skill_cooldown")) {
                    return new DurationTag(playerSkill.getCooldown()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_maxed[<skill>]>
                // @returns ElementTag(Boolean)
                // @plugin Depenizen, Fabled
                // @description
                // Returns whether the player has reached max level in the specified skill.
                // -->
                if (attribute.startsWith("skill_maxed")) {
                    return new ElementTag(playerSkill.isMaxed()).getObjectAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <PlayerTag.fabled.skill_status[<skill>]>
                // @returns ElementTag
                // @plugin Depenizen, Fabled
                // @description
                // Returns the player's current status for the specified skill.
                // Can be: ON_COOLDOWN, MISSING_MANA, or READY
                // -->
                if (attribute.startsWith("skill_status")) {
                    return new ElementTag(playerSkill.getStatus()).getObjectAttribute(attribute.fulfill(1));
                }
            }

        }

        return null;

    }
}
