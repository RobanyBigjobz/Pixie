/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pixie.pecoff.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(category = "File",
id = "pixie.pecoff.ui.OpenPeCoffAction")
@ActionRegistration(displayName = "#CTL_OpenPeCoffAction")
@ActionReferences({
	@ActionReference(path = "Menu/File", position = 1300)
})
@Messages("CTL_OpenPeCoffAction=Open PE/COFF...")
public final class OpenPeCoffAction implements ActionListener
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
		PeCoffTopComponent ptc = PeCoffTopComponent.findOpen(target);
		if (ptc == null)
		{
			ptc = new PeCoffTopComponent();
			ptc.setFile(target);
			ptc.open();
		}
		ptc.requestActive();
	}
}
