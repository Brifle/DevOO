package aeroport.sgbag.kernel;

import java.util.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Hall {

	@Getter
	@Setter
	private Circuit circuit;

	@Getter
	@Setter
	private ArrayList<Bagage>  bagagesList;
	
	@Getter
	private ArrayList<FileBagage> fileBagageList;
	
	public boolean update() {
		boolean allUpdated = true;
		for (int i = 0; i < fileBagageList.size(); i++) {
			if(!fileBagageList.get(i).update()) {
				allUpdated = false;
			}
		}
		if(!circuit.update()) {
			allUpdated = false;
		}
		return allUpdated;
	}
	
	public void addFileBagage(FileBagage file) {
		fileBagageList.add(file);
	}
	
	public void removeFileBagage(FileBagage file) {
		fileBagageList.remove(file);
	}

}