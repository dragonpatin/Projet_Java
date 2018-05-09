public class Radiateur extends Objet{

    protected int thermostat;
    protected int temperature;

    public Radiateur(int thermostat , int temperature){
        this.thermostat = thermostat;
        this.temperature = temperature;
    }

    public int getThermostat(){
            return this.thermostat;
    }

    public int getTemperature(){
            return this.temperature;
    }

    public void ModifieThermostat(int thermostat){
        this.thermostat = thermostat;
        }
}
