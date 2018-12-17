import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author zoutairan
 * @since 2018/12/17
 */
public class SettingStoreService implements PersistentStateComponent<SettingStoreService.State> {
    static class State {
        public List<String> outputList;
    }

    State settingState;

    @Nullable
    @Override
    public State getState() {
        return settingState;
    }

    @Override
    public void loadState(@NotNull State state) {
        settingState = state;
    }
}
