package fr.mokel.trade.gui2.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Reflection-based Swing Utilities. These utility methods are used to prompt
 * for objects and edit objects, based on class names.
 *
 * {@link https://www.informit.com/guides/content.aspx?g=java&seqNum=367}
 * 
 * @author Steven Haines
 */
public class FormPanel extends JPanel {
	/**
	 * This map creates a mapping between property name and the Swing components
	 * that implement them
	 */
	private Map propertyControls;

	private Class<?> clazz;

	public FormPanel() {
		// TODO Auto-generated constructor stub
	}
	public FormPanel(Class<?> clazz) {
		setLayout(new GridBagLayout());
		this.clazz = clazz;
		buildPanel(clazz);
	}

	/**
	 * Builds a JPanel containing the fields to query for the specified class
	 *
	 * @param c
	 *            The class for which to build the panel
	 * @return
	 */
	protected void buildPanel(Class c) {
		// Setup our gridbag layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridy = 0;
		constraints.ipadx = 2;
		constraints.ipady = 1;
		int height = 0;

		// Load the properties that we’re going to need to build the panel from
		Map props = ReflectionUtils.getWriteableProperties(c);
		this.propertyControls = new TreeMap();

		// Process the properties
		for (Iterator i = props.keySet().iterator(); i.hasNext();) {
			String propertyName = (String) i.next();
			String className = (String) props.get(propertyName);

			if (ReflectionUtils.isPrimitiveType(className)) {
				// Add Label - build it as the property name with the first
				// character capitalized
				// and a space inserted before each new word (upper case letter)
				StringBuffer sb = new StringBuffer();
				sb.append(Character.toUpperCase(propertyName.charAt(0)));
				boolean lastCharUpperCase = false;
				for (int j = 1; j < propertyName.length(); j++) {
					char ch = propertyName.charAt(j);
					if (Character.isUpperCase(ch)) {
						if (!lastCharUpperCase) {
							// Add a space since this is a new word
							sb.append(" ");
						}
						lastCharUpperCase = true;
					} else {
						lastCharUpperCase = false;
					}
					// Append the character itself
					sb.append(ch);
				}
				sb.append(":");
				// Build the JLabel and add it to the panel
				JLabel label = new JLabel(sb.toString());
				constraints.gridx = 0;
				constraints.anchor = GridBagConstraints.NORTHEAST;
				add(label, constraints);

				// Handle the Swing component
				constraints.gridx = 1;
				constraints.anchor = GridBagConstraints.NORTHWEST;
				if (className.equals("boolean")
						|| className.equals("java.lang.Boolean")) {
					// Booleans are treated as checkboxes
					JCheckBox checkbox = new JCheckBox();
					propertyControls.put(propertyName, checkbox);
					add(checkbox, constraints);
				} else {
					// All of the rest of the primitive types are handled as
					// String fields
					JTextField textField = new JTextField();
					propertyControls.put(propertyName, textField);
					add(textField, constraints);
				}

				// Increment the height of the panel so that we can display the
				// corresponding
				// dialog appropriately
				height += 30;
			}

			// Increment the gridy
			constraints.gridy++;
		}

		// Return the panel that we created
		setSize(400, height);
	}

	/**
	 * Builds a dialog box and presents it to the user with fields that all them
	 * to build an instance of the specified class. Once the user has built that
	 * object then this method constructs an instance of the object and sets its
	 * properties.
	 *
	 * @param className
	 * @return
	 */
	public Object getObject() {
		// Show the dialog
		// Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Dimension panelSize = getSize();
		// int width = panelSize.width;
		// int height = panelSize.height + 30;
		// dlg.setSize(width, height);
		// dlg.setLocation(screen.width / 2 - width / 2, screen.height / 2
		// - height / 2);
		// dlg.setVisible(true);

		// Build a property map
		Map propertyMap = new TreeMap();
		for (Iterator i = this.propertyControls.keySet().iterator(); i
				.hasNext();) {
			String propertyName = (String) i.next();
			String propertyValue = null;
			JComponent component = (JComponent) this.propertyControls
					.get(propertyName);
			if (component instanceof JTextField) {
				propertyValue = ((JTextField) component).getText();
			} else if (component instanceof JCheckBox) {
				propertyValue = Boolean.toString(((JCheckBox) component)
						.isSelected());
			}
			propertyMap.put(propertyName, propertyValue);
		}

		// Build and return the resultant object
		return ReflectionUtils.buildObject(clazz, propertyMap);
	}
}