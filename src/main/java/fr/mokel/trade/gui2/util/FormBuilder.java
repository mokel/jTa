package fr.mokel.trade.gui2.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Reflection-based Swing Utilities. These utility methods are used to prompt
 * for objects and edit objects, based on class names.
 */
public class FormBuilder implements java.awt.event.ActionListener {
	/**
	 * As we prompt for user input, this button will appear on the dialog box to
	 * signify an approval of input
	 */
	private JButton okButton = new JButton("OK");

	/**
	 * As we prompt for user input, this button will appear on the dialog box to
	 * signify a disapproval of input
	 *
	 */
	private JButton cancelButton = new JButton("Cancel");

	/**
	 * Dialog to present to the user
	 */
	private JDialog dlg;

	/**
	 * This boolean reports the result of button presses: isOK will be true if
	 * OK was pressed or false if Cancel was pressed
	 */
	private boolean isOK = false;

	/**
	 * This map creates a mapping between property name and the Swing components
	 * that implement them
	 */
	private Map propertyControls;

	/**
	 * Creates a new RSwing class instance
	 */
	public FormBuilder() {
		// Add the RSwing class as an action listener to the JButtons
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	/**
	 * Returns a JPanel containing the fields to query for the specified class
	 * name
	 *
	 * @param className
	 *            The name of the class for which to build the panel
	 * @return
	 */
	protected JPanel getPanel(String className) {
		try {
			Class c = Class.forName(className);
			return getPanel(c);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return null;
	}

	/**
	 * Builds a JPanel containing the fields to query for the specified class
	 *
	 * @param c
	 *            The class for which to build the panel
	 * @return
	 */
	protected JPanel getPanel(Class c) {
		// Setup our gridbag layout
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridy = 0;
		constraints.ipadx = 2;
		constraints.ipady = 1;
		int height = 0;

		// Create our panel
		JPanel panel = new JPanel(gridbag);

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
				gridbag.setConstraints(label, constraints);
				panel.add(label);

				// Handle the Swing component
				constraints.gridx = 1;
				constraints.anchor = GridBagConstraints.NORTHWEST;
				if (className.equals("boolean")
						|| className.equals("java.lang.Boolean")) {
					// Booleans are treated as checkboxes
					JCheckBox checkbox = new JCheckBox();
					propertyControls.put(propertyName, checkbox);
					gridbag.setConstraints(checkbox, constraints);
					panel.add(checkbox);
				} else {
					// All of the rest of the primitive types are handled as
					// String fields
					JTextField textField = new JTextField(20);
					propertyControls.put(propertyName, textField);
					gridbag.setConstraints(textField, constraints);
					panel.add(textField);
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
		panel.setSize(400, height);
		return panel;
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
	public Object getObject(JFrame frame, String name, String className) {
		this.dlg = new JDialog(frame, name, true);
		JPanel panel = getPanel(className);
		dlg.getContentPane().setLayout(new BorderLayout());
		dlg.getContentPane().add(panel, BorderLayout.CENTER);

		// Add the button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 4, 0));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonContainer.add(buttonPanel);
		dlg.getContentPane().add(buttonContainer, BorderLayout.SOUTH);

		// Show the dialog
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = panel.getSize();
		int width = panelSize.width;
		int height = panelSize.height + 30;
		dlg.setSize(width, height);
		dlg.setLocation(screen.width / 2 - width / 2, screen.height / 2
				- height / 2);
		dlg.setVisible(true);

		// If the user cancelled the dialog then don’t build the resultant
		// object
		if (!this.isOK) {
			return null;
		}

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
		return ReflectionUtils.buildObject(className, propertyMap);
	}

	/**
	 * Handle button presses
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		// See what button the user pressed
		if (e.getSource() == this.okButton) {
			System.out.println("OK pressed");
			this.isOK = true;
		} else {
			System.out.println("Cancel pressed");
			this.isOK = false;
		}

		// Hide the dialog
		dlg.setVisible(false);
	}

	public static void main(String[] args) {
		FormBuilder rs = new FormBuilder();
		PersonProfile pp = (PersonProfile) rs.getObject(null, "Simple Test",
				PersonProfile.class.getName());
		System.out.println("Person: " + pp.getFirstName() + " "
				+ pp.getLastName());
	}
}