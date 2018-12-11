import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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
                String path = element.getProject().getBasePath();
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File(
                        Utils.getProjectSourcePath(element.getProject()) + "\\" + element.getText() + ".java"));
                if (virtualFile != null) {
                    PsiJavaFileImpl psiJavaFile =
                            (PsiJavaFileImpl) PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                    PsiClassImpl psiClass = (PsiClassImpl) psiJavaFile.getChildren()[1];
                    PsiIdentifier psiIdentifier = psiClass.getNameIdentifier();
                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(Icon.JUMP_TO_SOURCE_FILE).setTarget(psiIdentifier).setTooltipText("Jump To Source File");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}
