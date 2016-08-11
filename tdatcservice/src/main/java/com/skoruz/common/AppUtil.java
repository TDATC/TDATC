package com.skoruz.common;

public class AppUtil {
	

public static String imagePath(String name){
	StringBuilder uri=new StringBuilder();
	uri.append(ApplicationConstants.FILE_URL).append(ApplicationConstants.IMG_URL)
	          .append(name);
	return uri.toString();
}


public static String filePath(String name){
StringBuilder url=new StringBuilder();
url.append(ApplicationConstants.FILE_URL).append(ApplicationConstants.FILE)
          .append(name);
return url.toString();
}

}
