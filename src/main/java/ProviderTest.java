import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import com.intellij.psi.impl.compiled.ClsMemberImpl;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.tree.java.JavaFileElement;
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
            if (element.getParent() instanceof PsiClassImpl) {
                if (!(((PsiJavaFileImpl) (element.getParent().getParent())).getOriginalFile().getFileType() instanceof JavaClassFileType)) {
                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File("D:\\Study" +
                            "\\Research\\SimpleTest\\out\\production\\SimpleTest\\Test.class"));
                    ClsFileImpl psiFile = (ClsFileImpl) PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                    PsiClass psiClass = (PsiClass) psiFile.getFirstChild();
                    PsiIdentifier psiIdentifier = psiClass.getNameIdentifier();
                    NavigationGutterIconBuilder<PsiElement> builder =
                            NavigationGutterIconBuilder.create(TestIcon.FILE).setTarget(psiIdentifier).setTooltipText("Provider Test");
                    result.add(builder.createLineMarkerInfo(element));
                    System.out.println("1");
                }
            }
        }
    }
}
