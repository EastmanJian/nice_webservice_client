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

            JAXBElement<String> jeKey = objFac.createKeyValuePairKey("key1");
            JAXBElement<String> jeValue = objFac.createKeyValuePairValue("value1");
            KeyValuePair kvp = new KeyValuePair();
            kvp.setKey(jeKey);
            kvp.setValue(jeValue);

            ArrayOfKeyValuePair aokvp = new ArrayOfKeyValuePair();
            aokvp.getKeyValuePair().add(kvp);
            jeKey = objFac.createKeyValuePairKey("key2");
            jeValue = objFac.createKeyValuePairValue("value2");
            kvp = new KeyValuePair();
            kvp.setKey(jeKey);
            kvp.setValue(jeValue);
            aokvp.getKeyValuePair().add(kvp);

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
