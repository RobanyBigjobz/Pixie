/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pixie.ico.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import pixie.ui.HexDumpPanel;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//pixie.ico.ui//Ico//EN",
autostore = false)
@TopComponent.Description(preferredID = "IcoTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "pixie.ico.ui.IcoTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_IcoAction",
preferredID = "IcoTopComponent")
@Messages({
	"CTL_IcoAction=Ico",
	"CTL_IcoTopComponent=Ico Window",
	"HINT_IcoTopComponent=This is a Ico window"
})
public final class IcoTopComponent extends TopComponent
{
	public static final String PROP_PATH = "path";
	private static final Logger logger =
		Logger.getLogger(IcoTopComponent.class.getName());
	private final InstanceContent ic = new InstanceContent();
	private byte[] bytes;
	private File file;
	private HexDumpPanel hexPanel;

	public IcoTopComponent()
	{
		initComponents();
		setName(Bundle.CTL_IcoTopComponent());
		setToolTipText(Bundle.HINT_IcoTopComponent());

		associateLookup(new AbstractLookup(ic));

		hexPanel = new HexDumpPanel();
		add(hexPanel, BorderLayout.CENTER);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	@Override
	public void componentOpened()
	{
		// TODO add custom code on component opening
	}

	@Override
	public void componentClosed()
	{
		if (file != null)
		{
			ic.remove(file);
		}
	}

	void writeProperties(java.util.Properties p)
	{
		// better to version settings since initial version as advocated at
		// http://wiki.apidesign.org/wiki/PropertyFiles
		p.setProperty("version", "1.0");
		if (file != null)
		{
			p.setProperty(PROP_PATH, file.getAbsolutePath());
		}
	}

	void readProperties(java.util.Properties p)
	{
		String version = p.getProperty("version");
		File testFile = new File(p.getProperty(PROP_PATH));
		setFile(testFile);
	}

	public static IcoTopComponent findOpen(File file)
	{
		Registry registry = WindowManager.getDefault().getRegistry();
		Iterator<TopComponent> iter = registry.getOpened().iterator();
		while (iter.hasNext())
		{
			TopComponent tc = iter.next();
			if (!(tc instanceof IcoTopComponent))
			{
				continue;
			}
			File tcFile = tc.getLookup().lookup(File.class);
			if (tcFile.getAbsolutePath().equals(file.getAbsolutePath()))
			{
				return (IcoTopComponent) tc;
			}
		}
		return null;
	}

	public File getFile()
	{
		return file;
	}

	private void readFile(File file)
	{
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(file);
			setDisplayName(file.getName());
			setToolTipText(file.getPath());
			int nBytes = in.available();
			bytes = new byte[nBytes];
			in.mark(nBytes);
			in.read(bytes);
			this.file = file;
		}
		catch (IOException exIO)
		{
			bytes = null;
			this.file = null;
			logger.log(Level.WARNING, "Cannot load file: "+file.getPath(), exIO);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException junk)
				{}
			}
		}		
	}

	public void setFile(File file)
	{
		if (this.file != null)
		{
			ic.remove(this.file);
		}
		readFile(file);
		if (file == null)
		{
			close();
			return;
		}
		ic.add(file);
		hexPanel.showBytes(bytes);
	}

}