import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packaging.artifacts.Artifact;
import com.intellij.packaging.artifacts.ArtifactManager;
import com.intellij.packaging.impl.artifacts.ArtifactImpl;
import com.intellij.psi.*;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zou tairan
 * @since 2018/12/4
 */
public class SourceFileIconProvider extends RelatedItemLineMarkerProvider {
    private int count = 0;

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiIdentifier) {
            Object object = element.getParent().getParent();
            if (
                    (object instanceof PsiJavaFileImpl)
                            &&
                            !(((PsiJavaFileImpl) object).getOriginalFile().getFileType() instanceof JavaClassFileType)
            ) {
                Project project = element.getProject();
                Module module = ModuleUtil.findModuleForPsiElement(element);
                VirtualFile[] moduleCompiledOutputRoots =
                        CompilerModuleExtension.getInstance(module).getOutputRoots(false);
                ArrayList<String> outputUrls = new ArrayList<>();
                Artifact[] artifacts = ArtifactManager.getInstance(project).getArtifacts();
                if (artifacts.length > 0) {
                    for (int i = 0; i < artifacts.length; i++) {
                        outputUrls.add(artifacts[i].getOutputPath());
                    }
                }
                if (moduleCompiledOutputRoots.length > 0) {
                    for (int i = 0; i < moduleCompiledOutputRoots.length; i++) {
                        outputUrls.add(moduleCompiledOutputRoots[i].getPath());
                    }
                }
                ArrayList<PsiIdentifier> targetPsiIdentifiers = new ArrayList<>();
                for (int i = 0; i < outputUrls.size(); i++) {
                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File(
                            outputUrls.get(i)
                                    + "\\" + Utils.convertPackageNameToPath(((PsiJavaFileImpl) (element.getContainingFile())).getPackageName())
                                    + "\\" + element.getText() + ".class"));
                    if (virtualFile != null) {
                        ClsFileImpl psiFile = (ClsFileImpl) PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                        PsiClass[] psiClasses = psiFile.getClasses();
                        for (int j = 0; j < psiClasses.length; j++) {
                            if (psiClasses[j].getName().equals(element.getText())) {
                                targetPsiIdentifiers.add(psiClasses[j].getNameIdentifier());
                            }
                        }
                    }
                }
                if (targetPsiIdentifiers.size() > 0) {
                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(Icon.JUMP_TO_SOURCE_FILE).setTargets(targetPsiIdentifiers).setTooltipText("Jump To Source File");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}
