package mc.Mitchellbrine.swing;

import java.io.File;

/**
 * Created by Mitchellbrine on 2015.
 */
public class IOUtils {

	private static String OS_NAME = System.getProperty("os.name").toLowerCase();
	public final static String USER_HOME = System.getProperty("user.home");


	public static File getDirectory() {
		if(OS_NAME.contains("win"))
			return new File(new File(System.getenv("APPDATA")), ".minecraft" + File.separator + "achgetOutput");
		else if(OS_NAME.contains("mac"))
			return new File(USER_HOME, "Library/Application Support/minecraft" + File.separator + "achgetOutput");
		else
			return new File(USER_HOME, ".minecraft" + File.separator + "achgetOutput");
	}
}
