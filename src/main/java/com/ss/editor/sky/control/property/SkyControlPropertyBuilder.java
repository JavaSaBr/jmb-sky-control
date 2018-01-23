package com.ss.editor.sky.control.property;

import static com.ss.editor.extension.property.EditablePropertyType.*;
import com.jme3.math.FastMath;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.extension.property.EditableProperty;
import com.ss.editor.extension.property.SimpleProperty;
import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.sky.control.PluginMessages;
import com.ss.editor.ui.control.property.builder.impl.EditableModelObjectPropertyBuilder;
import jme3utilities.sky.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The property builder to provide properties to edit {@link SkyControl}.
 *
 * @author JavaSaBr
 */
public class SkyControlPropertyBuilder extends EditableModelObjectPropertyBuilder {

    @NotNull
    private static final SkyControlPropertyBuilder INSTANCE = new SkyControlPropertyBuilder();

    @FromAnyThread
    public static @NotNull SkyControlPropertyBuilder getInstance() {
        return INSTANCE;
    }

    public SkyControlPropertyBuilder() {
        super(ModelChangeConsumer.class);
    }

    @Override
    @FxThread
    protected @Nullable List<EditableProperty<?, ?>> getProperties(@NotNull final Object object) {

        final List<EditableProperty<?, ?>> result = new ArrayList<>();

        if (object instanceof SkyControl) {

            final SkyControl control = (SkyControl) object;

            result.add(new SimpleProperty<>(BOOLEAN, PluginMessages.MODEL_PROPERTY_CLOUD_MODULATION,
                    control, SkyControl::getCloudModulation, SkyControl::setCloudModulation));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_CLOUDS_SPEED,
                    control, SkyControl::getCloudsRate, SkyControl::setCloudsRate));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_CLOUDINESS, 0.1F, 0, 1F,
                    control, c ->  c.getCloudLayer(0).getOpacity(), SkyControlCore::setCloudiness));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_CLOUDS_Y_OFFSET, 0.1F, 0, 1F,
                    control, SkyControl::getCloudsYOffset, SkyControl::setCloudsYOffset));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_TOP_VERTICAL_ANGLE, 0.1F, 0F, 1.784F,
                    control, SkyControl::getTopVerticalAngle, SkyControl::setTopVerticalAngle));
            result.add(new SimpleProperty<>(ENUM, PluginMessages.MODEL_PROPERTY_LUNAR_PHASE,
                    control, SkyControl::getPhase, SkyControl::setPhase));

        } else if (object instanceof SunAndStars) {

            final SunAndStars sunAndStars = (SunAndStars) object;

            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_OBSERVER_LATITUDE, 0.1F, -FastMath.HALF_PI, FastMath.HALF_PI,
                    sunAndStars, SunAndStars::getObserverLatitude, SunAndStars::setObserverLatitude));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_SOLAR_LONGITUDE, 0.1F, 0F, FastMath.TWO_PI,
                    sunAndStars, SunAndStars::getSolarLongitude, SunAndStars::setSolarLongitude));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_HOUR, 0.1F, 0F, Constants.hoursPerDay,
                    sunAndStars, SunAndStars::getHour, SunAndStars::setHour));

        }  else if (object instanceof Updater) {

            final Updater updater = (Updater) object;

            result.add(new SimpleProperty<>(DIRECTION_LIGHT_FROM_SCENE, PluginMessages.MODEL_PROPERTY_MAIN_LIGHT,
                    updater, Updater::getMainLight, Updater::setMainLight));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_MAIN_MULTIPLIER,
                    updater, Updater::getMainMultiplier, Updater::setMainMultiplier));
            result.add(new SimpleProperty<>(AMBIENT_LIGHT_FROM_SCENE, PluginMessages.MODEL_PROPERTY_AMBIENT_LIGHT,
                    updater, Updater::getAmbientLight, Updater::setAmbientLight));
            result.add(new SimpleProperty<>(FLOAT, PluginMessages.MODEL_PROPERTY_AMBIENT_MULTIPLIER,
                    updater, Updater::getAmbientMultiplier, Updater::setAmbientMultiplier));
        }

        return result;
    }
}
