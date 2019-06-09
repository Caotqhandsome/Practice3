package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Compress {
	private int [] times = new int[256] ;
	private String [] HuffCodes = new String [256] ;
	private LinkedList<HuffNode> list = new LinkedList<HuffNode>() ;
	private int fileLength = 0 ;
	
	public Compress() {
		for(int i = 0; i < HuffCodes.length; i++) {
			HuffCodes[i] = "" ;
		}
	}
	
	
	public void countTimes(String path) throws Exception{
		FileInputStream fis = new FileInputStream(new File(path)) ;
		
		int value = fis.read() ;
		while(value != -1) {
			times[value]++ ;
			value = fis.read() ;
			fileLength++ ;
		}
		fis.close() ;
	}
	
	public HuffNode createTree() {
		for(int i = 0; i < times.length; i++) {
			if(times[i] != 0) {
				HuffNode node = new HuffNode(times[i], i) ;
				list.add(getIndex(node), node) ;
			}
		}
		
		while(list.size() > 1) {
			HuffNode firstNode = list.removeFirst() ;
			HuffNode secondNode = list.removeFirst() ;
			HuffNode fatherNode = new HuffNode(firstNode.getData() + secondNode.getData(), -1) ;
			fatherNode.setLeft(firstNode);
			fatherNode.setRight(secondNode) ;
			list.add(getIndex(fatherNode),fatherNode) ;
		}
		return list.getFirst();
	}
	
	public void getHuffCode(HuffNode root, String code) {
		if(root.getLeft() != null) {
			getHuffCode(root.getLeft(), code+"0") ;
		}
		if(root.getRight() != null) {
			getHuffCode(root.getRight(), code+"1") ;
		}
		
		if(root.getLeft() == null && root.getRight() == null) {
			HuffCodes[root.getIndex()] = code ;
		}
	}
	
	public void compress(String path, String destpath) throws Exception{
		FileOutputStream fos = new FileOutputStream(new File(destpath)) ;
		FileInputStream fis = new FileInputStream(new File(path)) ;
		
		String code ="";

		for (int i = 0; i < 256; i++) {
			fos.write(HuffCodes[i].length());
			code+=HuffCodes[i];
			fos.flush();
		}
		
		String str1 = "" ;
		while(code.length() >= 8) {
			str1 = code.substring(0, 8) ;
			int c = changeStringToInt(str1) ;
			fos.write(c);
			fos.flush();
			code = code.substring(8) ;
		}
		
		int last = 8 - code.length() ;
		for(int i = 0; i < last; i++) {
			code += "0" ;
		}
		str1 = code.substring(0, 8) ;
		int c = changeStringToInt(str1) ;
		fos.write(c);
		fos.flush() ;
		
		int value=fis.read();
		String str = "";
		while(value!=-1){
			str+=HuffCodes[value];
			value=fis.read();
		}
		fis.close();
		
		String s = "" ;
		while(str.length() >= 8) {
			s = str.substring(0, 8) ;
			int b = changeStringToInt(s);
			fos.write(b);
			fos.flush();
			str = str.substring(8) ;
		}
		
		int last1 = 8-str.length() ;
		for(int i = 0; i < last1; i++) {
			str += "0" ;
		}
		s = str.substring(0,8) ;
		int d = changeStringToInt(s) ;
		fos.write(d);
		
		fos.write(last1);
		fos.flush();
		
		fos.close();
		
	}
	
	private int getOutputLength(String path) throws IOException {
		FileInputStream fis = new FileInputStream(new File(path)) ;
		int outputLength = 0 ;
		
		String code ="";

		for (int i = 0; i < 256; i++) {
			code+=HuffCodes[i];
		}
		
		String str1 = "" ;
		while(code.length() >= 8) {
			str1 = code.substring(0, 8) ;
			int c = changeStringToInt(str1) ;
			outputLength ++ ;
			code = code.substring(8) ;
		}
		
		int last = 8 - code.length() ;
		for(int i = 0; i < last; i++) {
			code += "0" ;
		}
		str1 = code.substring(0, 8) ;
		int c = changeStringToInt(str1) ;
		outputLength++ ;
		
		int value=fis.read();
		String str = "";
		while(value!=-1){
			str+=HuffCodes[value];
			value=fis.read();
		}
		fis.close();
		
		String s = "" ;
		while(str.length() >= 8) {
			s = str.substring(0, 8) ;
			int b = changeStringToInt(s);
			outputLength++ ;
			str = str.substring(8) ;
		}
		
		int last1 = 8-str.length() ;
		for(int i = 0; i < last1; i++) {
			str += "0" ;
		}
		s = str.substring(0,8) ;
		int d = changeStringToInt(s) ;
		outputLength++ ;
		outputLength++ ;
		
		return outputLength ;
	}
	
	public float getCompressRate(String path) throws IOException {
		float a = (float)getOutputLength(path) ;
		float b = (float)fileLength ;
		
		return a/b ;
	}
	
	private int changeStringToInt(String s) {
		int v1 = (s.charAt(0) - 48) * 128 ;
		int v2 = (s.charAt(1) - 48) * 64 ;
		int v3 = (s.charAt(2) - 48) * 32 ;
		int v4 = (s.charAt(3) - 48) * 16 ;
		int v5 = (s.charAt(4) - 48) * 8 ;
		int v6 = (s.charAt(5) - 48) * 4 ;
		int v7 = (s.charAt(6) - 48) * 2 ;
		int v8 = (s.charAt(7) - 48) * 1 ;
		
		return v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8 ;
	}
	
	private int getIndex(HuffNode node) {
		for(int i = 0; i < list.size(); i++) {
			if(node.getData() <= list.get(i).getData()) {
				return i ;
			}
		}
		return list.size();
	}
}
