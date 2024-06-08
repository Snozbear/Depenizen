package com.denizenscript.depenizen.bukkit.bridges;

import com.denizenscript.depenizen.bukkit.properties.fabled.FabledPlayerProperties;
import com.denizenscript.depenizen.bukkit.Bridge;
import com.denizenscript.depenizen.bukkit.events.fabled.FabledPlayerLevelsUpScriptEvent;
import com.denizenscript.depenizen.bukkit.events.fabled.FabledPlayerUnlocksSkillScriptEvent;
import com.denizenscript.depenizen.bukkit.events.fabled.FabledPlayerUpgradesSkillScriptEvent;
import com.denizenscript.depenizen.bukkit.objects.fabled.FabledClassTag;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.depenizen.bukkit.events.fabled.FabledPlayerDowngradesSkillScriptEvent;
import com.denizenscript.denizencore.events.ScriptEvent;
import com.denizenscript.denizencore.objects.ObjectFetcher;
import com.denizenscript.denizencore.objects.properties.PropertyParser;

public class FabledBridge extends Bridge {

    @Override
    public void init() {
        ObjectFetcher.registerWithObjectFetcher(FabledClassTag.class);
        PropertyParser.registerProperty(FabledPlayerProperties.class, PlayerTag.class);
        ScriptEvent.registerScriptEvent(FabledPlayerUnlocksSkillScriptEvent.class);
        ScriptEvent.registerScriptEvent(FabledPlayerUpgradesSkillScriptEvent.class);
        ScriptEvent.registerScriptEvent(FabledPlayerDowngradesSkillScriptEvent.class);
        ScriptEvent.registerScriptEvent(FabledPlayerLevelsUpScriptEvent.class);
    }
}
