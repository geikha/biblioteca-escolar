package control;

import javax.swing.*;

import vista.AppMain;

public class StringValido {
	
	static JFrame marco = AppMain.marco;

	/**
	 * @apiNote un username NO ES valido si: largo > 32 chars, ó tiene dos '.' seguidos
	 */
	public static boolean username(String s) { //
		if(s.length() > 32) 
			return false;
		char[] ch = s.toCharArray();
		for(int i = 1; i<ch.length; i++)
			if(ch[i] == '.' & ch[i-1] == '.')
				return false;
		return true;
	}
	
	/**
	 * @apiNote una password NO ES valida si: largo > 64 chars
	 */
	public static boolean password(String s) {
		if(s.length() > 64 || s.length() < 6) {
			return false;
		}
		return true;
	}
	
	public static boolean nombreApellido(String s) {
		s = control.StringFormat.superTrim(s);
		char[] ch = s.toCharArray();
		for(int i = 1; i<ch.length; i++)
			if(ch[i] == '\'' & ch[i-1] == '\'')
				return false;
		return s.length()<=100;
	}
	
	public static boolean telefono(String s) {
		s = control.StringFormat.superTrim(s);
		char[] ch = s.toCharArray();
		switch(ch.length) {
		case 8: //(ej: 45891256)
		case 10: //(ej: 1545891256)
			for(char c : ch) if(!Character.isDigit(c)) return false; 
			break;
		case 9: //(ej: 4589-1256)
			for(int i=0; i<=3; i++) if(!Character.isDigit(ch[i])) return false; 
			if(ch[4]!='-') return false;
			for(int i=5; i<=8; i++) if(!Character.isDigit(ch[i])) return false; 
			break;
		case 11: //(ej: 15 12561256)
			for(int i=0; i<=1; i++) if(!Character.isDigit(ch[i])) return false; 
			if(ch[2]!=' ') return false;
			for(int i=3; i<=10; i++) if(!Character.isDigit(ch[i])) return false;
			break;
		case 12://(ej: 15 1235-5697)
			for(int i=0; i<=1; i++) if(!Character.isDigit(ch[i])) return false; 
			if(ch[2]!=' ') return false;
			for(int i=3; i<=6; i++) if(!Character.isDigit(ch[i])) return false;
			if(ch[7]!='-') return false;
			for(int i=8; i<=11; i++) if(!Character.isDigit(ch[i])) return false;
			break;
		default:
			return false;
		}
		return true;
	}
	
	public static boolean DNI(String s) {
		s = control.StringFormat.superTrim(s);
		char[] ch = s.toCharArray();
		switch(ch.length) {
		case 7:
		case 8: //43915808
			for(char c : ch) if(!Character.isDigit(c)) return false; 
			break;
		case 9: //3.915.808
			if(!Character.isDigit(ch[0])) return false; 
			if(ch[1]!='.') return false;
			for(int i=2; i<=4; i++) if(!Character.isDigit(ch[i])) return false;
			if(ch[5]!='.') return false;
			for(int i=6; i<=8; i++) if(!Character.isDigit(ch[i])) return false;
			break;
		case 10: //43.915.808
			for(int i=0; i<=1; i++) if(!Character.isDigit(ch[i])) return false; 
			if(ch[2]!='.') return false;
			for(int i=3; i<=5; i++) if(!Character.isDigit(ch[i])) return false;
			if(ch[6]!='.') return false;
			for(int i=7; i<=9; i++) if(!Character.isDigit(ch[i])) return false;
			break;
		default:
			return false;
		}
		return true;
	}
	
	public static boolean curso(String año, String division) {
		switch(Integer.parseInt(año)) {
		case 1:
			return true;
		case 2:
			if(Integer.parseInt(division) > 4)
				return false;
			break;
		default:
			if(Integer.parseInt(division) > 3)
				return false;
			break;
		}
		return true;
	}
}
