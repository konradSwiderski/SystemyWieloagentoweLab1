import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


import java.util.Vector;
public class ClientAgent extends Agent{

    private Vector<AID> vectorOfServers = new Vector<>();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void setup()
    {
        super.setup();
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


