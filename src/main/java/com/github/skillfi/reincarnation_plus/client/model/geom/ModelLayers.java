package com.github.skillfi.reincarnation_plus.client.model.geom;

import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import com.google.common.collect.Sets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class ModelLayers {
    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
    public static final ModelLayerLocation OGRE_INNER_ARMOR = registerInnerArmor("ogre");
    public static final ModelLayerLocation OGRE_OUTER_ARMOR = registerOuterArmor("ogre");

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(new ResourceLocation(ReincarnationPlusMod.MODID, pPath), pModel);
    }

    private static ModelLayerLocation register(String pPath) {
        return register(pPath, "main");
    }

    private static ModelLayerLocation register(String pPath, String pModel) {
        ModelLayerLocation modellayerlocation = createLocation(pPath, pModel);
        if (!ALL_MODELS.add(modellayerlocation)) {
            throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
        } else {
            return modellayerlocation;
        }
    }

    private static ModelLayerLocation registerInnerArmor(String pPath) {
        return register(pPath, "inner_armor");
    }

    private static ModelLayerLocation registerOuterArmor(String pPath) {
        return register(pPath, "outer_armor");
    }
}
