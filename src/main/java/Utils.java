import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.CompilerProjectExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;

/**
 * @author zou tairan
 * @since 2018/12/10
 */
public class Utils {
    public static VirtualFile getProjectCompiledFolder(Project project) {
        return CompilerProjectExtension.getInstance(project).getCompilerOutput();
    }

    public static String getProjectCompiledFolderPath(Project project) {
        return CompilerProjectExtension.getInstance(project).getCompilerOutputUrl();
    }

    public static String getProjectSourcePath(Project project) {
        return ProjectRootManager.getInstance(project).getContentSourceRoots()[0].getPath();
    }

    public static String[] getModuleSourcePath(Module module) {
        return ModuleRootManager.getInstance(module).getSourceRootUrls();
    }

    public static String getModuleCompiledFolderPath(Module module) {
        return CompilerModuleExtension.getInstance(module).getCompilerOutputUrl();
    }
}
