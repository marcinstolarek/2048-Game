package gra2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Okno extends JFrame implements ActionListener, MouseListener, KeyListener{
	private Plansza plansza = new Plansza();
	private Plansza plansza_poprzednia = new Plansza();
	private boolean gra_w_toku = true;
	private JLabel wynik = new JLabel("0");
	private JLabel[] element = new JLabel[16];
	private JLabel wynik_opis = new JLabel("Wynik:");
	private JLabel koniec_gry = new JLabel("");
	private JButton przycisk_cofnij = new JButton("Cofnij");
	
	public Okno() {
		super("2048");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(408,510);
		setLocation(400,400);
		setForeground(Color.GREEN);
		GridLayout layout = new GridLayout(5,4);
		layout.setHgap(2);
		layout.setVgap(2);
		setLayout(layout);
		
		setVisible(true);
		
		addKeyListener(this);
		przycisk_cofnij.addActionListener(this);
		
		wynik_opis.setHorizontalAlignment(SwingConstants.RIGHT);
		wynik_opis.setVerticalAlignment(SwingConstants.CENTER);
		wynik_opis.setOpaque(true);
		
		wynik.setHorizontalAlignment(SwingConstants.LEFT);
		wynik.setVerticalAlignment(SwingConstants.CENTER);
		wynik.setOpaque(true);
		
		koniec_gry.setHorizontalAlignment(SwingConstants.CENTER);
		koniec_gry.setVerticalAlignment(SwingConstants.CENTER);
		koniec_gry.setOpaque(true);
		
		przycisk_cofnij.setFocusable(false);
		
		add(wynik_opis, 0);
		add(wynik, 1);
		add(koniec_gry , 2);
		add(przycisk_cofnij , 3);
		
		for (int i = 0; i < 16; i++) { // inicjowanie pol na pusto
			element[i] = new JLabel("");
			element[i].setHorizontalAlignment(SwingConstants.CENTER);
			element[i].setVerticalAlignment(SwingConstants.CENTER);
			element[i].setOpaque(true);
			element[i].setBackground(Color.GRAY);
			Font czcionka = element[i].getFont();
			element[i].setFont(new Font(czcionka.getFontName(), czcionka.getStyle(), 32));
			add(element[i], i + 4);
		}
		
		zapiszPlansze();
		OdswiezOkno();
	}
	
	public void OdswiezOkno() {
		Color kolor = new Color(255 * 255 * 255);
		RGBKonwerter rgb = new RGBKonwerter();
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				String tekst = new String("");
				int wartoscPola = plansza.wezWartoscPola(x, y);
				if (wartoscPola > 0)
					tekst = "" +wartoscPola;
				element[(3 - y) * 4  + x].setText(tekst);
				
				if (wartoscPola == 0) {
					element[(3 - y) * 4  + x].setBackground(Color.WHITE);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 2) {
					element[(3 - y) * 4  + x].setBackground(Color.GRAY);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 4) {
					element[(3 - y) * 4  + x].setBackground(Color.YELLOW);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 8) {
					element[(3 - y) * 4  + x].setBackground(Color.ORANGE);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 16) {
					kolor = new Color(rgb.rgb(253,104,104));
					element[(3 - y) * 4  + x].setBackground(kolor);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 32) {
					element[(3 - y) * 4  + x].setBackground(Color.RED);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola == 64) {
					kolor = new Color(rgb.rgb(218,151,252));
					element[(3 - y) * 4  + x].setBackground(kolor);
					element[(3 - y) * 4  + x].setForeground(Color.BLACK);
				}
				else if (wartoscPola > 64) {
					element[(3 - y) * 4  + x].setBackground(Color.BLUE);
					element[(3 - y) * 4  + x].setForeground(Color.WHITE);
				}
			}
		}
		
		wynik.setText("" +plansza.wezWynik());
	}
	
	public void zapiszPlansze() {
		plansza_poprzednia.zapiszZajete(plansza.wezZajete());
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++)
				plansza_poprzednia.zapiszWartosci(x, y, plansza.wezWartosci(x, y));
		}
	}
	
	public void przywrocPlansze() {
		plansza.zapiszZajete(plansza_poprzednia.wezZajete());
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++)
				plansza.zapiszWartosci(x, y, plansza_poprzednia.wezWartosci(x, y));
		}
	}
	
	// mysz
	@Override
	public void actionPerformed(ActionEvent e) { // wcisniety przycisk
		Object zrodlo = e.getSource();
		
		if (zrodlo == przycisk_cofnij) {
			przywrocPlansze();
			OdswiezOkno();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) { // kliknieto i zwolniono mysz w tym samym miejscu
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { // wjechano w obszar nasluchiwania
		// e.getX();
		// e.getY();
	}
	
	@Override
	public void mouseExited(MouseEvent e) { // zjechano kursorem z obszaru nasluchiwania
		// e.getX();
		// e.getY();
	}
	
	@Override
	public void mousePressed(MouseEvent e) { // nacisnieto przycisk myszy
		// e.getX();
		// e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { // zwolniono przycisk myszy
		// e.getX();
		// e.getY();
	}
	
	// klawiatura
	@Override
    public void keyPressed(KeyEvent e) {
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        int znak = e.getKeyCode();
        switch (znak) {
		    case KeyEvent.VK_UP: //strza³ka w górê
		    	if (gra_w_toku && plansza.czyMoznaWGore()) {
		    		zapiszPlansze();
		    		plansza.przesunWGore();
		    		plansza.wylosujNowe();
		    		if (plansza.czyKoniec()) {
		    			gra_w_toku = false;
		    			koniec_gry.setText("Koniec gry!");
		    		}
		    		OdswiezOkno();
		    	}
		        break;
		    case KeyEvent.VK_DOWN: //strza³ka w dó³
		    	if (gra_w_toku && plansza.czyMoznaWDol()) {
		    		zapiszPlansze();
		    		plansza.przesunWDol();
		    		plansza.wylosujNowe();
		    		if (plansza.czyKoniec()) {
		    			gra_w_toku = false;
		    			koniec_gry.setText("Koniec gry!");
		    		}
		    		OdswiezOkno();
		    	}
		        break;
		    case KeyEvent.VK_LEFT: //strza³ka w lewo
		    	if (gra_w_toku && plansza.czyMoznaWLewo()) {
		    		zapiszPlansze();
		    		plansza.przesunWLewo();
		    		plansza.wylosujNowe();
		    		if (plansza.czyKoniec()) {
		    			gra_w_toku = false;
		    			koniec_gry.setText("Koniec gry!");
		    		}
		    		OdswiezOkno();
		    	}
		        break;
		    case KeyEvent.VK_RIGHT: //strza³ka w prawo
		    	
		    	if (gra_w_toku && plansza.czyMoznaWPrawo()) {
		    		zapiszPlansze();
		    		plansza.przesunWPrawo();
		    		plansza.wylosujNowe();
		    		if (plansza.czyKoniec()) {
		    			gra_w_toku = false;
		    			koniec_gry.setText("Koniec gry!");
		    		}
		    		OdswiezOkno();
		    	}
		        break;
		    default:break;
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
}
