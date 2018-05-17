import java.util.TimerTask;

public class taskSauvegarde extends TimerTask {
	private Interface I;
	
	public taskSauvegarde(Interface i){
		this.I = i;
	}
	@Override
	public void run() {
		I.sauvegarder_fichier();
	}
}
