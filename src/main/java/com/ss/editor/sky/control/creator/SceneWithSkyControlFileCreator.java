package com.ss.editor.sky.control.creator;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.shadow.EdgeFilteringMode;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.extension.scene.SceneNode;
import com.ss.editor.extension.scene.filter.impl.EditableFXAAFilter;
import com.ss.editor.extension.scene.filter.impl.EditableHqDirectionalLightFromSceneShadowFilter;
import com.ss.editor.sky.control.PluginMessages;
import com.ss.editor.ui.component.creator.FileCreatorDescription;
import com.ss.editor.ui.component.creator.impl.EmptySceneCreator;
import com.ss.editor.util.EditorUtil;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;

/**
 * Creator to create a scene with {@link jme3utilities.sky.SkyControl}.
 *
 * @author JavaSaBr
 */
public class SceneWithSkyControlFileCreator extends EmptySceneCreator {

    public static final FileCreatorDescription DESCRIPTION = new FileCreatorDescription();

    static {
        DESCRIPTION.setFileDescription(PluginMessages.FILE_CREATOR_SCENE_WITH_SKY_CONTROL);
        DESCRIPTION.setConstructor(SceneWithSkyControlFileCreator::new);
    }

    @Override
    @FxThread
    protected @NotNull SceneNode createScene() {

        var assetManager = EditorUtil.getAssetManager();
        var camera = EditorUtil.getGlobalCamera();

        var skyControl = new SkyControl(assetManager, camera, 0.9f, true, true);
        skyControl.setCloudiness(1F);
        skyControl.setCloudsRate(2F);

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
        sceneNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        sceneNode.addControl(skyControl);
        sceneNode.addLight(ambientLight);
        sceneNode.addLight(directionalLight);

        skyControl.setEnabled(true);

        return sceneNode;
    }
}
