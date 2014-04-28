package fr.mokel.trade.gui2.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * {@link http://code.google.com/p/tda366-towerdefence/source/browse/trunk/TDA366-towerdefence/src/util/GridBagConstraintsBuilder.java?spec=svn16&r=16}
 * 
 * This class is used to supply the builder pattern when creating objects of
 * type GridBagConstraints. It may or may not be a good alternative to the Swing
 * default way, since it basically just wraps the manual property setting into
 * builder commands.
 *
 * @author Pelle & Group15 (tda366-towerdefence)
 *
 */
public class ConstraintsBuilder {

        private GridBagConstraints c = new GridBagConstraints();

        /**
         * This builder must be supplied values for gridx and gridy when it is
         * instantiated.
         *
         * @param gridx
         * @param gridy
         */
        public ConstraintsBuilder(int gridx, int gridy) {
                c.gridx = gridx;
                c.gridy = gridy;
        }

        /**
         * 
         */
        public ConstraintsBuilder() { }

		/**
         * Sets the gridwidth property of the GridBagConstraints object being built.
         *
         * @param gridwidth
         *            the value that the gridwidth property of the
         *            GridBagConstraints being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder gridwidth(int gridwidth) {
                c.gridwidth = gridwidth;
                return this;
        }

        /**
         * Sets the gridheight property of the GridBagConstraints object being
         * built.
         *
         * @param gridheight
         *            the value that the gridheight property of the
         *            GridBagConstraints being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder gridheight(int gridheight) {
                c.gridheight = gridheight;
                return this;
        }

        /**
         * Sets the fill property of the GridBagConstraints object being built.
         *
         * @param fill
         *            the value that the fill property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder fill(int fill) {
                c.fill = fill;
                return this;
        }
     
        public ConstraintsBuilder fillBoth() {
            c.fill = GridBagConstraints.BOTH;
            return this;
    }

        /**
         * Sets the ipadx property of the GridBagConstraints object being built.
         *
         * @param ipadx
         *            the value that the ipadx property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder ipadx(int ipadx) {
                c.ipadx = ipadx;
                return this;
        }

        /**
         * Sets the ipady property of the GridBagConstraints object being built.
         *
         * @param ipady
         *            the value that the ipady property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder ipady(int ipady) {
                c.ipady = ipady;
                return this;
        }

        /**
         * Sets the insets property of the GridBagConstraints object being built.
         *
         * @param insets
         *            the value that the insets property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder insets(Insets insets) {
                c.insets = insets;
                return this;
        }

        /**
         * Sets the anchor property of the GridBagConstraints object being built.
         *
         * @param anchor
         *            the value that the anchor property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder anchor(int anchor) {
                c.anchor = anchor;
                return this;
        }

        /**
         * Sets the weightx property of the GridBagConstraints object being built.
         *
         * @param weightx
         *            the value that the weightx property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder weightx(float weightx) {
                c.weightx = weightx;
                return this;
        }

        /**
         * Sets the weighty property of the GridBagConstraints object being built.
         *
         * @param weighty
         *            the value that the weighty property of the GridBagConstraints
         *            being built shall be set to
         * @return a reference to itself to allow further building within the same
         *         statement.
         */
        public ConstraintsBuilder weighty(float weighty) {
                c.weighty = weighty;
                return this;
        }

        /**
         * Finishes the building by returning a reference to the GridBagConstraints
         * object being built.
         *
         * @return a reference to the fully built GridBagConstraints object.
         */
        public GridBagConstraints build() {
                return c;
        }
}
