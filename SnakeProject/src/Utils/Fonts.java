package Utils;

import java.io.IOException;
import java.io.InputStream;


import javafx.scene.text.Font;

public class Fonts {

	public static Font minecraft50;
	public static Font minecraft30;
	public static Font minecraft15;
	
	public static void loadMinecraftFont()
	{
		String currentFontFile = "/View/Minecraft.ttf";
	      InputStream fontStream = Fonts.class.getResourceAsStream(currentFontFile);
	      Font bgFont = Font.loadFont(fontStream, 50);
	      minecraft50=bgFont;
	      InputStream fontStream2 = Fonts.class.getResourceAsStream(currentFontFile);
	      Font smFont = Font.loadFont(fontStream2, 30);
	      minecraft30=smFont;
	      InputStream fontStream3 = Fonts.class.getResourceAsStream(currentFontFile);
	      Font smsmFont = Font.loadFont(fontStream2, 15);
	      minecraft15=smsmFont;
	      try {
			fontStream.close();
			fontStream2.close();
			fontStream3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}
	
	
}
