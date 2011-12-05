package aeroport.sgbag.kernel;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public abstract class FileBagage {
    
	@Getter
	@Setter
	private ConnexionCircuit connexionCircuit;
	
    protected ArrayList<Bagage> listBagages = new ArrayList<Bagage>();
    
    public void addBagage(Bagage b){
    	this.listBagages.add(b);
    }
    
    public boolean removeBagage(Bagage b){
    	return this.listBagages.remove(b);
    }
    
    public Bagage removeBagage(int indice){
    	return this.listBagages.remove(indice);
    }
  
}