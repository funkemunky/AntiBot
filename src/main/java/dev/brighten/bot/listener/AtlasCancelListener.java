package dev.brighten.bot.listener;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import dev.brighten.bot.Antibot;
import dev.brighten.bot.handler.User;
import lombok.val;

@Init
public class AtlasCancelListener implements AtlasListener {

    @Listen
    public void onReceive(PacketReceiveEvent event) {
        switch(event.getType()) {
            case Packet.Client.BLOCK_DIG:
            case Packet.Client.BLOCK_PLACE:
            case Packet.Client.BLOCK_PLACE_1_9:
            case Packet.Client.LOOK:
            case Packet.Client.CHAT:
            case Packet.Client.ARM_ANIMATION:
            case Packet.Client.USE_ENTITY:
            case Packet.Client.CREATIVE_SLOT:
                val user = Antibot.INSTANCE.userHandler.getUser(event.getPlayer().getUniqueId());

                if(user.isPresent() && !user.get().confirmed) {
                    event.setCancelled(true);
                }
                break;
        }
    }
}
