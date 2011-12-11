package aeroport.sgbag.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.views.VueElem;
import aeroport.sgbag.views.VueHall;

import com.thoughtworks.xstream.XStream;

@Log4j
@NoArgsConstructor
public class SgbagBuilder {

	private XStream xStream;

	private String path;

	public SgbagBuilder(String path) throws IOException {
		super();
		xStream = new XStream();
		this.path = path;
	}

	public VueHallDataBinder deserialize() throws IOException {
		log.debug("Désérialisation");
		FileReader freader = new FileReader(path);
		BufferedReader in = new BufferedReader(freader);
		String tmp = "";
		String str = "";
		
		while((tmp = in.readLine()) != null) {
			log.debug("->" + tmp);
			str += (tmp + '\n');
		}
		
		log.debug(str);
		
		if (str.length() == 0) {
			return null;
		}
		
		return (VueHallDataBinder) xStream.fromXML(str);
	}

	public String serialize(VueHall vh) throws IOException {
		log.debug("Sérialisation");
		FileWriter fstream = new FileWriter(path);
		BufferedWriter out = new BufferedWriter(fstream);
		
		VueHallDataBinder v = new VueHallDataBinder(vh.getHall(),
				vh.getCalques());
		String str = xStream.toXML(v);

		log.debug("xml : \n" + str);
		out.write(str);
		out.flush();

		return str;
	}
}
