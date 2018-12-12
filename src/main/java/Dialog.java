import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.siyeh.ig.ui.TextField;

import javax.swing.*;
import java.awt.event.*;
// todo 181212 将输入框的文本换成选择文件夹
public class Dialog extends JDialog {
    private Project project;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField testTextField;
//    private FileTextField fileTextField;
    private TextFieldWithBrowseButton textFieldWithBrowseButton;

    public Dialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

//        fileTextField =
//                FileChooserFactory.getInstance().createFileTextField(FileChooserDescriptorFactory.createSingleFolderDescriptor(), null);
        textFieldWithBrowseButton = new TextFieldWithBrowseButton(testTextField,
                new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        project));
        textFieldWithBrowseButton.addBrowseFolderListener("browse test", "browse test description", project
                , FileChooserDescriptorFactory.createSingleFolderDescriptor());

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    private void onOK() {
        // add your code here
        OutputPathCache.outputPathMap.put(project.getName(), testTextField.getText());
        System.out.println(OutputPathCache.outputPathMap.entrySet().toString());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        testTextField = new JTextField();
        textFieldWithBrowseButton = new TextFieldWithBrowseButton(testTextField,
                new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        project));
        textFieldWithBrowseButton.addBrowseFolderListener("browse test", "browse test description", project
                , FileChooserDescriptorFactory.createSingleFolderDescriptor());
    }
}
