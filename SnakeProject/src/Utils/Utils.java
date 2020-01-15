package Utils;

import java.util.Objects;

public class Utils {

public static int counter=3;
public  String dataPath;

public Utils() {
	super();
	String protocol = this.getClass().getResource("").getProtocol();
	if(Objects.equals(protocol, "jar")){
		this.dataPath = "Questions.json";

	} else if(Objects.equals(protocol, "file")) {
		this.dataPath = "SnakeProject/src/Data/Questions.json";
	}
}

public String getDataPath() {
	return dataPath;
}

public void setDataPath(String dataPath) {
	this.dataPath = dataPath;
}



}
