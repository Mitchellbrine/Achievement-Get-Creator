package mc.Mitchellbrine.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mitchellbrine on 2015.
 */
public class Main implements Runnable{

	private static final ArrayList<JSONAchievement> achievements = new ArrayList<JSONAchievement>();

	public static void main(String[] args) {
		Main main = new Main();
		SwingUtilities.invokeLater(main);
	}

	public void run() {
		openScreen();
	}

	public void openScreen() {
		final JFrame frame = new JFrame("Achievement Get Creator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(350,600);
		frame.setResizable(false);

		frame.getContentPane().setBackground(new Color(0,100,0));

		frame.setLayout(new GroupLayout(frame.getContentPane()));

		insertComponents(frame);

		frame.setVisible(true);

	}

	public static JSONAchievement getAchievement(String name) {
		JSONAchievement ach = null;
		for (JSONAchievement achievement : achievements) {
			if (name.equalsIgnoreCase(achievement.id)) ach = achievement;
		}
		return ach;
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	public void insertComponents(final JFrame frame) {
		String labelString = "Welcome to the Achievement Get achievement creator";
		JLabel label = new JLabel(labelString);
		label.setBounds(5, 0, 350, 20);
		frame.add(label);

		String[] array = getAchNames();

		final JComboBox achBox = new JComboBox(array);
		achBox.setBounds(0,21,350,30);

		frame.add(achBox);

		int yLevel = 52;

		final String idString = "Id:";
		final JTextField id = new JTextField(idString);
		id.setToolTipText("This value has no default value and must be changed");

		id.setBounds(0,50,350,30);

		yLevel += 30;

		final String nameString = "Name:";
		final JTextField name = new JTextField(nameString);
		name.setToolTipText("This value has no default value and must be changed");

		name.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final String descString = "Description:";
		final JTextArea desc = new JTextArea(descString);
		desc.setToolTipText("This value has no default value and must be changed");

		desc.setBounds(0,yLevel,350,60);

		yLevel += 62;

		final String parentString = "Parent:";
		final JTextField parent = new JTextField(parentString);
		parent.setToolTipText("This value has no default value. (CAN BE NULL)");

		parent.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final String statString = "Stat Name:";
		final JTextField stat = new JTextField(statString);
		stat.setToolTipText("This value has no default value and must be changed");

		stat.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final SpinnerModel countModel = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);

		final JSpinner count = new JSpinner(countModel);
		count.setToolTipText("This value has no default value and must be changed (MUST BE INTEGER)");

		count.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final SpinnerModel xPosModel = new SpinnerNumberModel(0,-20,20,1);
		final SpinnerModel yPosModel = new SpinnerNumberModel(0,-20,20,1);

		final JSpinner xPos = new JSpinner(xPosModel);
		xPos.setToolTipText("This value has no default value and must be changed (MUST BE INTEGER)");

		xPos.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final JSpinner yPos = new JSpinner(yPosModel);
		yPos.setToolTipText("This value has no default value and must be changed (MUST BE INTEGER)");

		yPos.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final JComboBox isSpecial = new JComboBox<String>(new String[]{"false","true"});
		isSpecial.setToolTipText("This value has no default value and must be changed (MUST BE BOOLEAN)");

		isSpecial.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final JTextField item = new JTextField("minecraft:apple");

		item.setBounds(0,yLevel,350,30);

		yLevel += 32;

		final JTextField color = new JTextField("gray");

		color.setBounds(0,yLevel,350,30);

		yLevel += 40;

		frame.add(id);
		frame.add(name);
		frame.add(desc);
		frame.add(parent);
		frame.add(stat);
		frame.add(count);
		frame.add(xPos);
		frame.add(yPos);
		frame.add(isSpecial);
		frame.add(item);
		frame.add(color);

		final JButton button = new JButton("Add");
		button.setBounds(10,yLevel,330,40);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id.getText().equalsIgnoreCase(idString) || name.getText().equalsIgnoreCase(nameString) || desc.getText().equalsIgnoreCase(descString) || parent.getText().equalsIgnoreCase(parentString) || stat.getText().equalsIgnoreCase(statString))
					return;
				if (getAchievement(id.getText()) == null) {
					achievements.add(new JSONAchievement(id.getText(), name.getText(), desc.getText(), parent.getText(), stat.getText(), item.getText(), color.getText(), Boolean.parseBoolean((String) isSpecial.getItemAt(isSpecial.getSelectedIndex())), (Integer) count.getModel().getValue(), (Integer) xPos.getModel().getValue(), (Integer) yPos.getModel().getValue()));
				} else {
					JSONAchievement ach = getAchievement(id.getText());
					achievements.remove(ach);
					achievements.add(new JSONAchievement(id.getText(), name.getText(), desc.getText(), parent.getText(), stat.getText(), item.getText(), color.getText(), Boolean.parseBoolean((String) isSpecial.getItemAt(isSpecial.getSelectedIndex())), (Integer) count.getModel().getValue(), (Integer) xPos.getModel().getValue(), (Integer) yPos.getModel().getValue()));
				}
				achBox.setModel(new DefaultComboBoxModel<String>(getAchNames()));
			}
		});

		yLevel += 42;

		final JButton finalize = new JButton("Generate JSON");

		finalize.setBounds(10,yLevel,330,40);

		finalize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<JSONAchievement> achievements1 = new ArrayList<JSONAchievement>();
				for (JSONAchievement ach : achievements) {
					if (ach.parent != null && getAchievement(ach.parent) != null && achievements.indexOf(getAchievement(ach.parent)) > (achievements1.size() - 1)) {
						achievements1.add(getAchievement(ach.parent));
					}
					if (getAchievement(achievements1,ach.id) == null)
						achievements1.add(ach);
				}
				StringBuilder builder = new StringBuilder();
				builder.append("[\n");
				for (JSONAchievement achievement : achievements1) {
					builder.append("\t{\n");
					builder.append("\t\t\"id\": \"" + achievement.id + "\",\n");
					builder.append("\t\t\"name\": \"" + achievement.name + "\",\n");
					builder.append("\t\t\"desc\": \"" + achievement.desc + "\",\n");
					String parentString = !achievement.parent.equalsIgnoreCase("null") ? "\"" + achievement.parent + "\"" : "null";
					builder.append("\t\t\"parent\": " + parentString + ",\n");
					builder.append("\t\t\"stat\": \"" + achievement.stat + "\",\n");
					if (!achievement.item.equalsIgnoreCase("minecraft:apple")) {
						builder.append("\t\t\"item\": \"" + achievement.item + "\",\n");
					}
					if (!achievement.color.equalsIgnoreCase("gray")) {
						builder.append("\t\t\"color\": \"" + achievement.color + "\",\n");
					}
					builder.append("\t\t\"count\": " + achievement.count + ",\n");
					builder.append("\t\t\"xPos\": " + achievement.xPos + ",\n");
					builder.append("\t\t\"yPos\": " + achievement.yPos + ",\n");
					if (achievement.isSpecial) {
						builder.append("\t\t\"special\": true,\n");
					}
					builder.append("\t}");
					if (achievements1.indexOf(achievement) < (achievements1.size() - 1)) {
						builder.append(",");
					}
					builder.append("\n");
				}
				builder.append("]");

				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
				Date date = new Date();

				File output = new File(IOUtils.getDirectory(), dateFormat.format(date) + ".json");

				if (!output.getParentFile().exists()) {
					output.getParentFile().mkdirs();
				}

				try {
					output.createNewFile();
					PrintWriter writer = new PrintWriter(output);
					writer.println(builder.toString());
					writer.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		frame.add(button);
		frame.add(finalize);

		achBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getAchievement(((String)achBox.getItemAt(achBox.getSelectedIndex()))) != null) {
					JSONAchievement ach = (JSONAchievement)getAchievement((String)achBox.getItemAt(achBox.getSelectedIndex()));
					id.setText(ach.id);
					name.setText(ach.name);
					desc.setText(ach.desc);
					parent.setText(ach.parent);
					stat.setText(ach.stat);
					color.setText(ach.color);
					item.setText(ach.item);
					count.getModel().setValue(ach.count);
					xPos.getModel().setValue(ach.xPos);
					yPos.getModel().setValue(ach.yPos);
					isSpecial.getModel().setSelectedItem(ach.isSpecial);
				}
			}
		});

	}

	public static String[] getAchNames() {
		String[] array = new String[achievements.size()];
		for (JSONAchievement ach : achievements) {
			array[achievements.indexOf(ach)] = ach.id;
		}
		return array;
	}

	public static JSONAchievement getAchievement(java.util.List<JSONAchievement> arrays,String name) {
		for (JSONAchievement achieve : arrays) {
			if (achieve.id.equalsIgnoreCase(name))
				return achieve;
		}
		return null;
	}

}
