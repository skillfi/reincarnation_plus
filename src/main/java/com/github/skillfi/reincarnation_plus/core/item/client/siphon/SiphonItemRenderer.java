package com.github.skillfi.reincarnation_plus.core.item.client.siphon;

import com.github.skillfi.reincarnation_plus.core.item.SiphonItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SiphonItemRenderer extends GeoItemRenderer<SiphonItem> {
    public SiphonItemRenderer() {
        super(new SiphonItemModel());
    }
}
