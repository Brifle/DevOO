package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;


@NoArgsConstructor
public abstract class FileBagage {
    
	@Getter
	@Setter
	private ConnexionCircuit connexionCircuit;
	
    protected LinkedList<Bagage> listBagages = new LinkedList<Bagage>();
    
    public void addBagage(Bagage b){
    	listBagages.add(b);
    }
    
    public Bagage popBagage() {
    	return listBagages.pop();
    }
    
    public boolean removeBagage(Bagage b){
    	return listBagages.remove(b);
    }
    
    public Bagage removeBagage(int indice){
    	return listBagages.remove(indice);
    }
    
    public abstract boolean update();
  
}