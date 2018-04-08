package nice;

import nice.wsdl.ArrayOfKeyValuePair;
import nice.wsdl.GeneralInfo;
import nice.wsdl.KeyValuePair;
import nice.wsdl.SetGeneralInfoResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner lookup(NiceClient niceClient) {
        return args -> {
            //construct the SOAP object
            JAXBElement<String> jeKey = new JAXBElement<>(new QName("Key"), String.class, "key1");
            JAXBElement<String> jeValue = new JAXBElement<>(new QName("Value"), String.class, "value1");
            KeyValuePair kvp = new KeyValuePair();
            kvp.setKey(jeKey);
            kvp.setValue(jeValue);

            ArrayOfKeyValuePair aokvp = new ArrayOfKeyValuePair();
            aokvp.getKeyValuePair().add(kvp);
            jeKey = new JAXBElement<>(new QName("Key"), String.class, "key2");
            jeValue = new JAXBElement<>(new QName("Value"), String.class, "value2");
            kvp.setKey(jeKey);
            kvp.setValue(jeValue);
            aokvp.getKeyValuePair().add(kvp);

            JAXBElement<ArrayOfKeyValuePair> jeaokvp = new JAXBElement<>(new QName("ArrayOfKeyValuePair"), ArrayOfKeyValuePair.class, aokvp);
            GeneralInfo generalInfo = new GeneralInfo();
            generalInfo.setKeyValuePairs(jeaokvp);

            JAXBElement<GeneralInfo> reqPara = new JAXBElement<>(new QName("GeneralInfo"), GeneralInfo.class, generalInfo);

            //invoke the web service
            SetGeneralInfoResponse response = niceClient.setGeneralInfo(reqPara);
            System.out.println("SetGeneralInfoResponse=" + response);
        };
    }
}
