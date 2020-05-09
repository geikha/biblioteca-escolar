package control;

public class StringFormat {

	public static String stringForSQL(String s) {
		s = control.StringFormat.superTrim(s);
		return s.replaceAll("'", "''");
	}
	
	public static int DNI(String s) {
		if(s.contains("-")) s = s.replaceAll(".", "");
		return Integer.parseInt(s);
	}
	
	public static String telefono(String s) {
		char[] ch = s.toCharArray();
		String r = "";
		
		switch(ch.length) {
		case 8: //(ej: 45891256)
			for(int i=0; i<=3; i++) r+=ch[i];
			r+='-';
			for(int i=4; i<=7; i++) r+=ch[i];
			break;
		case 10: //(ej: 1545891256)
			for(int i=0; i<=1; i++) r+=ch[i];
			r+=' ';
			for(int i=2; i<=5; i++) r+=ch[i];
			r+='-';
			for(int i=6; i<=9; i++) r+=ch[i];
			break;
		case 11: //(ej: 15 12561256)
			for(int i=0; i<=2; i++) r+=ch[i];
			for(int i=3; i<=6; i++) r+=ch[i];
			r+='-';
			for(int i=7; i<=10; i++) r+=ch[i];
			break;
		default:
			r=s;
			break;
		}
		
		return r;
	}
	
	public static String superTrim(String s) {
		return s.trim().replaceAll(" +", " ");
	}
	
}
