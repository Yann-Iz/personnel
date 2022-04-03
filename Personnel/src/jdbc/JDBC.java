package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			System.out.println(ligue);
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (N_Ligue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (N_Employe, P_Employe, Mail_Employe, Droit_Root, Mdp_Employe, Date_début, Id_Ligue, Id_Ligue_1) VALUES (?, ?, ?, 0, ?, '"+java.time.LocalDate.now()+"', ?, ?);", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, employe.getLigue().getId());
			instruction.setInt(6, employe.getLigue().getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public int modifier(Employe employe) throws SauvegardeImpossible {
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set N_Employe = ?, P_Employe = ?, `Mail_Employe` = ?, `Mdp_Employe` = ?, `Id_Ligue` = ?, `Id_Ligue_1` = ? where N_Employe = ?, P_Employe = ?);", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, employe.getLigue().getId());
			instruction.setInt(6, employe.getLigue().getId());
			instruction.setString(7, employe.getNom());
			instruction.setString(8, employe.getPrenom());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public int supprimer(Employe employe) throws SauvegardeImpossible {
		try 
		{	
			Statement st=connection.createStatement();
			String sql="delete from employer where N_Employe = "+employe.getNom()+", P_Employe = "+employe.getPrenom()+"";
			return st.executeUpdate(sql);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
			
	}

	@Override
	public int modifier(Ligue ligue) throws SauvegardeImpossible {
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set N_Ligue = ? where Id_Ligue = ?);", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());			
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}	
	}

	@Override
	public int supprimer(Ligue ligue) throws SauvegardeImpossible {
		try 
		{	
			Statement st=connection.createStatement();
			String sql="delete from ligue where N_Employe = "+ligue.getId()+"";
			return st.executeUpdate(sql);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}	
	}
}
