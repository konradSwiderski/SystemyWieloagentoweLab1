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

        //Temp Container
//        ContainerOfTokens tempContainer = new ContainerOfTokens();
//        tempContainer.setLimitValueOfTokens(ThreadLocalRandom.current().nextInt(100, 200 + 1));
//        handlingClientBehaviour.setContainerOfTokens(tempContainer);//init member
        handlingClientBehaviour.getContainerOfTokens().setLimitValueOfTokens(randNumberOfTokens);

//        System.out.println("Limit of tokens: " + tempContainer.getLimitValueOfTokens());

        //ADD Creating token behaviour #interval
        Behaviour creatingTokenBehaviour = new TickerBehaviour( this, randInterval)
        {
            protected void onTick()
            {
//                tempContainer.createSingleToken();
//                handlingClientBehaviour.setContainerOfTokens(tempContainer);//set to member
                handlingClientBehaviour.getContainerOfTokens().createSingleToken();
            }
        };
        addBehaviour(creatingTokenBehaviour);
        System.out.println("Server has been started");
    }
}