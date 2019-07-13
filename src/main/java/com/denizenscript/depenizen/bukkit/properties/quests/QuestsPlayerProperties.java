package com.denizenscript.depenizen.bukkit.properties.quests;

import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.depenizen.bukkit.bridges.QuestsBridge;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;
import com.denizenscript.denizen.objects.dPlayer;
import com.denizenscript.denizencore.objects.ElementTag;
import com.denizenscript.denizencore.objects.ListTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;

public class QuestsPlayerProperties implements Property {

    @Override
    public String getPropertyString() {
        return null;
    }

    @Override
    public String getPropertyId() {
        return "QuestsPlayer";
    }

    public static boolean describes(ObjectTag object) {
        return object instanceof dPlayer;
    }

    public static QuestsPlayerProperties getFrom(ObjectTag object) {
        if (!describes(object)) {
            return null;
        }
        else {
            return new QuestsPlayerProperties((dPlayer) object);
        }
    }

    public static final String[] handledTags = new String[] {
            "quests"
    };

    public static final String[] handledMechs = new String[] {
    }; // None

    private QuestsPlayerProperties(dPlayer player) {
        this.player = player;
        Quests quests = (Quests) QuestsBridge.instance.plugin;
        // This would be Quests.getInstance() but the developers of Quests did a stupid and broke that method.
        this.quester = quests.getQuester(player.getOfflinePlayer().getUniqueId());
    }

    dPlayer player = null;
    Quester quester;

    @Override
    public String getAttribute(Attribute attribute) {
        if (attribute == null) {
            return null;
        }

        if (attribute.startsWith("quests")) {
            attribute = attribute.fulfill(1);

            // <--[tag]
            // @attribute <p@player.quests.points>
            // @returns ElementTag(Number)
            // @description
            // Returns the number of quest points the player has.
            // @Plugin Depenizen, Quests
            // -->
            if (attribute.startsWith("points")) {
                if (quester.getBaseData().contains("quest-points")) {
                    return new ElementTag(quester.getBaseData().getInt("quest-points")).getAttribute(attribute.fulfill(1));
                }
                return new ElementTag("0").getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.quests.completed_names>
            // @returns ListTag
            // @description
            // Returns the names of quests the player has completed.
            // @Plugin Depenizen, Quests
            // -->
            if (attribute.startsWith("completed_names")) {
                ListTag list = new ListTag();
                for (String quest : quester.getCompletedQuests()) {
                    list.add(quest);
                }
                return list.getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.quests.active_names>
            // @returns ListTag
            // @description
            // Returns the names of quests the player has active.
            // @Plugin Depenizen, Quests
            // -->
            if (attribute.startsWith("active_names")) {
                ListTag list = new ListTag();
                for (Quest quest : quester.getCurrentQuests().keySet()) {
                    list.add(quest.getName());
                }
                return list.getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.quests.completed>
            // @returns ElementTag(Number)
            // @description
            // Returns the number of quests the player has completed.
            // @Plugin Depenizen, Quests
            // -->
            else if (attribute.startsWith("completed")) {
                return new ElementTag(quester.getCompletedQuests().size()).getAttribute(attribute.fulfill(1));
            }

            // <--[tag]
            // @attribute <p@player.quests.active>
            // @returns ElementTag(Number)
            // @description
            // Returns the number of quests the player has active.
            // @Plugin Depenizen, Quests
            // -->
            else if (attribute.startsWith("active")) {
                return new ElementTag(quester.getCurrentQuests().size()).getAttribute(attribute.fulfill(1));
            }
            return null;
        }
        return null;
    }

    @Override
    public void adjust(Mechanism mechanism) {
    }
}
