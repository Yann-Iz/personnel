package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Employe employe) throws SauvegardeImpossible;
	public int modifier(Employe employe) throws SauvegardeImpossible;
	public int supprimer(Employe employe) throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public int modifier(Ligue ligue) throws SauvegardeImpossible;
	public int supprimer(Ligue ligue) throws SauvegardeImpossible;
}
