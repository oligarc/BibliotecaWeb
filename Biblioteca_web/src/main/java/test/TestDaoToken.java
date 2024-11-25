package test;

import dao.DaoToken;
import entidades.Token;

public class TestDaoToken {

	public static void main(String[] args) {
	
		Token t = new Token();
		DaoToken daoT = new DaoToken();
		
		t.setEmail("prueba");
		t.setClave("ABC");
		t.setValue("123");
		t.setTelefono("64578");
		
		daoT.addToken(t);
	}

}
