package aeroport.sgbag.kernel;

import java.util.ArrayList;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public abstract class FileBagage {
    
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