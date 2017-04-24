package zxl.bean;

import java.io.File;

public class Binutils {

	public static void remove_things_from_disk(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
	
}
