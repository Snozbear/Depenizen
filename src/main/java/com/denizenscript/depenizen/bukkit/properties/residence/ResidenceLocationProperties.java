package com.denizenscript.depenizen.bukkit.properties.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.depenizen.bukkit.objects.residence.dResidence;
import com.denizenscript.denizen.objects.dLocation;
import com.denizenscript.denizencore.objects.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;

public class ResidenceLocationProperties implements Property {

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "ResidenceLocation";
    }

    public static boolean describes(ObjectTag object) {
        return object instanceof dLocation;
    }

    public static ResidenceLocationProperties getFrom(ObjectTag object) {
        if (!describes(object)) {
            return null;
        }
        else {
            return new ResidenceLocationProperties((dLocation) object);
        }
    }

    ///////////////////
    // Instance Fields and Methods
    /////////////

    public static final String[] handledTags = new String[] {
            "has_residence", "residence"
    };

    public static final String[] handledMechs = new String[] {
    }; // None

    private ResidenceLocationProperties(dLocation location) {
        this.location = location;
    }

    dLocation location;

    @Override
    public String getAttribute(Attribute attribute) {
        // <--[tag]
        // @attribute <l@location.has_residence>
        // @returns ElementTag(Boolean)
        // @description
        // Returns if the location has a residence.
        // @Plugin Depenizen, Residence
        // -->
        if (attribute.startsWith("has_residence")) {
            ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
            return new ElementTag(res != null).getAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <l@location.residence>
        // @returns dResidence
        // @description
        // Returns the residence contained by this location.
        // @Plugin Depenizen, Residence
        // -->
        if (attribute.startsWith("residence")) {
            ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);
            if (res != null) {
                return new dResidence(res).getAttribute(attribute.fulfill(1));
            }
            return null;
        }

        return null;
    }

    @Override
    public void adjust(Mechanism mechanism) {
        ElementTag value = mechanism.getValue();
    }
}
