import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;

/**
 * @author zou tairan
 * @since 2018/12/4
 */
public class ProviderTest extends RelatedItemLineMarkerProvider {
    private int count = 0;

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiIdentifier) {
//            if (element.getText().contains("Test")) {
            Object object = element.getParent().getParent();
            if (
                    (object instanceof PsiJavaFileImpl)
                            &&
                            !(((PsiJavaFileImpl) object).getOriginalFile().getFileType() instanceof JavaClassFileType)
            ) {
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File(
                        Utils.getProjectCompiledFolderPath(element.getProject())
                                + "\\" + element.getText() + ".class"));
                if (virtualFile != null) {
                    ClsFileImpl psiFile = (ClsFileImpl) PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                    PsiClass psiClass = (PsiClass) psiFile.getFirstChild();
                    PsiIdentifier psiIdentifier = psiClass.getNameIdentifier();
                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(TestIcon.JUMP_TO_SOURCE_FILE).setTarget(psiIdentifier).setTooltipText("Jump To Source File");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
//            }
        }
    }
}
