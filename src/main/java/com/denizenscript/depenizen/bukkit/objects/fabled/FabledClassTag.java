package com.denizenscript.depenizen.bukkit.objects.fabled;

import com.denizenscript.denizencore.utilities.CoreUtilities;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import studio.magemonkey.fabled.Fabled;
import studio.magemonkey.fabled.api.classes.FabledClass;
import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.TagContext;

public class FabledClassTag implements ObjectTag {

    // <--[ObjectType]
    // @name FabledClassTag
    // @prefix fabledclass
    // @base ElementTag
    // @format
    // The identity format for regions is <class_name>
    // For example, 'fabledclass@myclass'.
    //
    // @plugin Depenizen, Fabled
    // @description
    // A FabledClassTag represents a Fabled class.
    //
    // -->

    /////////////////////
    //   OBJECT FETCHER
    /////////////////

    @Fetchable("fabledclass")
    public static FabledClassTag valueOf(String string, TagContext context) {
        if (string.startsWith("fabledclass@")) {
            string = string.substring("fabledclass@".length());
        }
        FabledClass fabledClass = Fabled.getClass(string);
        if (fabledClass != null) {
            return new FabledClassTag(fabledClass);
        }
        return null;
    }

    public static boolean matches(String arg) {
        if (valueOf(arg, CoreUtilities.noDebugContext) != null) {
            return true;
        }

        return false;
    }

    /////////////////////
    //   CONSTRUCTORS
    /////////////////

    FabledClass fabledClass;

    public FabledClassTag(FabledClass fabledClass) {
        this.fabledClass = fabledClass;
    }

    public FabledClass getFabledClass() {
        return fabledClass;
    }

    /////////////////////
    //   ObjectTag Methods
    /////////////////

    private String prefix = "FabledClass";

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public ObjectTag setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String identify() {
        return "fabledclass@" + fabledClass.getName();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public String toString() {
        return identify();
    }

    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {

        // <--[tag]
        // @attribute <FabledClassTag.name>
        // @returns ElementTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the name of this Fabled class.
        // -->
        if (attribute.startsWith("name")) {
            return new ElementTag(fabledClass.getName()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.prefix_color>
        // @returns ElementTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the color of the prefix of this Fabled class.
        // -->
        if (attribute.startsWith("prefix_color")) {
            return new ElementTag(fabledClass.getPrefixColor().toString()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.class_prefix>
        // @returns ElementTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the prefix of this Fabled class.
        // -->
        else if (attribute.startsWith("class_prefix")) {
            return new ElementTag(fabledClass.getPrefix()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.needs_permission>
        // @returns ElementTag(Boolean)
        // @plugin Depenizen, Fabled
        // @description
        // Returns whether this Fabled class requires permission to profess as it.
        // -->
        else if (attribute.startsWith("needs_permission")) {
            return new ElementTag(fabledClass.isNeedsPermission()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.group_name>
        // @returns ElementTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the name of the group that this Fabled class falls into.
        // -->
        if (attribute.startsWith("group_name")) {
            return new ElementTag(fabledClass.getGroup()).getObjectAttribute(attribute.fulfill(1));
        }
        if (attribute.startsWith("group.name")) { // old
            Debug.echoError("Deprecation notice - please change 'FabledClassTag.group.name' to 'FabledClassTag.group_name'");
            return new ElementTag(fabledClass.getGroup()).getObjectAttribute(attribute.fulfill(2));
        }

        // <--[tag]
        // @attribute <FabledClassTag.has_parent>
        // @returns ElementTag(Boolean)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the whether this Fabled class has a parent class.
        // -->
        if (attribute.startsWith("has_parent")) {
            return new ElementTag(fabledClass.hasParent()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.parent>
        // @returns FabledClassTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the parent class of this Fabled class.
        // -->
        if (attribute.startsWith("parent")) {
            return new FabledClassTag(fabledClass.getParent()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.icon>
        // @returns ItemTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the item icon representing this Fabled class in menus.
        // -->
        if (attribute.startsWith("icon")) {
            return new ItemTag(fabledClass.getIcon()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.max_level>
        // @returns ElementTag(Number)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the maximum level that this Fabled class can reach.
        // -->
        if (attribute.startsWith("max_level")) {
            return new ElementTag(fabledClass.getMaxLevel()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.base_health>
        // @returns ElementTag(Decimal)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the base amount of health for this Fabled class.
        // -->
        if (attribute.startsWith("base_health")) {
            return new ElementTag(fabledClass.getBaseHealth()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.health_scale>
        // @returns ElementTag(Decimal)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the amount of health gained per level for this Fabled class.
        // -->
        if (attribute.startsWith("health_scale")) {
            return new ElementTag(fabledClass.getHealthScale()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.base_mana>
        // @returns ElementTag(Decimal)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the base amount of mana for this Fabled class.
        // -->
        if (attribute.startsWith("base_mana")) {
            return new ElementTag(fabledClass.getBaseMana()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.mana_scale>
        // @returns ElementTag(Decimal)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the amount of mana gained per level for this Fabled class.
        // -->
        if (attribute.startsWith("mana_scale")) {
            return new ElementTag(fabledClass.getManaScale()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.mana_name>
        // @returns ElementTag
        // @plugin Depenizen, Fabled
        // @description
        // Returns the alias for mana that this Fabled class uses.
        // -->
        if (attribute.startsWith("mana_name")) {
            return new ElementTag(fabledClass.getManaName()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.has_mana_regen>
        // @returns ElementTag(Boolean)
        // @plugin Depenizen, Fabled
        // @description
        // Returns whether this Fabled class has mana regeneration.
        // -->
        if (attribute.startsWith("has_mana_regen")) {
            return new ElementTag(fabledClass.hasManaRegen()).getObjectAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <FabledClassTag.mana_regen>
        // @returns ElementTag(Decimal)
        // @plugin Depenizen, Fabled
        // @description
        // Returns the amount of mana regeneration that this Fabled class has.
        // -->
        if (attribute.startsWith("mana_regen")) {
            return new ElementTag(fabledClass.getManaRegen()).getObjectAttribute(attribute.fulfill(1));
        }

        return new ElementTag(identify()).getObjectAttribute(attribute);

    }
}
