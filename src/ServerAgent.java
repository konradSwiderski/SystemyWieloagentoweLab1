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
    private  int randInterval = 0;
    private  int randNumberOfTokens = 0;
    public ServerAgent()
    {
        randInterval = ThreadLocalRandom.current().nextInt(200, 2000 + 1);
        System.out.println("_Time of interval behaviour: #Creating token " + randInterval);
        randNumberOfTokens = ThreadLocalRandom.current().nextInt(100, 200 + 1);
        System.out.println("Limit of tokens: " + randNumberOfTokens);
    }
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

        //Set limit of tokens
        handlingClientBehaviour.getContainerOfTokens().setLimitValueOfTokens(randNumberOfTokens);

        Behaviour creatingTokenBehaviour = new TickerBehaviour( this, randInterval)
        {
            protected void onTick()
            {

                handlingClientBehaviour.getContainerOfTokens().createSingleToken();//set to member
            }
        };
        addBehaviour(creatingTokenBehaviour);
        System.out.println("Server has been started");
    }
}