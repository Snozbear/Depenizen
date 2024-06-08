package com.denizenscript.depenizen.bukkit.events.fabled;

import studio.magemonkey.fabled.api.event.PlayerSkillUnlockEvent;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FabledPlayerUnlocksSkillScriptEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // fabled player unlocks <'skill'>
    //
    // @Location true
    //
    // @Triggers when a player unlocks a skill in Fabled.
    //
    // @Context
    // <context.skill_name> returns the name of the skill unlocked.
    //
    // @Determine
    // None
    //
    // @Plugin Depenizen, Fabled
    //
    // @Player Always.
    //
    // @Group Depenizen
    //
    // -->

    public FabledPlayerUnlocksSkillScriptEvent() {
        registerCouldMatcher("fabled player unlocks <'skill'>");
    }

    public PlayerSkillUnlockEvent event;
    public PlayerTag player;
    public ElementTag skill;

    @Override
    public boolean matches(ScriptPath path) {
        String skill = path.eventArgLowerAt(3);

        if (!skill.equals("skill") && !skill.equals(CoreUtilities.toLowerCase(this.skill.asString()))) {
            return false;
        }

        if (!runInCheck(path, player.getLocation())) {
            return false;
        }

        return super.matches(path);
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @Override
    public ObjectTag getContext(String name) {
        if (name.equals("skill_name")) {
            return skill;
        }
        return super.getContext(name);
    }

    @EventHandler
    public void onFabledPlayerUnlocksSkill(PlayerSkillUnlockEvent event) {
        if (!EntityTag.isPlayer(event.getPlayerData().getPlayer())) {
            return;
        }
        player = PlayerTag.mirrorBukkitPlayer(event.getPlayerData().getPlayer());
        skill = new ElementTag(event.getUnlockedSkill().getData().getName());
        this.event = event;
        fire(event);
    }
}
