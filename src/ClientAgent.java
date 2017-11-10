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

    private int randInterval = 0;
    private Vector<AID> vectorOfServers = new Vector<>();
    private Integer allowToRequest = 0;
    int numberOfReceivedMsg = 0;

    public ClientAgent()
    {
        randInterval = ThreadLocalRandom.current().nextInt(200, 2000 + 1);
        System.out.println("_Time of interval behaviour: #Reqesusting server " + randInterval);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setup()
    {
        super.setup();
        Behaviour requestingBehaviour = new TickerBehaviour(this, randInterval)
        {
            protected void onTick()
            {
                vectorOfServers.clear();
                searchServers();
                if(canReceive() == true)
                {
                    ACLMessage msg = myAgent.receive();
                    if(msg != null)
                    {
                        if (msg.getPerformative() == ACLMessage.AGREE)//Token
                        {
                            allowToRequest = 2;
                            numberOfReceivedMsg++;
                        }
                        else if (msg.getPerformative() == ACLMessage.INFORM)//Not yet
                        {
                            allowToRequest = 2;
                        }
                        else if (msg.getPerformative() == ACLMessage.FAILURE)//The end
                        {
                            System.out.println("-----------\nEND OF AGENT " + myAgent.getName());
                            System.out.println("I have received: " + numberOfReceivedMsg + " tokens");
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
            }
        };
        addBehaviour(requestingBehaviour);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean canReceive()
    {
        if(allowToRequest>0)
            allowToRequest--;
        if(allowToRequest == 0)
            return  true;
        else
            return false;
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


