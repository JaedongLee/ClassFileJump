import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author zoutairan
 * @since 2018/12/11
 */
public class Action extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project currentProject = e.getProject();
        Dialog dialogTest = new Dialog();
        dialogTest.setProject(currentProject);
        dialogTest.pack();
        dialogTest.setVisible(true);
    }
}
