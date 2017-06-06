package ruuLength;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//���ӵǴ� 0�� �� 1�� ���� ��ȯ
class DecToBinary	//num==���ӵǴ� 0�� ���� �Ű������� ex bit��ŭ ũ��� ��ȯ
{
	String calBinary(int num,int ex) 
	{
		String res="";	//fileOut�� string������ �ϱ� ���� �ӽ� ����
		for(int i=ex ;i>=0; i--)
		{
			res+=(num>>i)&1;
		}
		return res;
	}

}


public class RunLengthEncoding {

	//�Էµ� txt���� �ִ� 0�� ������ �������� bit�� ���� �Լ�
	int calEx(int number)
	{
		int count=0;

		for(;;)
		{
			number=number/2;	//���ѷ����� �Ű������� �Էµ� ���� 2���� ���� �� ���� ����
			count ++;
			if(number<2)
				break;
		}

		return count;
	}

	//�����Լ�
	public static void main(String[] args) {

		int c=0;
		double IOcount=0;
		int ex=0;
		int count=0;
		int maxCount=0;
		String msg="";
		double IOcount2=0;
		double convertPer=0;


		DecToBinary obj=new DecToBinary(); //��ȯŬ������ �̿��ϱ� ���� ��ü ����



		FileInputStream fin = null;
		FileInputStream fin2 = null;
		FileWriter fout = null;	//���� ������� ���� ��ü ����

		try {
			fout=new FileWriter("D:\\Run_LengthConvert.txt");	//����� ������ ����� txt
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			fin = new FileInputStream("D:\\testIO.txt"); // ��ȯ��ų ������ ����ִ� txt������ �о��

			while((c = fin.read()) != -1) {	//������ �о�´�.

				if((char)c=='0')	//int�� c�� char������ ����ȯ�Ͽ� �� ��
				{
					maxCount++;		
				}
				if((char)c=='1')
				{
					if(ex<maxCount)	//���� ���ӵǴ� 0�� ���ڰ� ������ Max���� ũ�ٸ� �� ���� Max�� ����
					{ex=maxCount;}
					maxCount=0;	//max�ʱ�ȭ
				}
				IOcount++;		//����� ��������� �� ���ھ� �о�� �� ���� ++ ���ش�.(==��ȯ�� txt������ ����) 
			}

		} catch (IOException e) {
			System.out.println("IO����");
			e.printStackTrace();
		}

		try {
			fin.close();
		} catch (IOException e) {

			e.printStackTrace();
		} 
		//////////////0���� �ִ� ���� ã�� ���� , �� ���̸� ����ϱ� ���� �о��.
		//////////////���� ���� ���� �о����. try catch�� ���� ����½� �߻� �� ���ܿ����� ���� ó�� 
		
		ex=new RunLengthEncoding().calEx(ex); // ex == ��ȯ�� txt�� �ִ� 0���� �ִ� ����


		try {
			fin2 = new FileInputStream("D:\\testIO.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int tempCount=0;
		try {
			while((c = fin2.read()) != -1) {
				
				tempCount++;
				if((char)c=='0')
				{
					count++;
				}
				if((char)c=='1')
				{

					msg=obj.calBinary(count,ex); //��ȯ�� �������� 0���� count�� ex bit ũ��� 2���� ��ȯ Ŭ����
					fout.write(msg); //�̸� ������ ���� txt���Ͽ� ��ȯ�� ������ string ���·� ���(==����)
					
					IOcount2+=(ex+1); //2���� ��� �� �� ��° ���.
					//ù ��° ��� :��µ� txt������ �ٽ� �о�ͼ� ������ �� ��İ� �Ȱ��� count�� �������� ���̰��.
					//�� ��° ��� :������ ��µ� ��Ʈ���� ex��ŭ ��µ� ���̹Ƿ� ex��ŭ ��� ����(�������, 9��Ʈ��� ��ȯ �� �� ���� 9 ��Ʈ �� ������ �� �̿�)
					count=0;//count �ʱ�ȭ
				}
				//if((c= fin2.read())==-1){}
				if((tempCount==IOcount)&& ((char)c=='0') ) //������ ���ڰ� 0�̶��.
				{
					msg=obj.calBinary(count,ex);
					fout.write(msg);
					IOcount2+=(ex+1);
					IOcount2+=(ex+1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		try {
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("");

		//����� ����Ͽ� �ܼ�â�� ���.
		System.out.println("���� ������ �ڵ��� ��Ʈ���� :"+(int)IOcount+"��");
		System.out.println("������ڵ��� ��Ʈ���� : "+(int)IOcount2+"��");
		convertPer=(double)((IOcount-IOcount2)*100/IOcount);
		System.out.printf("�����: %.6f",convertPer);
		System.out.print(" %");


	}

}

//������ 1. 0�� ��������� ���� �Ͽ� �ۼ�
//������ 2. ������ ���� 0���� ���� ���� �ȵ� -> ���Ƿ� 1�� �־ ����.
//			=>2�� ������ ����ϴ� ���꿡�� ���ǹ��� ���������ν� �ذ� ����.

