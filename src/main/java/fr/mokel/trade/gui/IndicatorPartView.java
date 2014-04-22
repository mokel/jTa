package fr.mokel.trade.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class IndicatorPartView extends JPanel {
	
	private String title;
	private String content;
	
	private JLabel jContent;
	
	public IndicatorPartView(String aTitle) {
		title = aTitle;
		setLayout(new MigLayout());
		setDimensions();
		JLabel jTitle = new JLabel(title);
		Map<TextAttribute, Object> map = new HashMap<TextAttribute, Object>();
		map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font font = getFont().deriveFont(map).deriveFont(Font.ITALIC);
		jTitle.setFont(font);
		add(jTitle, "north");
		
		jContent = new JLabel();
		jContent.setPreferredSize(new Dimension(300, 70));
		jContent.setMinimumSize(new Dimension(300, 70));
		jContent.setMaximumSize(new Dimension(300, 70));
		add(jContent);
	}

	public void setModel(String string) {
		content = string;
		jContent.setText(content);
		repaint();
	}
	
	private void setDimensions() {
		setPreferredSize(new Dimension(300, 100));
		setMinimumSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(300, 100));
	}

}
