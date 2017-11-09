import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HandlingClientBehaviour extends CyclicBehaviour
{

    private ContainerOfTokens containerOfTokens;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setContainerOfTokens(ContainerOfTokens containerOfTokens) { this.containerOfTokens = containerOfTokens; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void action() //listening ClientAgents //TO DO... handle msg and send token
    {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST)); //set pattern and filter to request
        if (msg!= null)
        {
            ACLMessage reply = msg.createReply();//create reply to Agent
            SingleToken singleToken = containerOfTokens.getSingleToken();

            if (singleToken != null)//I have token
            {
                reply.setPerformative(ACLMessage.AGREE);
                reply.setContent(singleToken.getTokenValue());
                System.out.println("I have free token and I send to: " + msg.getSender().getName());
            }
            else if(singleToken == null && containerOfTokens.getCurrentNumberOfTokens() < containerOfTokens.getLimitValueOfTokens())//Not yet
            {
                reply.setPerformative(ACLMessage.INFORM);
            }
            else//No more tokens
            {
                reply.setPerformative(ACLMessage.FAILURE);
            }
            myAgent.send(reply);
        }
        else
        {
            block();
        }

    }
}