package faye.rpg.modules.stats.data;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.codecs.EnumCodec;

public enum StatId {
    Vitality,
    Strength,
    Wisdom,
    Intelligence,
    Defense,
    Dexterity,
    Luck;

    public static final Codec<StatId> CODEC = new EnumCodec<>(StatId.class, EnumCodec.EnumStyle.CAMEL_CASE);
}
