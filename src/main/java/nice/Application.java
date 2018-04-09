package nice;

import nice.wsdl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.bind.JAXBElement;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner lookup(NiceClient niceClient) {
        return args -> {
            //construct the SOAP object
            ObjectFactory objFac = new ObjectFactory();
            ArrayOfKeyValuePair aokvp = new ArrayOfKeyValuePair();
            JAXBElement<String> jeKey = null;
            JAXBElement<String> jeValue = null;
            KeyValuePair kvp = null;

            if (args.length > 1 && args.length % 2 == 0) {
                //set the input key-value pairs from arguments
                for (int i = 0; i < args.length; i += 2) {
                    jeKey = objFac.createKeyValuePairKey(args[i]);
                    jeValue = objFac.createKeyValuePairValue(args[i+1]);
                    kvp = new KeyValuePair();
                    kvp.setKey(jeKey);
                    kvp.setValue(jeValue);
                    aokvp.getKeyValuePair().add(kvp);
                }
            } else {
                //default test key-value pair
                jeKey = objFac.createKeyValuePairKey("key1");
                jeValue = objFac.createKeyValuePairValue("value1");
                kvp = new KeyValuePair();
                kvp.setKey(jeKey);
                kvp.setValue(jeValue);
                aokvp.getKeyValuePair().add(kvp);
            }

            JAXBElement<ArrayOfKeyValuePair> jeaokvp = objFac.createGeneralInfoKeyValuePairs(aokvp);
            GeneralInfo generalInfo = new GeneralInfo();
            generalInfo.setKeyValuePairs(jeaokvp);

            JAXBElement<GeneralInfo> reqPara = objFac.createSetGeneralInfoGeneralInfo(generalInfo);

            //invoke the web service
            SetGeneralInfoResponse response = niceClient.setGeneralInfo(reqPara);
            System.out.println("SetGeneralInfoResponse=" + response);
        };
    }
}
