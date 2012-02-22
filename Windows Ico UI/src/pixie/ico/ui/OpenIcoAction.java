/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pixie.ico.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(category = "File",
id = "pixie.ico.ui.OpenIcoAction")
@ActionRegistration(displayName = "#CTL_OpenIcoAction")
@ActionReferences({
	@ActionReference(path = "Menu/File", position = 1200)
})
@Messages("CTL_OpenIcoAction=Open Ico File...")
public final class OpenIcoAction implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Frame frame = WindowManager.getDefault().getMainWindow();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(""));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int retVal = chooser.showSaveDialog(frame);
		if (retVal != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		File target = chooser.getSelectedFile();
		IcoTopComponent itc = IcoTopComponent.findOpen(target);
		if (itc == null)
		{
			itc = new IcoTopComponent();
			itc.setFile(target);
			itc.open();
		}
		itc.requestActive();
	}
}
