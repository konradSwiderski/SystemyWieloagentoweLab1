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

        //ADD Handling Client behaviour
        HandlingClientBehaviour handlingClientBehaviour = new HandlingClientBehaviour();
        addBehaviour(handlingClientBehaviour);

        //Temp Container
        ContainerOfTokens tempContainer = new ContainerOfTokens();
        tempContainer.setLimitValueOfTokens(ThreadLocalRandom.current().nextInt(20, 30 + 1));//set limit

        System.out.println("Limit of tokens: " + tempContainer.getLimitValueOfTokens());

        //ADD Creating token behaviour #interval
        Behaviour creatingTokenBehaviour = new TickerBehaviour( this, ThreadLocalRandom.current().nextInt(200, 2000 + 1))
        {
            protected void onTick()
            {
                tempContainer.createSingleToken();
            }

        };
        /////////////////////////////////////////TO DO... set container in HandlingClientBehaviour
        addBehaviour(creatingTokenBehaviour);
        System.out.println("Server has been started");
    }
}