package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;

/*
 */
public class ConnexionCircuit extends Noeud {

	@Getter
	@Setter
    private FileBagage fileBagage;
    
    public Boolean update(){
    	if( this.hasChariot() ){
    		if( this.getListeChariot().getFirst().getDestination() == this){
    			if(this.fileBagage instanceof TapisRoulant){
    				if(((TapisRoulant)fileBagage).hasReadyBagage()){
    					this.getListeChariot().getFirst().setBagage(((TapisRoulant)fileBagage).getBagageIfReady());
    					//TODO moveToNextRail();
    				}   				
    			}
    			else{//Type toboggan
    				this.getListeChariot().getFirst().moveBagageToFile(fileBagage);
    				//TODO moveToNextRail();
    			}
    		}else{
    			//TODO moveToNextRail();
    		}  			
    	}
    	return true;
    }

}