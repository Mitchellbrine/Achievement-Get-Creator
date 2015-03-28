package mc.Mitchellbrine.swing;

/**
 * Created by Mitchellbrine on 2015.
 */
public class JSONAchievement {

	public String id, name, desc, parent, stat, item, color;

	public boolean isSpecial;

	public int count, xPos, yPos;

	public JSONAchievement(String id, String name, String desc, String parent, String stat, String item, String color, boolean isSpecial, int count, int xPos, int yPos) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.parent = parent;
		this.stat = stat;
		this.item = item;
		this.color = color;
		this.isSpecial = isSpecial;
		this.count = count;
		this.xPos = xPos;
		this.yPos = yPos;
	}

}
