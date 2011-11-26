package aeroport.sgbag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;



/**
 * Hello world!
 * 
 */
@ToString
@AllArgsConstructor
public  class App {
	
	@Getter
	private String exemple;

	public static void main(String[] args) {
		App app = new App("Hello World! This is a test");
		
		System.out.println(app);

		app.getExemple();
	}
}
