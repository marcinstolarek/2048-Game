package gra2048;

import gra2048.Okno;

public class Gra2048 {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
	
			@Override
			public void run() {
				new Okno();	
			}
		});
	}
}
