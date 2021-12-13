package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Basketball");
		assertEquals("Basketball", ligue.getNom());
	}
	
	@Test
	void modifLigue() throws SauvegardeImpossible
	{
		Ligue ligue=gestionPersonnel.addLigue("Basketball");
		ligue.setNom("Basket");
		assertEquals("Basket",ligue.getNom());
	}
	
	@Test
	void deleteLigue() throws SauvegardeImpossible
	{
		Ligue ligue =gestionPersonnel.addLigue("Basketball");
		ligue.remove();
		assertEquals(false, gestionPersonnel.getLigues().contains(ligue));
	}
	
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Basketball");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void modifEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Basketball");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		Employe employe2 = ligue.addEmploye("Bouchard2","Gérard2","g.bouchard2@gmail.com", "azerty2");
		employe.setNom("Bouchard2");
		employe.setPrenom("Gérard2");
		employe.setMail("g.bouchard2@gmail.com");
		employe.setPassword("azerty2");
		assertEquals(employe2.getNom(),employe.getNom());
		assertEquals(employe2.getPrenom(),employe.getPrenom());	
		assertEquals(employe2.getMail(),employe.getMail());	
	}
	
	@Test
	void deleteEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Basketball");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		employe.remove();
		assertEquals(false,ligue.getEmployes().contains(employe));
		
	}
}
