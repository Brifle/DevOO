package aeroport.sgbag.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Pair<X,Y> {
	@Getter
	@Setter
	private X x;
	
	@Getter
	@Setter
	private Y y;

}
