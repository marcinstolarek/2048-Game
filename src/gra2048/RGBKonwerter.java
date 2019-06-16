package gra2048;

public class RGBKonwerter {
	private int red;
	private int green;
	private int blue;
	private int rgb;
	
	public RGBKonwerter() {
		red = 0;
		green = 0;
		blue = 0;
		rgb = 0;
	}
	
	public RGBKonwerter(int r, int g, int b) {
		rgb(r, g, b);
		red = r;
		green = g;
		blue = b;
	}
	
	public int rgb() {
		return rgb;
	}
	
	public int rgb(int r, int g, int b) {
		rgb = r;
		rgb *= 65536; // przesuniecie bitow na pozycje 16-23
		rgb += g * 256; // bity na pozycjach 8-15
		rgb += b; // bity na pozycjach 0-7
		
		return rgb;
	}
}
