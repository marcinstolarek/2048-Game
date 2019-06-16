package gra2048;

import java.util.Random;

public class Plansza {
	private Pole[][] pole= new Pole[4][4];
	private long punkty;
	
	public Plansza() {
		for (int x = 0; x < 4; x++) { // opisanie pol
			for (int y = 0; y < 4; y++)
				pole[x][y] = new Pole(0);
		}
		wylosujStartowe();
		punkty = 0; // startujemy z zerowa liczba punktow
	}
	
	public void wylosujStartowe() { // losuje pola startowe - jedna pole zawsze z dwojka, drugie ma 10% szans na czworke
		int los1 = -1;
		int los2 = -2;
		Random losuj = new Random();
		
		los1 = losuj.nextInt(16);
		do {
			los2 = losuj.nextInt(16);
		} while (los1 == los2);
		
		if (losuj.nextInt(10) == 0) // 10% szans na rozpoczecie z czworka
			pole[los1 / 4][los1 % 4].zapiszWartosc(4);
		else
			pole[los1 / 4][los1 % 4].zapiszWartosc(2);
		pole[los2 / 4][los2 % 4].zapiszWartosc(2); // drugie pole zawsze jako dwojka
	}

	public void wylosujNowe() { // losuje nowe pole - 10% szans na czworke
		int los;
		int x = 0, y = 0;
		Random losuj = new Random();
		
		los = losuj.nextInt(16 - pole[0][0].wezIloscZajetych()) + 1;
		
		do {
			if (pole[x][y].puste())
				--los;
			if (los > 0) {
				++x;
				if (x == 4) {
					x = 0;
					++y;
				}
			}
		}while (los > 0);
		
		if (losuj.nextInt(10) == 0) // 10% szans na czworke
			pole[x][y].zapiszWartosc(4);
		else
			pole[x][y].zapiszWartosc(2);
		
		pole[0][0].zwiekszZajete();
	}
	
