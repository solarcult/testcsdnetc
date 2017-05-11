package org.shil.testcsdnetc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.shil.testcsdnetc.entity.CsdnAccount;

public class CsdnAccountDaoImpl {

	public static void insertCsdnAccount(CsdnAccount csdnAccount){
		
		Connection connection = PostgresqlDataBaseManager.getConnection();
		
		PreparedStatement preparedStatement = null;

		try{

			preparedStatement = connection.prepareStatement(
					
				"insert into csdnaccount(csdnname,password,email,site,status) values(?,?,?,?,?)"
			);

			preparedStatement.setString(1, csdnAccount.getCsdnname());

			preparedStatement.setString(2, csdnAccount.getPassword());
			
			preparedStatement.setString(3, csdnAccount.getEmail());
			
			preparedStatement.setString(4, csdnAccount.getSite());
			
			preparedStatement.setInt(5, csdnAccount.getStatus());
			
			preparedStatement.executeUpdate();

		}catch(Exception e){

			e.printStackTrace();

		}finally{
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateCsdnAccountStatusByEmail(String email,int status){
		Connection connection = PostgresqlDataBaseManager.getConnection();
		
		PreparedStatement preparedStatement = null;

		try{

			preparedStatement = connection.prepareStatement(
					
				"update csdnaccount set status = ? where email = ?"
			);

			preparedStatement.setInt(1, status);
			
			preparedStatement.setString(2, email);

			preparedStatement.executeUpdate();

		}catch(Exception e){

			e.printStackTrace();

		}finally{
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<CsdnAccount> listCsdnAccounts(String site, long offset,long pageSize){
		List<CsdnAccount> accounts = new ArrayList<CsdnAccount>();
		
		Connection connection = PostgresqlDataBaseManager.getConnection();

		PreparedStatement preStatement = null;

		try
		{
			preStatement = connection.prepareStatement(

					"select "

					+ "id,csdnname,password,email,site,status"
					
					+ " from csdnaccount "

					+ "where site = ? order by id offset ? limit ?"

					);

			preStatement.setString(1, site);

			preStatement.setLong(2, offset);
			
			preStatement.setLong(3, pageSize);

			ResultSet resultSet = preStatement.executeQuery();

			while(resultSet.next())

			{
				long id = resultSet.getLong(1);

				String csdnname = resultSet.getString(2);

				String password = resultSet.getString(3);

				String email = resultSet.getString(4);

		        int status = resultSet.getInt(6);

		        CsdnAccount csdnAccount = new CsdnAccount(csdnname,password,email,status);
		        csdnAccount.setId(id);
				
		        accounts.add(csdnAccount);

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(preStatement!=null) preStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return accounts;
	}
	
	
	public static void main(String[] args) {
//		CsdnAccount csdnAccount = new CsdnAccount("a","b","c@d");
//		insertCsdnAccount(csdnAccount);
//		updateCsdnAccountStatusByEmail("c@d",1);
		List<CsdnAccount> ss = listCsdnAccounts("163.com",100,20);
		for(CsdnAccount ca : ss){
			System.out.println(ca);
		}
	}

}
