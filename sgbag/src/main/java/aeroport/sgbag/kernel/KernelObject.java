package aeroport.sgbag.kernel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public abstract class KernelObject {
	private static int counter = 0;

	protected static String kTypeName = "Objet";
	protected static int kTypeCounter = 1; 

	@Getter
	final private int id;
	
	@Setter
	protected String name = null;
	
	public String getName(){
		if(name == null){
			name = kTypeName + " n°" + kTypeCounter;
			kTypeCounter++;
			return name;
		}else{
			return name;
		}
	}
	
	public KernelObject() {
		id = counter++;
	}
}
