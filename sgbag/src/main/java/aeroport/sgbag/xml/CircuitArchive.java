package aeroport.sgbag.xml;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Point;

import aeroport.sgbag.kernel.ElementCircuit;
import aeroport.sgbag.kernel.Noeud;
import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.utils.CircuitGenerator;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.views.VueRail;
import aeroport.sgbag.views.VueTapisRoulant;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("circuit")
public class CircuitArchive {
	
	// Description de la structure de notre fichier XML 
	@Data
	@EqualsAndHashCode
	@XStreamAlias("elementCircuit")
	public static class ElementCircuitSaved {
		@XStreamOmitField
		protected ElementCircuit unpackedObject = null;
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	@AllArgsConstructor
	@XStreamAlias("rail")
	public static class RailSaved extends ElementCircuitSaved {
		@XStreamAsAttribute
		private NoeudSaved from;
		@XStreamAsAttribute
		private NoeudSaved to;
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	@AllArgsConstructor
	@NoArgsConstructor
	@XStreamAlias("noeud")
	public static class NoeudSaved extends ElementCircuitSaved {
		@XStreamAsAttribute
		protected int x;
		@XStreamAsAttribute
		protected int y;
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	@AllArgsConstructor
	@XStreamAlias("toboggan")
	public static class TobogganSaved extends NoeudSaved {
		@XStreamAsAttribute
		private int angle;
		
		public TobogganSaved(int x, int y, int angle) {
			this.x = x;
			this.y = y;
			this.angle = angle;
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	@AllArgsConstructor
	@XStreamAlias("tapis")
	public static class TapisRoulantSaved extends NoeudSaved {
		@XStreamAsAttribute
		private int length;
		
		@XStreamAsAttribute
		private int vitesse;
		
		@XStreamAsAttribute
		private int distanceEntreBagage;
		
		@XStreamAsAttribute
		private int angle;
		
		@XStreamAsAttribute
		private Boolean autoGeneration = false;
		
		public TapisRoulantSaved(int x, int y, int length, int vitesse,
				int distanceEntreBagage, int angle, Boolean autoGeneration) {
			super(x, y);
			this.length = length;
			this.vitesse = vitesse;
			this.distanceEntreBagage = distanceEntreBagage;
			this.angle = angle;
			this.autoGeneration = autoGeneration;
		}
	}
	
	@Data
	@XStreamAlias("chariot")
	public static class ChariotSaved {
		final public static int DEFAULT_WIDTH = 20;
		final public static int DEFAULT_SPEED = 20;
		
		@XStreamAsAttribute
		private ElementCircuitSaved on;
		
		@XStreamAsAttribute
		private int position = 0;
		
		@XStreamAsAttribute
		private int maxMoveDistance;
		
		@XStreamAsAttribute
		private Noeud to;
		
		@XStreamAsAttribute
		private int length = DEFAULT_WIDTH;
		
		@XStreamAsAttribute
		private int maxSpeed = DEFAULT_SPEED;
	}
	
	@Getter
	@Setter
	@XStreamImplicit
	private LinkedList<TobogganSaved> toboggans = new LinkedList<CircuitArchive.TobogganSaved>();
	
	@Getter
	@Setter
	@XStreamImplicit
	private LinkedList<TapisRoulantSaved> tapisRoulants = new LinkedList<CircuitArchive.TapisRoulantSaved>();
	
	@Getter
	@Setter
	@XStreamImplicit
	private LinkedList<NoeudSaved> noeuds = new LinkedList<CircuitArchive.NoeudSaved>();
	
	@Getter
	@Setter
	@XStreamImplicit
	private LinkedList<RailSaved> rails = new LinkedList<CircuitArchive.RailSaved>();
	
	@Getter
	@Setter
	@XStreamImplicit
	private LinkedList<ChariotSaved> chariots = new LinkedList<CircuitArchive.ChariotSaved>();
	
	/**
	 * Extrait le contenu de la sauvegarde du circuit dans un nouveau
	 * Hall.
	 * @param vueHall
	 */
	public void unpackTo(VueHall vueHall) {
		CircuitGenerator cg = new CircuitGenerator(vueHall);
		
		for(RailSaved railp: rails){
			VueRail vr = cg.createSegment(new Point(railp.from.x, railp.from.y), 
						                  new Point(railp.to.x, railp.to.y));
			
			railp.getFrom().setUnpackedObject(vr.getRail().getNoeudPrecedent());
			railp.getTo().setUnpackedObject(vr.getRail().getNoeudSuivant());
		}
		
		for(TapisRoulantSaved tapisRoulant: tapisRoulants){
			VueTapisRoulant vtr = cg.createEntry(new Point(tapisRoulant.x, tapisRoulant.y),
												 tapisRoulant.length, tapisRoulant.vitesse,
												 tapisRoulant.distanceEntreBagage, 
												 tapisRoulant.autoGeneration);
			
			
		}
		
		for(TobogganSaved tobogganp: toboggans){
			cg.createExit(new Point(tobogganp.x, tobogganp.y));
		}
		
		for(ChariotSaved chariotp: chariots){
			if(chariotp.on instanceof RailSaved){
				cg.addChariot((Rail) chariotp.on.unpackedObject, chariotp.maxSpeed,
								chariotp.length, chariotp.position, chariotp.to, null, null);  
			}else if(chariotp.on instanceof NoeudSaved){
				cg.addChariot((Noeud) chariotp.on.unpackedObject, chariotp.maxSpeed,
						chariotp.length, chariotp.to, null, null);
			}
		}
	}
}
