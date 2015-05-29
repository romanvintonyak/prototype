package com.epam.controllers;

import com.epam.customer.data.CustomerAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.CustomerFacade;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author Roman_Kovalenko
 */
@Controller
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.GET)
    public  @ResponseBody Collection<CustomerAddressData> getCustomerAddresses(@PathVariable("customerId") String customerId){
        return customerFacade.findCustomerAddresses(customerId);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public  @ResponseBody CustomerAddressData getCustomerAddresses(EpamCustomerData customer, CustomerAddressData addressData){
        return customerFacade.createCustomerAddress(customer, addressData);
    }
    
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)    
    public @ResponseBody EpamCustomerData getCustomer(@PathVariable("customerId") String customerId) {
    	String userId = new String(Base64.decodeBase64((customerId.getBytes(StandardCharsets.UTF_8))));
        return customerFacade.findCustomerByUID(userId);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer(@RequestBody EpamCustomerData customerData) throws DuplicateUidException {
    	customerFacade.createCustomer(customerData);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody EpamCustomerData customerData) {
    	customerFacade.updateCustomer(customerData);
    }
}
