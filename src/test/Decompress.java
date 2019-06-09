package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decompress {
	public int [] codelengths = new int[256] ;
	public String [] codeMap = new String[256] ;
	
	public void decompress(String srcpath, String destpath) throws IOException {
		FileInputStream fis = new FileInputStream(srcpath);
		FileOutputStream fos = new FileOutputStream(destpath) ;
		
		int value;
		int codeLength = 0 ;
		String code = "" ;
		
		for(int i = 0; i < codelengths.length; i++) {
			value = fis.read() ;
			codelengths[i] = value ;
//			System.out.println(times[i]);
			codeLength += codelengths[i] ;
		}
		
		int len = codeLength/8 ;
		if((codeLength % 8) != 0) {
			len++ ;
		}
		
		for(int i = 0; i < len; i++) {
			code += changeIntToString(fis.read()) ;
			System.out.println(code);
		}
		for(int i = 0; i < codeMap.length; i++) {
			if(codelengths[i] != 0) {
					System.out.println(codelengths.length);
					String ss = code.substring(0, codelengths[i]) ;
					codeMap[i] = ss ;
					code = code.substring(codelengths[i]) ;
			}
			else {
				codeMap[i] = "" ;
			}
		}
		
		String codeContent = "" ;
		while(fis.available()>1) {
			codeContent += changeIntToString(fis.read()) ;
		}
		
		value = fis.read() ;
		
		codeContent = codeContent.substring(0, codeContent.length()-value) ;
		
		for(int i = 0; i < codeContent.length(); i++) {
			String codecontent = codeContent.substring(0, i+1) ;
			
			for(int j = 0; j < codeMap.length; j++) {
				if(codeMap[j].equals(codecontent)) {
					fos.write(j);
					fos.flush();
					codeContent=codeContent.substring(i+1) ;
					i = -1 ;
					break ;
				}
			}
		}
		fos.close() ;
		fis.close();
	}
	
	public String changeIntToString(int value) {
		String s = "" ;
		for(int i = 0; i < 8; i++) {
			s = value %2 + s ;
			value = value / 2 ;
		}
		return s ;
	}
}
