import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HandlingClientBehaviour extends CyclicBehaviour
{

    private ContainerOfTokens containerOfTokens;// NOT SET

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setContainerOfTokens(ContainerOfTokens containerOfTokens) { this.containerOfTokens = containerOfTokens; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void action() //listening ClientAgents //TO DO... handle msg and send token
    {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST)); //set pattern and filter to request
        if (msg!= null)
        {
            ACLMessage reply = msg.createReply();//create reply to Agent
            //handling msg from ClientAgent
            //send msg to ClientAgent
        }
        else
        {
            block();
        }

    }
}