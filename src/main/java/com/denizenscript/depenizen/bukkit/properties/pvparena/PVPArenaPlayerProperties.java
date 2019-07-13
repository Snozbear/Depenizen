package com.denizenscript.depenizen.bukkit.properties.pvparena;

import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.depenizen.bukkit.objects.pvparena.PVPArenaArena;
import com.denizenscript.denizen.objects.dPlayer;
import com.denizenscript.denizencore.objects.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import net.slipcor.pvparena.arena.ArenaPlayer;

public class PVPArenaPlayerProperties implements Property {

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "PVPArenaPlayer";
    }

    @Override
    public void adjust(Mechanism mechanism) {
        // None
    }

    public static boolean describes(ObjectTag object) {
        return object instanceof dPlayer;
    }

    public static PVPArenaPlayerProperties getFrom(ObjectTag object) {
        if (!describes(object)) {
            return null;
        }
        else {
            return new PVPArenaPlayerProperties((dPlayer) object);
        }
    }

    public static final String[] handledTags = new String[] {
            "pvparena"
    };

    public static final String[] handledMechs = new String[] {
    }; // None

    private PVPArenaPlayerProperties(dPlayer player) {
        this.player = ArenaPlayer.parsePlayer(player.getName());
    }

    ArenaPlayer player = null;

    @Override
    public String getAttribute(Attribute attribute) {

        if (attribute.startsWith("pvparena")) {

            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <p@player.pvparena.in_arena[<pvparena>]>
            // @returns ElementTag(Boolean)
            // @description
            // Returns true if the player is in the specified arena. If no arena is specified,
            // this returns true if the player is in any arena.
            // @Plugin Depenizen, PvP Arena
            // -->
            if (attribute.startsWith("inarena") || attribute.startsWith("in_arena")) {
                if (player.getArena() == null) {
                    return new ElementTag(false).getAttribute(attribute.fulfill(1));
                }
                if (attribute.hasContext(1)) {
                    PVPArenaArena a = PVPArenaArena.valueOf(attribute.getContext(1));
                    if (a == null) {
                        return null;
                    }
                    return new ElementTag(CoreUtilities.toLowerCase(player.getArena().getName())
                            .equals(CoreUtilities.toLowerCase(a.getArena().getName())))
                            .getAttribute(attribute.fulfill(1));
                }
                else {
                    return new ElementTag(player.getArena() != null)
                            .getAttribute(attribute.fulfill(1));
                }
            }

            // <--[tag]
            // @attribute <p@player.pvparena.class>
            // @returns ElementTag
            // @description
            // Returns the player's class if they're in an arena. Otherwise, returns null.
            // @Plugin Depenizen, PvP Arena
            // -->
            else if (attribute.startsWith("class")) {
                return new ElementTag(player.getArenaClass().getName()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.pvparena.is_ready>
            // @returns ElementTag(Boolean)
            // @description
            // Returns true if the player is ready.
            // @Plugin Depenizen, PvP Arena
            // -->
            else if (attribute.startsWith("isready") || attribute.startsWith("is_ready")) {
                return new ElementTag(player.getStatus().equals(ArenaPlayer.Status.READY))
                        .getAttribute(attribute.fulfill(1));
            }

            else if (attribute.startsWith("team")) {

                attribute = attribute.fulfill(1);

                // <--[tag]
                // @attribute <p@player.pvparena.team.player_count>
                // @returns ElementTag(Number)
                // @description
                // Returns the number of players in the team.
                // @Plugin Depenizen, PvP Arena
                // -->
                if (attribute.startsWith("playercount") || attribute.startsWith("player_count")) {
                    return new ElementTag(player.getArenaTeam().getTeamMembers().size())
                            .getAttribute(attribute.fulfill(1));
                }

                // <--[tag]
                // @attribute <p@player.pvparena.team.name>
                // @returns ElementTag
                // @description
                // Returns the player's team name if they're in an arena. Otherwise, returns null.
                // @Plugin Depenizen, PvP Arena
                // -->
                if (attribute.startsWith("name")) {
                    return new ElementTag(player.getArenaTeam().getName())
                            .getAttribute(attribute.fulfill(1));
                }
            }
        }
        return null;
    }
}
