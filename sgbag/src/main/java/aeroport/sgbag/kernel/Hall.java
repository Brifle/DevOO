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

}