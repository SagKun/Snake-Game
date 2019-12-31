package Utils;

import java.io.InputStream;


import javafx.scene.text.Font;

public class Fonts {

	public static Font minecraft;
	
	
	public static void loadMinecraftFont()
	{
		String currentFontFile = "/View/Minecraft.ttf";
	      InputStream fontStream = Fonts.class.getResourceAsStream(currentFontFile);
	      Font bgFont = Font.loadFont(fontStream, 50);
	      minecraft=bgFont;
	}
	
	
}
