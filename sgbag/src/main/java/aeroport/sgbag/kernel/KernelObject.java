package aeroport.sgbag.kernel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class KernelObject {
	private static int counter = 0;

	@Getter
	final private int id;
	
	@Setter
	@Getter
	private String name;
	
	public KernelObject() {
		id = counter++;
	}
}
