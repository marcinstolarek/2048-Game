package gra2048;

public class Pole{
	private int wartosc;
	private static int zajete = 2; // zawsze dwa pola zajete na starcie
	
	public Pole() {
		wartosc = 0;
	}
	
	public Pole(int wartosc) {
		this.wartosc = wartosc;
	}
	
	public int wezWartosc() {
		return wartosc;
	}
	
	public void podwojWartosc() {
		wartosc *= 2;
	}
	
	public void wyzerujWartosc() {
		wartosc = 0;
	}
	
	public void zapiszWartosc(int wartosc) {
		this.wartosc = wartosc;
	}
	
	public boolean niepuste() {
		if (wartosc > 0)
			return true;
		else
			return false;
	}
	
	public boolean puste() {
		if (wartosc == 0)
			return true;
		else
			return false;
	}
	
	public int wezIloscZajetych() {
		return zajete;
	}
	
	public void zwiekszZajete() {
		++zajete;
	}
	
	public void zmniejszZajete() {
		--zajete;
	}

	public void ustawZajete(int zajete) {
		this.zajete = zajete;
	}
}
