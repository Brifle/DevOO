package aeroport.sgbag.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.utils.CircuitGenerator;

import com.thoughtworks.xstream.XStream;

@Log4j
@NoArgsConstructor
public class SgbagBuilder {

	private XStream xStream;
	private String path;

	public SgbagBuilder(String path) throws IOException {
		super();
		xStream = new XStream();

		xStream.omitField(CircuitGenerator.class, "vueHall");		
		xStream.omitField(CircuitGenerator.class, "hall");
		xStream.omitField(CircuitGenerator.class, "listePointsNoeuds");
		xStream.omitField(CircuitGenerator.class, "listOfRail");
		xStream.omitField(CircuitGenerator.class, "circuit");
		xStream.omitField(CircuitGenerator.class, "mapOfElems");
		xStream.omitField(Chariot.class, "parent");

		xStream.setMode(XStream.ID_REFERENCES);

		this.path = path;
	}

	public CircuitGenerator deserialize() throws IOException {
		log.debug("Désérialisation");
		FileReader freader = new FileReader(path);
		BufferedReader in = new BufferedReader(freader);
		String tmp = "";
		String str = "";

		while ((tmp = in.readLine()) != null) {
			str += (tmp + '\n');
		}

		log.debug(str);

		if (str.length() == 0) {
			return null;
		}

		return (CircuitGenerator) xStream.fromXML(str);
	}

	public String serialize(CircuitGenerator cg) throws IOException {
		log.debug("Sérialisation");
		FileWriter fstream = new FileWriter(path);
		BufferedWriter out = new BufferedWriter(fstream);

		String str = xStream.toXML(cg);
		out.write(str);
		out.flush();

		return str;
	}
}
