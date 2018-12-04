import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;

/**
 * @author zou tairan
 * @since 2018/12/4
 */
public class ProviderTest extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiIdentifier) {
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File("D:\\Study" +
                    "\\Research\\SimpleTest\\out\\production\\SimpleTest\\Test.class"));
            ClsFileImpl psiFile = (ClsFileImpl)PsiManager.getInstance(element.getProject()).findFile(virtualFile);
            System.out.println("1");
        }
    }
}
