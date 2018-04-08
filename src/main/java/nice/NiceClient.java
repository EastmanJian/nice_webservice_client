package nice;

import nice.wsdl.GeneralInfo;
import nice.wsdl.SetGeneralInfo;
import nice.wsdl.SetGeneralInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.support.MarshallingUtils;

import javax.xml.bind.JAXBElement;
import java.io.IOException;

//extend the WebServiceGatewaySupport class to create a web service client
public class NiceClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(NiceClient.class);

    //GetCountryResponse and GetCountryResponse are derived from the WSDL and were generated in the JAXB generation process
    public SetGeneralInfoResponse setGeneralInfo(JAXBElement<GeneralInfo> reqPara) {
        SetGeneralInfo request = new SetGeneralInfo();
        request.setGeneralInfo(reqPara);

        log.info("Requesting parameter=" + reqPara);

        //log SOAP message
        System.out.println("-----SOAP Envelope-----");
        Marshaller marshaller = getWebServiceTemplate().getMarshaller();
        DefaultMessageContext ex = new DefaultMessageContext(this.getMessageFactory());
        WebServiceMessage requestMsg = ex.getRequest();
        try {
            MarshallingUtils.marshal(marshaller, request, requestMsg);
            requestMsg.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        //send SOAP  web service request and receive response
        //uses the WebServiceTemplate supplied by the WebServiceGatewaySupport base class to do the actual SOAP exchange
        SetGeneralInfoResponse response = (SetGeneralInfoResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/RTGeneralService", request,
                        new SoapActionCallback("http://nice.com/IRTGeneralService/SetGeneralInfo"));

        return response;
    }
}
