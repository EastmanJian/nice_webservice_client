package nice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

//Spring WS uses Spring Framework’s OXM module which has the Jaxb2Marshaller to serialize and deserialize XML requests.
@Configuration
public class NiceConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml
        marshaller.setContextPath("nice.wsdl");
        return marshaller;
    }

    //The marshaller is pointed at the collection of generated domain objects and will use them to both serialize and deserialize between XML and POJOs.
    @Bean
    public NiceClient countryClient(Jaxb2Marshaller marshaller) {
        NiceClient client = new NiceClient();
        client.setDefaultUri("http://localhost:8080/RTGeneralService");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
