package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
	
	private static FontLoader fontLoader = new FontLoader();
	
	private FontLoader() {
		
	}
	
	public static FontLoader getInstance() {
		return fontLoader;
	}
	
	public Font loadMarioFont() {
		Font font = null;
		int fontSize = 20;
		
		try {
            InputStream in = getClass().getResourceAsStream("/font/Super-Mario-World.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
        	font = new Font("Serif", Font.BOLD, fontSize);
            e.printStackTrace();
        }
		return font;
	}
}
