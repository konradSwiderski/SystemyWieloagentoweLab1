import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.concurrent.ThreadLocalRandom;

public class ServerAgent extends Agent
{
    @Override
    protected void setup()
    {
        super.setup();

        //Register agent
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("generator_of_tokens");
        sd.setName("serverAgent");
        dfd.addServices(sd);
        try
        {
            DFService.register(this, dfd);
        }
        catch(FIPAException ex)
        {
            ex.printStackTrace();
        }
    }
}