package com.example.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class HelloWebClientController {
    @Autowired
    private DiscoveryClient discoveryClient;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HelloWebClientController.class);
    
    @GetMapping("/")
    public String handleRequest(Model model) {
        //accessing hello-service
        List<ServiceInstance> instances = discoveryClient.getInstances("demo-service");
        if (instances != null && instances.size() > 0) {//todo: replace with a load balancing mechanism
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();
            url = url + "/hello";
            logger.info("************** Url created for web client : " + url);
            RestTemplate restTemplate = new RestTemplate();
            HelloObject helloObject = restTemplate.getForObject(url,HelloObject.class);
            model.addAttribute("msg", helloObject.getMessage());
            model.addAttribute("time", LocalDateTime.now());
        }
        return "hello-page";
    }
    
    @GetMapping("/calculate")
    public String handleCalculationResult(Model model)
    {
    	List instances =  discoveryClient.getInstances("demo-service-calculation");
    	if(instances !=null && instances.size() > 0)
    	{
    		ServiceInstance serviceInstance = (ServiceInstance) instances.get(0);
    		String urlString = serviceInstance.getUri().toString();
    		urlString = urlString + "/add";
    		RestTemplate template = new RestTemplate();
    		MathObject math = template.getForObject(urlString, MathObject.class);
    		model.addAttribute("result", math.getResult());
    		model.addAttribute("time", LocalDateTime.now());
    	}
    	return "result";
    }
}