	public boolean czyKoniec() {
		if (pole[0][0].wezIloscZajetych() == 16) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					if (x < 3) { // sprawdzanie w prawo
						if (pole[x][y].wezWartosc() == pole[x + 1][y].wezWartosc())
							return false;
					}
					if (y < 3) { // sprawdzanie w gore
						if (pole[x][y].wezWartosc() == pole[x][y + 1].wezWartosc())
							return false;
					}
				}
			} // jezeli wyjdziemy z tej petli, to znaczy ze koniec gry
			return true;
		}
		else // sa jeszcze wolne pola
			return false;
	}

	public void zamienPola(int x_a, int y_a, int x_b, int y_b) {
		int temp = pole[x_a][y_a].wezWartosc();
		pole[x_a][y_a].zapiszWartosc(pole[x_b][y_b].wezWartosc());
		pole[x_b][y_b].zapiszWartosc(temp);
	}
	
	public boolean czyMoznaWGore() { // brak ruchu - false, poruszone pola - true
		boolean ruch = false;
		
		for (int x = 0; x < 4; x++) {
			for (int koniec = 0; koniec < 3 ; koniec++) { // likwidowanie dziur
				for (int i = 3; i > koniec; i--) {
					if (pole[x][i].puste() && pole[x][i - 1].niepuste())
						ruch = true;
				}
			}
			for (int y = 3; y > 0; y--) {				
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x][y - 1].wezWartosc()) // zlaczanie pol
					ruch = true;
			}
		}
		return ruch;
	}
	
	public boolean czyMoznaWDol() {
		boolean ruch = false;
		
		for (int x = 0; x < 4; x++) {
			for (int koniec = 3; koniec > 0 ; koniec--) { // likwidowanie dziur
				for (int i = 0; i < koniec; i++) {
					if (pole[x][i].puste() && pole[x][i + 1].niepuste())
						ruch = true;
				}
			}
			for (int y = 0; y < 3; y++) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x][y + 1].wezWartosc()) // zlaczanie pol
					ruch = true;
			}
		}
		return ruch;
	}

	public boolean czyMoznaWPrawo() { // brak ruchu - false, poruszone pola - true
		boolean ruch = false;
		
		for (int y = 0; y < 4; y++) {
			for (int koniec = 0; koniec < 3 ; koniec++) { // likwidowanie dziur
				for (int i = 3; i > koniec; i--) {
					if (pole[i][y].puste() && pole[i - 1][y].niepuste())
						ruch = true;
				}
			}
			for (int x = 3; x > 0; x--) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x - 1][y].wezWartosc()) // zlaczanie pol
					ruch = true;
			}
		}
		return ruch;
	}
	
	public boolean czyMoznaWLewo() { // brak ruchu - false, poruszone pola - true
		boolean ruch = false;
		
		for (int y = 0; y < 4; y++) {
			for (int koniec = 3; koniec > 0 ; koniec--) { // likwidowanie dziur
				for (int i = 0; i < koniec; i++) {
					if (pole[i][y].puste() && pole[i + 1][y].niepuste())
						ruch = true;
				}
			}
			for (int x = 0; x < 3; x++) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x + 1][y].wezWartosc()) // zlaczanie pol
					ruch = true;
			}
		}
		return ruch;
	}
	
	public void przesunWGore() { // brak ruchu - false, poruszone pola - true	
		for (int x = 0; x < 4; x++) {
			for (int koniec = 0; koniec < 3 ; koniec++) { // likwidowanie dziur
				for (int i = 3; i > koniec; i--) {
					if (pole[x][i].puste() && pole[x][i - 1].niepuste())
						zamienPola(x, i, x, i - 1);
				}
			}
			for (int y = 3; y > 0; y--) {				
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x][y - 1].wezWartosc()) { // zlaczanie pol
					pole[0][0].zmniejszZajete();
					pole[x][y].podwojWartosc();
					punkty += pole[x][y].wezWartosc(); // zwiekszenie punktow o sumaryczna wartosc zlaczenia
					for (int i = y - 1; i > 0; i--) {
						if (pole[x][i].niepuste())
							pole[x][i].zapiszWartosc(pole[x][i - 1].wezWartosc());
					}
					pole[x][0].wyzerujWartosc();
				}
			}
		}
	}
	
	public void przesunWDol() { // brak ruchu - false, poruszone pola - true	
		for (int x = 0; x < 4; x++) {
			for (int koniec = 3; koniec > 0 ; koniec--) { // likwidowanie dziur
				for (int i = 0; i < koniec; i++) {
					if (pole[x][i].puste() && pole[x][i + 1].niepuste())
						zamienPola(x, i, x, i + 1);
				}
			}
			for (int y = 0; y < 3; y++) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x][y + 1].wezWartosc()) { // zlaczanie pol
					pole[0][0].zmniejszZajete();
					pole[x][y].podwojWartosc();
					punkty += pole[x][y].wezWartosc(); // zwiekszenie punktow o sumaryczna wartosc zlaczenia
					for (int i = y + 1; i < 3; i++) {
						if (pole[x][i].niepuste())
							pole[x][i].zapiszWartosc(pole[x][i + 1].wezWartosc());
					}
					pole[x][3].wyzerujWartosc();
				}
			}
		}
	}
	
	public void przesunWPrawo() { // brak ruchu - false, poruszone pola - true
		for (int y = 0; y < 4; y++) {
			for (int koniec = 0; koniec < 3 ; koniec++) { // likwidowanie dziur
				for (int i = 3; i > koniec; i--) {
					if (pole[i][y].puste() && pole[i - 1][y].niepuste())
						zamienPola(i, y, i - 1, y);
				}
			}
			for (int x = 3; x > 0; x--) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x - 1][y].wezWartosc()) { // zlaczanie pol
					pole[0][0].zmniejszZajete();
					pole[x][y].podwojWartosc();
					punkty += pole[x][y].wezWartosc(); // zwiekszenie punktow o sumaryczna wartosc zlaczenia
					for (int i = x - 1; i > 0; i--) {
						if (pole[i][y].niepuste())
							pole[i][y].zapiszWartosc(pole[i - 1][y].wezWartosc());
					}
					pole[0][y].wyzerujWartosc();
				}
			}
		}
	}
	
	public void przesunWLewo() { // brak ruchu - false, poruszone pola - true
		for (int y = 0; y < 4; y++) {
			for (int koniec = 3; koniec > 0 ; koniec--) { // likwidowanie dziur
				for (int i = 0; i < koniec; i++) {
					if (pole[i][y].puste() && pole[i + 1][y].niepuste())
						zamienPola(i, y, i + 1, y);
				}
			}
			for (int x = 0; x < 3; x++) {
				if (pole[x][y].niepuste() && pole[x][y].wezWartosc() == pole[x + 1][y].wezWartosc()) { // zlaczanie pol
					pole[0][0].zmniejszZajete();
					pole[x][y].podwojWartosc();
					punkty += pole[x][y].wezWartosc(); // zwiekszenie punktow o sumaryczna wartosc zlaczenia
					for (int i = x + 1; i < 3; i++) {
						if (pole[i][y].niepuste())
							pole[i][y].zapiszWartosc(pole[i + 1][y].wezWartosc());
					}
					pole[3][y].wyzerujWartosc();
				}
			}
		}
	}

	public int wezWartoscPola(int x, int y) {
		return pole[x][y].wezWartosc();
	}
	
	public long wezWynik() {
		return punkty;
	}

	// do kopiowania obiektu
    public int wezWartosci(int x, int y) {
    	return pole[x][y].wezWartosc();
    }
    
    public int wezZajete() {
    	return pole[0][0].wezIloscZajetych();
    }
    
    public void zapiszZajete(int zajete) {
    	pole[0][0].ustawZajete(zajete);
    }
    
    public void zapiszWartosci(int x, int y, int wart) {
    	pole[x][y].zapiszWartosc(wart);
    }
	
}
