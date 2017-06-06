package ruuLength;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//연속되는 0의 수 1의 수를 변환
class DecToBinary	//num==연속되는 0의 수를 매개변수로 ex bit만큼 크기로 변환
{
	String calBinary(int num,int ex) 
	{
		String res="";	//fileOut을 string형으로 하기 위해 임시 선언
		for(int i=ex ;i>=0; i--)
		{
			res+=(num>>i)&1;
		}
		return res;
	}

}


public class RunLengthEncoding {

	//입력된 txt에서 최대 0의 갯수를 기준으로 bit수 분할 함수
	int calEx(int number)
	{
		int count=0;

		for(;;)
		{
			number=number/2;	//무한루프로 매개변수로 입력된 수가 2보다 작을 때 까지 나눔
			count ++;
			if(number<2)
				break;
		}

		return count;
	}

	//메인함수
	public static void main(String[] args) {

		int c=0;
		double IOcount=0;
		int ex=0;
		int count=0;
		int maxCount=0;
		String msg="";
		double IOcount2=0;
		double convertPer=0;


		DecToBinary obj=new DecToBinary(); //변환클래스를 이용하기 위한 객체 선언



		FileInputStream fin = null;
		FileInputStream fin2 = null;
		FileWriter fout = null;	//파일 입출력을 위한 객체 선언

		try {
			fout=new FileWriter("D:\\Run_LengthConvert.txt");	//압축된 값들이 저장될 txt
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			fin = new FileInputStream("D:\\testIO.txt"); // 변환시킬 값들이 들어있는 txt파일을 읽어옴

			while((c = fin.read()) != -1) {	//끝까지 읽어온다.

				if((char)c=='0')	//int형 c를 char형으로 형변환하여 값 비교
				{
					maxCount++;		
				}
				if((char)c=='1')
				{
					if(ex<maxCount)	//만약 연속되는 0의 숫자가 이전의 Max보다 크다면 그 값을 Max에 저장
					{ex=maxCount;}
					maxCount=0;	//max초기화
				}
				IOcount++;		//압축률 계산을위해 한 글자씩 읽어올 때 마다 ++ 해준다.(==변환될 txt파일의 길이) 
			}

		} catch (IOException e) {
			System.out.println("IO실패");
			e.printStackTrace();
		}

		try {
			fin.close();
		} catch (IOException e) {

			e.printStackTrace();
		} 
		//////////////0들의 최대 갯수 찾기 위해 , 총 길이를 계산하기 위해 읽어옴.
		//////////////여기 까지 파일 읽어오기. try catch는 파일 입출력시 발생 할 예외오류에 관한 처리 
		
		ex=new RunLengthEncoding().calEx(ex); // ex == 변환될 txt에 있는 0들의 최대 갯수


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

					msg=obj.calBinary(count,ex); //변환될 연속적인 0들의 count를 ex bit 크기로 2진수 변환 클래스
					fout.write(msg); //미리 마련해 놓은 txt파일에 변환된 값들을 string 형태로 출력(==저장)
					
					IOcount2+=(ex+1); //2가지 방법 중 두 번째 방법.
					//첫 번째 방법 :출력된 txt파일을 다시 읽어와서 위에서 한 방식과 똑같이 count를 증가시켜 길이계산.
					//두 번째 방법 :어차피 출력될 비트들은 ex만큼 출력될 것이므로 ex만큼 계속 더함(예를들어, 9비트라면 변환 될 때 마다 9 비트 씩 찍히는 걸 이용)
					count=0;//count 초기화
				}
				//if((c= fin2.read())==-1){}
				if((tempCount==IOcount)&& ((char)c=='0') ) //마지막 숫자가 0이라면.
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

		//압축률 계산하여 콘솔창에 출력.
		System.out.println("기존 데이터 코드의 비트수는 :"+(int)IOcount+"개");
		System.out.println("압축된코드의 비트수는 : "+(int)IOcount2+"개");
		convertPer=(double)((IOcount-IOcount2)*100/IOcount);
		System.out.printf("압축률: %.6f",convertPer);
		System.out.print(" %");


	}

}

//문제점 1. 0의 런랭쓰라는 기준 하에 작성
//문제점 2. 마지막 값이 0으로 끝날 때는 안됨 -> 임의로 1을 넣어서 수정.
//			=>2번 문제는 출력하는 연산에서 조건문을 삽입함으로써 해결 가능.

