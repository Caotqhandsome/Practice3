package test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyJFrame extends JFrame {
	JList jl1 ;
	JScrollPane jsp1 ;
	JPanel jp1 ;
	JTextArea jta ;
	boolean choices = false ;
	
	public MyJFrame(String title) {
		this.setTitle(title);
		this.setSize(400, 500);
		this.setLocation(800, 300) ;
		this.setLayout(null);
		
		JLabel jlb1 = new JLabel("源文件地址：") ;
		jlb1.setLocation(50, 50);
		jlb1.setSize(100, 50);
		this.add(jlb1) ;
		
		JTextField jtf1 = new JTextField(50) ;
		jtf1.setSize(200, 20);
		jtf1.setLocation(125 ,65);
		this.add(jtf1) ;
		
		JLabel jlb2 = new JLabel("压缩文件地址：") ;
		jlb2.setLocation(35, 100);
		jlb2.setSize(100, 50);
		this.add(jlb2) ;
		
		JTextField jtf2 = new JTextField(50) ;
		jtf2.setLocation(125, 115);
		jtf2.setSize(200, 20);
		this.add(jtf2) ;
		
		JButton jb = new JButton("我要压缩") ;
		jb.setLocation(80, 160) ;
		jb.setSize(100, 30);
		this.add(jb) ;
		
		JLabel jlb3 = new JLabel("您输入的源文件不存在，请重新输入") ;
		jlb3.setSize(300,100) ;
		jlb3.setLocation(80, 170);
		jlb3.setForeground(Color.RED) ;
		jlb3.setVisible(false);
		this.add(jlb3) ;
		
		JLabel jlb4 = new JLabel("文件输入格式: D:\\\\demo\\\\text.txt") ;
		jlb4.setSize(300,100);
		jlb4.setLocation(100, -20) ;
		this.add(jlb4) ;
		
		JLabel jlb8 = new JLabel("Huffman压缩文件格式:.chf   LZW压缩文件格式:.clz") ;
		jlb8.setSize(500,100);
		jlb8.setLocation(60, 0);
		this.add(jlb8) ;
		
		JLabel jlb5 = new JLabel() ;
		jlb5.setSize(300, 100);
		jlb5.setLocation(120, 230);
		jlb5.setVisible(false);
		this.add(jlb5) ;
		
		JLabel jlb6 = new JLabel("两种压缩算法的压缩率如下：") ;
		jlb6.setSize(300, 100);
		jlb6.setLocation(120, 200);
		jlb6.setVisible(false);
		this.add(jlb6) ;
		
		JLabel jlb7 = new JLabel() ;
		jlb7.setSize(300, 100);
		jlb7.setLocation(120, 260);
		jlb7.setVisible(false);
		this.add(jlb7) ;
		
		JButton Huffman = new JButton("Huffman压缩") ;
		Huffman.setSize(110, 30);
		Huffman.setLocation(70, 360);
		Huffman.setVisible(false);
		this.add(Huffman) ;
		
		JButton LZW = new JButton("LZW压缩") ;
		LZW.setSize(110, 30);
		LZW.setLocation(190, 360);
		LZW.setVisible(false);
		this.add(LZW) ;
		
		JButton jb2 = new JButton("我要解压") ;
		jb2.setLocation(200, 160);
		jb2.setSize(100,30);
		this.add(jb2) ;
		
		JButton jbl1 = new JButton("Huffman解压") ;
		jbl1.setSize(110, 30);
		jbl1.setLocation(130, 240);
		jbl1.setVisible(false);
		this.add(jbl1) ;
		
		JButton jbl2 = new JButton("LZW解压") ;
		jbl2.setSize(110, 30);
		jbl2.setLocation(250, 240);
		jbl2.setVisible(false);
		this.add(jbl2) ;
		
		JLabel jla = new JLabel("您输入的文件格式不合法，请重新输入") ;
		jla.setSize(300,100) ;
		jla.setLocation(80, 170);
		jla.setForeground(Color.RED) ;
		jla.setVisible(false);
		this.add(jla) ;
		
		
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				jbl1.setVisible(false);
				jbl2.setVisible(false);
				jla.setVisible(false);
				
				
				String path = jtf1.getText() ;
				String destpath = jtf2.getText() ;
				File file1 = new File(path) ;
				if(!file1.exists()) {
					jlb3.setVisible(true);
					jlb5.setVisible(false);
					jlb6.setVisible(false);
					jlb7.setVisible(false);
					Huffman.setVisible(false);
					LZW.setVisible(false);
				}
				else {
					jlb3.setVisible(false);
					jlb6.setVisible(true) ;
					Compress com1 = new Compress() ;
					CompressLZW com2 = new CompressLZW() ;
					try {
						com1.countTimes(path);
						HuffNode root = com1.createTree() ;
						com1.getHuffCode(root, "");
//						compress.getCompressRate(path)*100 + "%"
						float rate1 = 100 - com1.getCompressRate(path)*100 ;
						jlb5.setText("Huffman:  " + rate1 + "%" );
						
						float rate2 = 100 - com2.getCompressRate(path)*100 ;
						jlb7.setText("LZW:         " + rate2 + "%");
						
						jlb5.setVisible(true);
						jlb7.setVisible(true);
						Huffman.setVisible(true) ;
						LZW.setVisible(true) ;
						
						Huffman.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								String path = jtf1.getText() ;
								String destpath = jtf2.getText() ;
								try {
									com1.compress(path, destpath);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						});
						
						LZW.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								String path = jtf1.getText() ;
								String destpath = jtf2.getText() ;
								try {
									com2.compress(path, destpath);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
						});
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		
		
		
		
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jlb5.setVisible(false);
				jlb6.setVisible(false);
				jlb7.setVisible(false);
				Huffman.setVisible(false);
				LZW.setVisible(false);
				jlb3.setVisible(false);
				
				String path = jtf1.getText() ;
				String destpath = jtf2.getText() ;
				File file = new File(destpath) ;
				if(!file.exists()) {
					jlb3.setVisible(true);
				}
				else {
					jlb3.setVisible(false);
					jbl1.setVisible(true);
					jbl2.setVisible(true);
					
					jbl1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String path = jtf1.getText() ;
							String destpath = jtf2.getText() ;
							String last = destpath.substring(destpath.length()-4, destpath.length()) ;
							if(!last.equals(".chf")) {
								jla.setVisible(true);
							}
							else {
								jla.setVisible(false);
								Decompress d = new Decompress() ;
								try {
									d.decompress(destpath, path);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					});
					
					jbl2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String path = jtf1.getText() ;
							String destpath = jtf2.getText() ;
							String last = destpath.substring(destpath.length()-4, destpath.length()) ;
							if(!last.equals(".clz")) {
								jla.setVisible(true);
							}
							else {
								jla.setVisible(false);
								try {
									new DecompressLZW() .decompress(destpath, path);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					});
				}
			}
			
		});
		
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

//D:\\demo\\test.txt
//D:\\demo\\test.zip
