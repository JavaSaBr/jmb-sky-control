package com.ss.editor.sky.control.creator;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.shadow.EdgeFilteringMode;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.extension.property.EditablePropertyType;
import com.ss.editor.extension.scene.SceneNode;
import com.ss.editor.extension.scene.filter.impl.EditableFXAAFilter;
import com.ss.editor.extension.scene.filter.impl.EditableHqDirectionalLightFromSceneShadowFilter;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.sky.control.PluginMessages;
import com.ss.editor.ui.component.creator.FileCreatorDescriptor;
import com.ss.editor.ui.component.creator.impl.EmptySceneCreator;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.common.util.array.Array;
import jme3utilities.sky.SkyControl;
import jme3utilities.sky.StarsOption;
import org.jetbrains.annotations.NotNull;

/**
 * Creator to create a scene with {@link jme3utilities.sky.SkyControl}.
 *
 * @author JavaSaBr
 */
public class SceneWithSkyControlFileCreator extends EmptySceneCreator {

    public static final FileCreatorDescriptor DESCRIPTOR = new FileCreatorDescriptor(
            PluginMessages.FILE_CREATOR_SCENE_WITH_SKY_CONTROL,
            SceneWithSkyControlFileCreator::new
    );

    private static final String PROP_STAR_OPTIONS = "starOptions";
    private static final String PROP_CLOUDINESS = "cloudiness";
    private static final String PROP_CLOUDS_RATE = "cloudsRate";

    @Override
    @FromAnyThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        var result = Array.ofType(PropertyDefinition.class);
        result.add(new PropertyDefinition(EditablePropertyType.ENUM,
                PluginMessages.MODEL_PROPERTY_START_OPTIONS, PROP_STAR_OPTIONS, StarsOption.TwoDomes));
        result.add(new PropertyDefinition(EditablePropertyType.FLOAT,
                PluginMessages.MODEL_PROPERTY_CLOUDINESS, PROP_CLOUDINESS, 1F, 0F, 1F));
        result.add(new PropertyDefinition(EditablePropertyType.FLOAT,
                PluginMessages.MODEL_PROPERTY_CLOUDS_SPEED, PROP_CLOUDS_RATE, 2F));

        return result;
    }

    @Override
    @FxThread
    protected @NotNull SceneNode createScene() {

        var assetManager = EditorUtil.getAssetManager();
        var camera = EditorUtil.getGlobalCamera();

        var starsOption = vars.getEnum(PROP_STAR_OPTIONS, StarsOption.class);
        var cloudiness = vars.getFloat(PROP_CLOUDINESS);
        var cloudsRate = vars.getFloat(PROP_CLOUDS_RATE);

        var skyControl = new SkyControl(assetManager, camera, 0.9f, starsOption, true);
        skyControl.setCloudiness(cloudiness);
        skyControl.setCloudsRate(cloudsRate);

        var sunAndStars = skyControl.getSunAndStars();
        sunAndStars.setHour(12);

        var directionalLight = new DirectionalLight();
        var ambientLight = new AmbientLight();

        var updater = skyControl.getUpdater();
        updater.setAmbientLight(ambientLight);
        updater.setMainLight(directionalLight);

        var shadowFilter = new EditableHqDirectionalLightFromSceneShadowFilter();
        shadowFilter.setRenderBackFacesShadows(true);
        shadowFilter.setShadowIntensity(0.7F);
        shadowFilter.setEdgeFilteringMode(EdgeFilteringMode.PCF8);
        shadowFilter.setLight(directionalLight);

        var sceneNode = super.createScene();
        sceneNode.addFilter(new EditableFXAAFilter());
        sceneNode.addFilter(shadowFilter);
        sceneNode.setShadowMode(ShadowMode.CastAndReceive);
        sceneNode.addControl(skyControl);
        sceneNode.addLight(ambientLight);
        sceneNode.addLight(directionalLight);

        skyControl.setEnabled(true);

        return sceneNode;
    }
}
