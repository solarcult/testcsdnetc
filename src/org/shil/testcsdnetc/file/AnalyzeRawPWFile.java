package org.shil.testcsdnetc.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.shil.testcsdnetc.db.CsdnAccountDaoImpl;
import org.shil.testcsdnetc.entity.CsdnAccount;

public class AnalyzeRawPWFile {
	
	public static void analyzeFile2DB(){
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(new File("e:/www.csdn.net.sql")));
			String oneline = bufferedReader.readLine();
			int i=1;
			while(oneline!=null){
				try {
//					System.out.println(oneline);
					
					//proccess
					String[] threes = oneline.split("#");
					CsdnAccount csdnAccount = new CsdnAccount(threes[0].trim(),threes[1].trim(),threes[2].trim(),0);
					CsdnAccountDaoImpl.insertCsdnAccount(csdnAccount);
					System.out.println(csdnAccount);

				} catch (Exception e) {
					e.printStackTrace();
				}
				oneline = bufferedReader.readLine();
				i++;
			}
			System.out.println("total: "+ i);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bufferedReader!=null)
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
	}

	public static void main(String[] args) {
		analyzeFile2DB();
	}

}
