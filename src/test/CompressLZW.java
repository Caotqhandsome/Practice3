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
		// �����ļ�������
		int bianma = 256;// ����
		String perfix = "";// ǰ׺
		String suffix = "";// ��׺
		String zhongjian = "";// �м����
		HashMap<String, Integer> hm = new HashMap<String, Integer>();// �����
		int fileLength = 0 ;
				InputStream is = new FileInputStream(path);
				byte[] buffer = new byte[is.available()];// ������������
				is.read(buffer);// �������е��ļ��ֽ�
				String str = new String(buffer);// ���ֽڽ��д���
				is.close(); // �ر���
				// �����ļ������
				OutputStream os = new FileOutputStream(destpath);
				DataOutputStream dos = new DataOutputStream(os);
//				System.out.println(str);
				// ���������256��Ascll��ű������
				for (int i = 0; i < 256; i++) {
					char ch = (char) i;
					String st = ch + "";
					hm.put(st, i);
				}

				for (int i = 0; i < str.length(); i++) {
					if(bianma==65535){
						System.out.println("����");
						dos.writeChar(65535);//д��һ��-1��Ϊ���õı�ʾ�����Ĵ�ӡ
						
						hm.clear();//���Hashmap
						for (int j = 0; j < 256; j++) {//���½�����256������д��
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
					if (hm.get(zhongjian) == null) {// ��������û�� ǰ׺�Ӻ�׺�����
//						System.out.print(zhongjian);
//						System.out.println("  ��Ӧ�ı���Ϊ  " + bianma);
						hm.put(zhongjian, bianma);// �������� ǰ׺�Ӻ�׺ �� ��Ӧ�ı���
//						System.out.println("  " + perfix);
//						System.out.println("д��ı��� "+hm.get(perfix));
						
						dos.writeChar(hm.get(perfix)); // ��ǰ׺д��ѹ���ļ�
						bianma++;
						perfix = suffix;
					} else {// �������һ��ǰ׺���� ��һ��ǰ׺�Ӻ�׺
						perfix = zhongjian;
					}
					if (i == str.length() - 1) {// �����һ��д��ȥ
//						System.out.print("д�����һ��"+perfix);
						dos.writeChar(hm.get(perfix));
//						System.out.println("     "+hm.get(perfix));
					}
					
				}
				
				os.close();// �ر���
//				System.out.println(hm.toString());// ������

	}
	
	public int getFileLength(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		byte[] buffer = new byte[is.available()];// ������������
		is.read(buffer);// �������е��ļ��ֽ�
		String str = new String(buffer);// ���ֽڽ��д���
		is.close(); // �ر���
		
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
		// �����ļ�������
		int bianma = 256;// ����
		String perfix = "";// ǰ׺
		String suffix = "";// ��׺
		String zhongjian = "";// �м����
		HashMap<String, Integer> hm = new HashMap<String, Integer>();// �����
		int fileLength = 0 ;
				InputStream is = new FileInputStream(path);
				byte[] buffer = new byte[is.available()];// ������������
				is.read(buffer);// �������е��ļ��ֽ�
				String str = new String(buffer);// ���ֽڽ��д���
				is.close(); // �ر���
				// �����ļ������
//				System.out.println(str);
				// ���������256��Ascll��ű������
				for (int i = 0; i < 256; i++) {
					char ch = (char) i;
					String st = ch + "";
					hm.put(st, i);
				}

				for (int i = 0; i < str.length(); i++) {
					if(bianma==65535){
						System.out.println("����");
						outputLength++ ;
						
						hm.clear();//���Hashmap
						for (int j = 0; j < 256; j++) {//���½�����256������д��
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
					if (hm.get(zhongjian) == null) {// ��������û�� ǰ׺�Ӻ�׺�����
//						System.out.print(zhongjian);
//						System.out.println("  ��Ӧ�ı���Ϊ  " + bianma);
						hm.put(zhongjian, bianma);// �������� ǰ׺�Ӻ�׺ �� ��Ӧ�ı���
//						System.out.println("  " + perfix);
//						System.out.println("д��ı��� "+hm.get(perfix));
						
						outputLength++ ;
						bianma++;
						perfix = suffix;
					} else {// �������һ��ǰ׺���� ��һ��ǰ׺�Ӻ�׺
						perfix = zhongjian;
					}
					if (i == str.length() - 1) {// �����һ��д��ȥ
//						System.out.print("д�����һ��"+perfix);
						outputLength++ ;
//						System.out.println("     "+hm.get(perfix));
					}
					
				}
		//				System.out.println(hm.toString());// ������
		
		return outputLength;
	}
}
