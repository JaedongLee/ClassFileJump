import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zou tairan
 * @since 2018/12/7
 */
public class ClassFileIconProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiIdentifier) {
            Object object = element.getParent().getParent();
            if (
                    (object instanceof PsiJavaFileImpl)
                            && (((PsiJavaFileImpl) object).getOriginalFile().getFileType() instanceof JavaClassFileType)
            ) {
                Project project = element.getProject();
                ArrayList<VirtualFile> sourceFiles = new ArrayList<>();
                VirtualFile[] projectContentSourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
                for (int i = 0; i < projectContentSourceRoots.length; i++) {
                    VirtualFile temp = LocalFileSystem.getInstance().findFileByIoFile(new File(
                            projectContentSourceRoots[i].getPath()
                                    + "\\" + Utils.convertPackageNameToPath(((PsiJavaFileImpl) (element.getContainingFile())).getPackageName())
                                    + "\\" + element.getContainingFile().getName()));
                    if (temp != null) {
                        sourceFiles.add(temp);
                    }
                }
                ArrayList<PsiIdentifier> psiIdentifiers = new ArrayList<>();
                if (sourceFiles.size() > 0) {
                    for (int i = 0; i < sourceFiles.size(); i++) {
                        PsiJavaFileImpl psiJavaFile =
                                (PsiJavaFileImpl) PsiManager.getInstance(element.getProject()).findFile(sourceFiles.get(i));
                        PsiClass[] psiClass = psiJavaFile.getClasses();
                        for (int j = 0; j < psiClass.length; j++) {
                            if (psiClass[j].getName().equals(element.getText())) {
                                psiIdentifiers.add(psiClass[j].getNameIdentifier());
                            }
                        }
                    }
                }
                if (psiIdentifiers.size() > 0) {
                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(Icon.JUMP_TO_SOURCE_FILE).setTargets(psiIdentifiers).setTooltipText("Jump To Source File");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}
