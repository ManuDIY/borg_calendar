/*
 * This file is part of BORG.
 *
 * BORG is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * BORG is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * BORG; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 *
 * Copyright 2003 by Mike Berger
 */

package net.sf.borg.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import net.sf.borg.common.util.Resource;
import net.sf.borg.model.ReminderTimes;


public class PopupOptionsView extends JDialog {


	private javax.swing.JLabel jAlarmLabel;
	private javax.swing.JCheckBox[] alarmBoxes;
	private char[] remtimes_;
	private AppointmentPanel appPanel_;
	private JPanel jPanel = null;
	private JPanel checkpanel = null;
	private JPanel buttonPanel = null;
	private JButton saveButton = null;
	private JButton dismissButton = null;
	private JButton clearButton = null;
	private JButton allButton = null;

	public PopupOptionsView(char[] remtimes, AppointmentPanel appPanel) {
		super();

		initialize();

		ResourceHelper.setTitle(this, "Popup_Times");
		appPanel_ = appPanel;
		remtimes_ = remtimes;

		jAlarmLabel = new JLabel();
		ResourceHelper.setText(jAlarmLabel, "custom_times_header");
		jAlarmLabel.setText(jAlarmLabel.getText() + " '" + appPanel_.getText() + "'");
		alarmBoxes = new JCheckBox[ReminderTimes.getNum()];
		for (int i = 0; i < ReminderTimes.getNum(); ++i) {
			alarmBoxes[i] = new JCheckBox(minutes_string(i));
		}

		checkpanel = getCheckpanel();
		checkpanel.add(jAlarmLabel, new GridBagConstraints(0, 0, 2, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0));

		int colht = alarmBoxes.length / 2;
		for (int i = 0; i < colht; ++i) {
			checkpanel.add(alarmBoxes[i], new GridBagConstraints(0, i + 1, 1,
					1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

			if (remtimes_[i] == 'Y') {
				alarmBoxes[i].setSelected(true);
			} else {
				alarmBoxes[i].setSelected(false);
			}
		}

		for (int i = colht; i < alarmBoxes.length; ++i) {
			checkpanel.add(alarmBoxes[i], new GridBagConstraints(1, i - colht
					+ 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
			if (remtimes_[i] == 'Y') {
				alarmBoxes[i].setSelected(true);
			} else {
				alarmBoxes[i].setSelected(false);
			}
		}

		pack();


		this.setModal(true);
		//manageMySize(PrefName.POPVIEWSIZE);

	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJPanel());
	}
	public void destroy() {
		this.dispose();
	}

	public void refresh() {
	}

	private void saveButtonClicked(java.awt.event.ActionEvent evt) {
		for (int i = 0; i < ReminderTimes.getNum(); ++i) {
			if (alarmBoxes[i].isSelected()) {
				remtimes_[i] = 'Y';
			} else {
				remtimes_[i] = 'N';
			}

		}

		appPanel_.setPopupTimesString();
		this.dispose();
	}

	private void cancelButtonClicked(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	private String minutes_string(int i) {
		int j = ReminderTimes.getTimes(i);
		int jj = (j >= 0 ? j : -j);
		int k = jj / 60;
		int l = jj % 60;
		String minStr;
		String hrStr;
		String minute = Resource.getResourceString("Minute");
		String minutes = Resource.getResourceString("Minutes");
		String hour = Resource.getResourceString("Hour");
		String hours = Resource.getResourceString("Hours");
		String before = Resource.getResourceString("Before");
		String after = Resource.getResourceString("After");

		if (k > 1) {
			hrStr = k + " " + hours;
		} else if (k > 0) {
			hrStr = k + " " + hour;
		} else {
			hrStr = "";
		}

		if (l > 1) {
			minStr = l + " " + minutes;
		} else if (l > 0) {
			minStr = l + " " + minute;
		} else if (k >= 1) {
			minStr = "";
		} else {
			minStr = l + " " + minutes;
		}

		if( !hrStr.equals("") && !minStr.equals(""))
			minStr = " " + minStr;


		String bef_aft;
		if (j > 0) {
			bef_aft = " " + before;
		} else if (j == 0) {
			bef_aft = "";
		} else {
			bef_aft = " " + after;
		}


		return hrStr + minStr + bef_aft;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.insets = new java.awt.Insets(4, 4, 4, 4);
			gridBagConstraints5.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.weighty = 1.0D;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weighty = 1.0D;
			gridBagConstraints6.insets = new java.awt.Insets(4, 4, 4, 4);
			gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
			jPanel.add(getCheckpanel(), gridBagConstraints5);
			jPanel.add(getButtonPanel(), gridBagConstraints6);

		}
		return jPanel;
	}

	/**
	 * This method initializes checkpanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getCheckpanel() {
		if (checkpanel == null) {
			checkpanel = new JPanel();
			checkpanel.setLayout(new GridBagLayout());
		}
		return checkpanel;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getSaveButton(), null);
			buttonPanel.add(getClearButton(), null);  // Generated
			buttonPanel.add(getAllButton(), null);  // Generated
			buttonPanel.add(getJButton1(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes saveButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			ResourceHelper.setText(saveButton, "Save");
			saveButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/Save16.gif")));
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					saveButtonClicked(evt);
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (dismissButton == null) {
			dismissButton = new JButton();
			ResourceHelper.setText(dismissButton, "Dismiss");
			dismissButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/Stop16.gif")));
			dismissButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					cancelButtonClicked(evt);
				}
			});
	    	getRootPane()
			.registerKeyboardAction
			(
				new ActionListener()
				{
					public final void actionPerformed(ActionEvent e) {
						cancelButtonClicked(e);
					}
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW
			);
		}
		return dismissButton;
	}

	/**
	 * This method initializes clearButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			ResourceHelper.setText(clearButton, "clear_all");
			clearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					for (int i = 0; i < ReminderTimes.getNum(); ++i) {
						alarmBoxes[i].setSelected(false);
					}
				}
			});
		}
		return clearButton;
	}

	/**
	 * This method initializes allButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAllButton() {
		if (allButton == null) {
			allButton = new JButton();
			ResourceHelper.setText(allButton, "select_all");
			allButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					for (int i = 0; i < ReminderTimes.getNum(); ++i) {
						alarmBoxes[i].setSelected(true);
					}
				}
			});
		}
		return allButton;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
