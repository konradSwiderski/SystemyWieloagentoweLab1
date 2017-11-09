import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class ClientAgent extends Agent{

    private Vector<AID> vectorOfServers = new Vector<>();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setup()
    {
        super.setup();
        Behaviour requestingBehaviour = new TickerBehaviour(this,ThreadLocalRandom.current().nextInt(200, 2000 + 1) )
        {
            protected void onTick()
            {
                vectorOfServers.clear();
                searchServers();
                ACLMessage msg = myAgent.receive();
                if(msg != null)
                {
                    if (msg.getPerformative() == ACLMessage.AGREE)//Token
                    {

                    }
                    else if (msg.getPerformative() == ACLMessage.INFORM)//Not yet
                    {

                    }
                    else if (msg.getPerformative() == ACLMessage.FAILURE)//The end
                    {

                        doDelete();
                    }
                }
                else//Send request
                {
                    if(vectorOfServers.isEmpty() != true)
                    {
                            ACLMessage reply = new ACLMessage(ACLMessage.REQUEST);
                            reply.addReceiver(vectorOfServers.firstElement());
                            myAgent.send(reply);
                    }
                        block();
                }
            }
        };
        addBehaviour(requestingBehaviour);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void searchServers()
    {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("generator_of_tokens");
        sd.setName("serverAgent");
        template.addServices(sd);
        try
        {
            DFAgentDescription[] result = DFService.search(this, template);
            for(int i = 0; i < result.length; ++i)
                vectorOfServers.addElement(result[i].getName());
        }
        catch (FIPAException ex)
        {
            ex.printStackTrace();
        }
    }
}


