package com.epam.controllers;

import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.EpamCustomerFacade;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
 * @author Irina_Vasilyeva
 */
@Controller
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private EpamCustomerFacade epamCustomerFacade;

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.GET)
    public  @ResponseBody Collection<EpamAddressData> getCustomerAddresses(@PathVariable("customerId") String customerId){
        return epamCustomerFacade.findCustomerAddresses(customerId);
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.POST)
    public  @ResponseBody EpamAddressData createCustomerAddresses(@PathVariable("customerId") String customerId, EpamAddressData addressData){
        return epamCustomerFacade.saveCustomerAddress(addressData, customerId);
    }

    @RequestMapping(value = "/{customerId}/address", method = RequestMethod.PUT)
    public  @ResponseBody EpamAddressData updateCustomerAddresses(@PathVariable("customerId") String customerId, EpamAddressData addressData){
        return epamCustomerFacade.saveCustomerAddress(addressData, customerId);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public @ResponseBody EpamCustomerData getCustomer(@PathVariable("customerId") String customerId) {
        String userId = new String(Base64.decodeBase64((customerId.getBytes(StandardCharsets.UTF_8))));
        return epamCustomerFacade.findCustomerByUID(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createCustomer(@RequestBody EpamCustomerData customerData) throws DuplicateUidException {
        epamCustomerFacade.createCustomer(customerData);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody EpamCustomerData customerData) {
        epamCustomerFacade.updateCustomer(customerData);
    }
}
