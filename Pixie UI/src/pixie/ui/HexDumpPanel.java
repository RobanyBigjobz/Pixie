/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pixie.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Formatter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * $Id$
 *
 * @author adminjamesd
 */
public class HexDumpPanel extends JPanel
{
	private JTextArea hexText;
	private JScrollPane scrollPane;

	public HexDumpPanel()
	{
		super();
		initComponents();
		hexText.setFont(new Font("Monospaced", Font.PLAIN, 12));
	}

	private void initComponents()
	{
		scrollPane = new JScrollPane();
		hexText = new JTextArea();

		setLayout(new BorderLayout());

		hexText.setColumns(20);
		hexText.setRows(5);
		scrollPane.setViewportView(hexText);

		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	public void showBytes(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		int nLines = bytes.length/16;
		int offset = 0;
		for (int i=0; i<nLines; i++)
		{
			formatLine(formatter, bytes, offset, 16);
			offset += 16;
		}
		int overspill = bytes.length-nLines*16;
		if (overspill > 0)
		{
			formatLine(formatter, bytes, offset, overspill);
		}
		hexText.setText(sb.toString());
		hexText.setCaretPosition(0);
	}

	private void formatLine(Formatter formatter, byte[] bytes, int offset,
		int len)
	{
		formatter.format("0x%08x  ", offset);
		for (int i=0; i<len; i++)
		{
			formatter.format(" %02x", bytes[offset+i]);
			if ((i > 0) && ((i % 8) == 7))
			{
				formatter.format("  ");
			}
		}
		formatter.format("     ");
		for (int i=0; i<len; i++)
		{
			int cp = bytes[offset+i];
			if (Character.isValidCodePoint(cp) && !Character.isISOControl(cp))
			{
				formatter.format("%c", cp);
			}
			else
			{
				formatter.format(".");
			}
		}
		formatter.format("\n");
	}

}
