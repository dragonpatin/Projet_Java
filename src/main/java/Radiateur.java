public class Radiateur extends Objet{

    int thermostat;
    int temperature;

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
