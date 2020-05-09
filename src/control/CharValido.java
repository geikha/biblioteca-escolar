package control;

public class CharValido {

	/**
	 * @apiNote Un caractér de username SOLO puede ser letras, digitos, puntos y guion bajos
	 */
	public static boolean username(char ch) {
		return (Character.isLetter(ch) || Character.isDigit(ch)|| ch == '_' || ch == '.');
	}
	
	/**
	 * @apiNote Un caractér de password SOLO puede ser letras o digitos (simple)
	 */
	public static boolean password(char ch) {
		return (Character.isLetter(ch) || Character.isDigit(ch));
	}
	
	/**
	 * @apiNote Un caractér de DNI SOLO puede ser digitos o '.'
	 */
	public static boolean DNI(char ch) {
		return Character.isDigit(ch) || ch == '.';
	}
	
	/**
	 * @apiNote Un caractér de ISBN SOLO puede ser digitos o guiones medios
	 */
	public static boolean ISBN(char ch) {
		return Character.isDigit(ch) || ch == '-';
	}
	
	public static boolean nombreDePersona(char ch) {
		return Character.isLetter(ch) || ch == '\'';
	}
	
	public static boolean telefono(char ch) {
		return Character.isDigit(ch) || ch == '-' || ch == ' ';
	}
}
