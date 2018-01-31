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
import jme3utilities.sky.SunAndStars;
import jme3utilities.sky.Updater;
import org.jetbrains.annotations.NotNull;

/**
 * Creator to create a scene with {@link jme3utilities.sky.SkyControl}.
 *
 * @author JavaSaBr
 */
public class SceneWithSkyControlFileCreator extends EmptySceneCreator {

    @NotNull
    public static final FileCreatorDescription DESCRIPTION = new FileCreatorDescription();

    static {
        DESCRIPTION.setFileDescription(PluginMessages.FILE_CREATOR_SCENE_WITH_SKY_CONTROL);
        DESCRIPTION.setConstructor(SceneWithSkyControlFileCreator::new);
    }

    @Override
    @FxThread
    protected @NotNull SceneNode createScene() {

        final SkyControl control = new SkyControl(EditorUtil.getAssetManager(), EditorUtil.getGlobalCamera(),
                0.9f, true, true);
        control.setCloudiness(1F);
        control.setCloudsRate(2F);

        final SunAndStars sunAndStars = control.getSunAndStars();
        sunAndStars.setHour(12);

        final DirectionalLight directionalLight = new DirectionalLight();
        final AmbientLight ambientLight = new AmbientLight();

        final Updater updater = control.getUpdater();
        updater.setAmbientLight(ambientLight);
        updater.setMainLight(directionalLight);

        final EditableHqDirectionalLightFromSceneShadowFilter shadowFilter = new EditableHqDirectionalLightFromSceneShadowFilter();
        shadowFilter.setRenderBackFacesShadows(true);
        shadowFilter.setShadowIntensity(0.7F);
        shadowFilter.setEdgeFilteringMode(EdgeFilteringMode.PCF8);
        shadowFilter.setLight(directionalLight);

        final SceneNode sceneNode = super.createScene();
        sceneNode.addFilter(new EditableFXAAFilter());
        sceneNode.addFilter(shadowFilter);
        sceneNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        sceneNode.addControl(control);
        sceneNode.addLight(ambientLight);
        sceneNode.addLight(directionalLight);

        control.setEnabled(true);

        return sceneNode;
    }
}
