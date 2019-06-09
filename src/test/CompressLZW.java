package test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class CompressLZW {
	public void compress(String path, String destpath) throws IOException {
		// 创建文件输入流
		int bianma = 256;// 编码
		String perfix = "";// 前缀
		String suffix = "";// 后缀
		String zhongjian = "";// 中间变量
		HashMap<String, Integer> hm = new HashMap<String, Integer>();// 编码表
		int fileLength = 0 ;
				InputStream is = new FileInputStream(path);
				byte[] buffer = new byte[is.available()];// 创建缓存区域
				is.read(buffer);// 读入所有的文件字节
				String str = new String(buffer);// 对字节进行处理
				is.close(); // 关闭流
				// 创建文件输出流
				OutputStream os = new FileOutputStream(destpath);
				DataOutputStream dos = new DataOutputStream(os);
//				System.out.println(str);
				// 把最基本的256个Ascll码放编码表中
				for (int i = 0; i < 256; i++) {
					char ch = (char) i;
					String st = ch + "";
					hm.put(st, i);
				}

				for (int i = 0; i < str.length(); i++) {
					if(bianma==65535){
						System.out.println("重置");
						dos.writeChar(65535);//写出一个-1作为重置的表示与码表的打印
						
						hm.clear();//清空Hashmap
						for (int j = 0; j < 256; j++) {//重新将基本256个编码写入
							char ch = (char) j;
							String st = ch + "";
							hm.put(st, j);
						}
						perfix="";
						bianma=0;
					}
					char ch = str.charAt(i);
					String s = ch + "";
					suffix = s;
					zhongjian = perfix + suffix;
					if (hm.get(zhongjian) == null) {// 如果码表中没有 前缀加后缀的码表
//						System.out.print(zhongjian);
//						System.out.println("  对应的编码为  " + bianma);
						hm.put(zhongjian, bianma);// 向码表添加 前缀加后缀 和 对应的编码
//						System.out.println("  " + perfix);
//						System.out.println("写入的编码 "+hm.get(perfix));
						
						dos.writeChar(hm.get(perfix)); // 把前缀写入压缩文件
						bianma++;
						perfix = suffix;
					} else {// 如果有下一个前缀保存 上一个前缀加后缀
						perfix = zhongjian;
					}
					if (i == str.length() - 1) {// 把最后一个写进去
//						System.out.print("写入最后一个"+perfix);
						dos.writeChar(hm.get(perfix));
//						System.out.println("     "+hm.get(perfix));
					}
					
				}
				
				os.close();// 关闭流
//				System.out.println(hm.toString());// 输出码表

	}
	
	public int getFileLength(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		byte[] buffer = new byte[is.available()];// 创建缓存区域
		is.read(buffer);// 读入所有的文件字节
		String str = new String(buffer);// 对字节进行处理
		is.close(); // 关闭流
		
		return str.length() ;
	}
	
	public float getCompressRate(String path) throws IOException {
		float a = (float)getOutputLength(path) ;
		float b = (float)getFileLength(path) ;
		
//		System.out.println(a + " " + b);
		
		return a/b ;
		
	}
	
	public int getOutputLength(String path) throws IOException {
		int outputLength = 0 ;
		// 创建文件输入流
		int bianma = 256;// 编码
		String perfix = "";// 前缀
		String suffix = "";// 后缀
		String zhongjian = "";// 中间变量
		HashMap<String, Integer> hm = new HashMap<String, Integer>();// 编码表
		int fileLength = 0 ;
				InputStream is = new FileInputStream(path);
				byte[] buffer = new byte[is.available()];// 创建缓存区域
				is.read(buffer);// 读入所有的文件字节
				String str = new String(buffer);// 对字节进行处理
				is.close(); // 关闭流
				// 创建文件输出流
//				System.out.println(str);
				// 把最基本的256个Ascll码放编码表中
				for (int i = 0; i < 256; i++) {
					char ch = (char) i;
					String st = ch + "";
					hm.put(st, i);
				}

				for (int i = 0; i < str.length(); i++) {
					if(bianma==65535){
						System.out.println("重置");
						outputLength++ ;
						
						hm.clear();//清空Hashmap
						for (int j = 0; j < 256; j++) {//重新将基本256个编码写入
							char ch = (char) j;
							String st = ch + "";
							hm.put(st, j);
						}
						perfix="";
						bianma=0;
					}
					char ch = str.charAt(i);
					String s = ch + "";
					suffix = s;
					zhongjian = perfix + suffix;
					if (hm.get(zhongjian) == null) {// 如果码表中没有 前缀加后缀的码表
//						System.out.print(zhongjian);
//						System.out.println("  对应的编码为  " + bianma);
						hm.put(zhongjian, bianma);// 向码表添加 前缀加后缀 和 对应的编码
//						System.out.println("  " + perfix);
//						System.out.println("写入的编码 "+hm.get(perfix));
						
						outputLength++ ;
						bianma++;
						perfix = suffix;
					} else {// 如果有下一个前缀保存 上一个前缀加后缀
						perfix = zhongjian;
					}
					if (i == str.length() - 1) {// 把最后一个写进去
//						System.out.print("写入最后一个"+perfix);
						outputLength++ ;
//						System.out.println("     "+hm.get(perfix));
					}
					
				}
		//				System.out.println(hm.toString());// 输出码表
		
		return outputLength;
	}
}
