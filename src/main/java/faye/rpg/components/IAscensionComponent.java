package faye.rpg.components;


import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public interface IAscensionComponent<T extends Component<EntityStore>> {

    Class<T> componentClass();

    String id();

    BuilderCodec<T> codec();
}
