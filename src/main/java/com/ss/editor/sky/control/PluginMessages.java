package com.ss.editor.sky.control;

import static com.ss.editor.plugin.api.messages.MessagesPluginFactory.getResourceBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

/**
 * The class with localised all plugin messages.
 *
 * @author JavaSaBr
 */
public interface PluginMessages {

    @NotNull ResourceBundle RESOURCE_BUNDLE = getResourceBundle(SkyControlEditorPlugin.class,
            "com/ss/editor/sky/control/messages/messages");

    @NotNull String MODEL_NODE_TREE_ACTION_CREATE_SKY_CONTROL = RESOURCE_BUNDLE.getString("ModelNodeTreeActionCreateSkyControl");
    @NotNull String MODEL_FILE_EDITOR_NODE_SKY_CONTROL = RESOURCE_BUNDLE.getString("ModelFileEditorNodeSkyControl");
    @NotNull String MODEL_FILE_EDITOR_NODE_SUN_AND_STARS = RESOURCE_BUNDLE.getString("ModelFileEditorNodeSunAndStars");
    @NotNull String MODEL_FILE_EDITOR_NODE_UPDATER = RESOURCE_BUNDLE.getString("ModelFileEditorNodeUpdater");

    @NotNull String MODEL_PROPERTY_START_OPTIONS = RESOURCE_BUNDLE.getString("ModelPropertyStarsOption");
    @NotNull String MODEL_PROPERTY_CLOUD_MODULATION = RESOURCE_BUNDLE.getString("ModelPropertyCloudModulation");
    @NotNull String MODEL_PROPERTY_LUNAR_PHASE = RESOURCE_BUNDLE.getString("ModelPropertyLunarPhase");
    @NotNull String MODEL_PROPERTY_CLOUDS_SPEED = RESOURCE_BUNDLE.getString("ModelPropertyCloudsSpeed");
    @NotNull String MODEL_PROPERTY_CLOUDINESS = RESOURCE_BUNDLE.getString("ModelPropertyCloudiness");
    @NotNull String MODEL_PROPERTY_CLOUDS_Y_OFFSET = RESOURCE_BUNDLE.getString("ModelPropertyCloudsYOffset");
    @NotNull String MODEL_PROPERTY_TOP_VERTICAL_ANGLE = RESOURCE_BUNDLE.getString("ModelPropertyTopVerticalAngle");

    @NotNull String MODEL_PROPERTY_OBSERVER_LATITUDE = RESOURCE_BUNDLE.getString("ModelPropertyObserverLatitude");
    @NotNull String MODEL_PROPERTY_SOLAR_LONGITUDE = RESOURCE_BUNDLE.getString("ModelPropertySolarLongitude");
    @NotNull String MODEL_PROPERTY_HOUR = RESOURCE_BUNDLE.getString("ModelPropertyHour");

    @NotNull String MODEL_PROPERTY_MAIN_LIGHT = RESOURCE_BUNDLE.getString("ModelPropertyMainLight");
    @NotNull String MODEL_PROPERTY_MAIN_MULTIPLIER = RESOURCE_BUNDLE.getString("ModelPropertyMainMultiplier");
    @NotNull String MODEL_PROPERTY_AMBIENT_LIGHT = RESOURCE_BUNDLE.getString("ModelPropertyAmbientLight");
    @NotNull String MODEL_PROPERTY_AMBIENT_MULTIPLIER = RESOURCE_BUNDLE.getString("ModelPropertyAmbientMultiplier");

    @NotNull String FILE_CREATOR_SCENE_WITH_SKY_CONTROL = RESOURCE_BUNDLE.getString("FileCreatorSceneWithSkyControl");
}
