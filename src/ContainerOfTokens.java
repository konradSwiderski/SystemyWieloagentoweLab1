import java.security.SecureRandom;
import java.util.Vector;

public class ContainerOfTokens
{
    private Vector<SingleToken> vectorOfTokens = new Vector<>();
    private Integer limitValueOfTokens = 0;
    private Integer currentNumberOfTokens = 0;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer getLimitValueOfTokens() {
        return limitValueOfTokens;
    }

    public void setLimitValueOfTokens(Integer limitValueOfTokens) {
        this.limitValueOfTokens = limitValueOfTokens;
    }

    public Integer getCurrentNumberOfTokens() {
        return currentNumberOfTokens;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void createSingleToken()
    {
        if(currentNumberOfTokens < limitValueOfTokens)
        {
            //Secure
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);

            //Token
            SingleToken singleToken = new SingleToken();
            singleToken.setTokenValue(bytes.toString());
            vectorOfTokens.addElement(singleToken);
            currentNumberOfTokens++;

            //Print
            System.out.print("New token! Current number of token: " + currentNumberOfTokens);
            System.out.println(" Value of tokens: " + bytes.toString());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public SingleToken getSingleToken()
    {

        if(vectorOfTokens.isEmpty() != true)
        {
            SingleToken singleToken = vectorOfTokens.firstElement();
            vectorOfTokens.removeElementAt(0);
            return singleToken;
        }
        else
            return null;
    }
}
